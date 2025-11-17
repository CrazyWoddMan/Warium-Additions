package crazywoddman.warium_additions.network;

import net.mcreator.crustychunks.entity.FireSpearRocketProjectileEntity;
import net.mcreator.crustychunks.entity.RadarSpearMissileProjectileEntity;
import net.mcreator.crustychunks.entity.SeekerSpearMissileProjectileEntity;
import net.mcreator.crustychunks.entity.StrikeSpearProjectileEntity;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import crazywoddman.warium_additions.compat.curios.CuriosUtil;

public class LaunchKeyPacket {
    public LaunchKeyPacket() {}

    public void encode(FriendlyByteBuf buffer) {}

    public static LaunchKeyPacket decode(FriendlyByteBuf buffer) {
        return new LaunchKeyPacket();
    }

    public void handle(Supplier<Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            Level level = player.level();
            
            if (player != null) {
                CuriosUtil.getSlot(player, "hardpoint").ifPresent(handler -> {
                    Vec3 pos = player.getEyePosition();
                    AbstractArrow missile = switch (ForgeRegistries.ITEMS.getKey(handler.getStackInSlot(0).getItem()).getPath()) {
                        case "fire_spear_rocket" -> new FireSpearRocketProjectileEntity(CrustyChunksModEntities.FIRE_SPEAR_ROCKET_PROJECTILE.get(), pos.x, pos.y, pos.z, level);
                        case "seeker_spear_rocket" -> new SeekerSpearMissileProjectileEntity(CrustyChunksModEntities.SEEKER_SPEAR_MISSILE_PROJECTILE.get(), pos.x, pos.y, pos.z, level);
                        case "radar_spear_missile" -> new RadarSpearMissileProjectileEntity(CrustyChunksModEntities.RADAR_SPEAR_MISSILE_PROJECTILE.get(), pos.x, pos.y, pos.z, level);
                        default -> new StrikeSpearProjectileEntity(CrustyChunksModEntities.STRIKE_SPEAR_PROJECTILE.get(), pos.x, pos.y, pos.z, level);
                    };
                    double tilt = player.isVisuallyCrawling() ? 0.9 : (player.isCrouching() ? 0.4 : 0.2);
                    Vec3 look = player.getLookAngle();
                    Vec3 horizontalLook = new Vec3(look.x, 0, look.z).normalize();
                    Vec3 direction = (new Vec3(0, 1, 0))
                        .scale(1.0 - tilt)
                        .add(horizontalLook.scale(tilt))
                        .normalize();
                    missile.shoot(
                        direction.x,
                        direction.y,
                        direction.z,
                        4.0F,
                        2.0F
                    );

                    if (level.addFreshEntity(missile) && !player.isCreative())
                        handler.setStackInSlot(0, ItemStack.EMPTY);
                });
            }
        });

        context.get().setPacketHandled(true);
    }
}