package crazywoddman.warium_additions.network;

import net.mcreator.crustychunks.entity.BulletfireProjectileEntity;
import net.mcreator.crustychunks.entity.FireSpearRocketProjectileEntity;
import net.mcreator.crustychunks.entity.RadarSpearMissileProjectileEntity;
import net.mcreator.crustychunks.entity.SeekerSpearMissileProjectileEntity;
import net.mcreator.crustychunks.entity.StrikeSpearProjectileEntity;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.mcreator.crustychunks.procedures.CasingDropProcedure;
import net.mcreator.crustychunks.procedures.MediumFireSoundProcedure;
import net.mcreator.crustychunks.procedures.MuzzleFlashProcedure;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Optional;
import java.util.function.Supplier;

public class ShootKeyPacket {
    public ShootKeyPacket() {}

    public void encode(FriendlyByteBuf buffer) {}

    public static ShootKeyPacket decode(FriendlyByteBuf buffer) {
        return new ShootKeyPacket();
    }

    private static Optional<IDynamicStackHandler> getSlot(ICuriosItemHandler inventory, String slot) {
        ICurioStacksHandler handler = inventory.getStacksHandler(slot).orElse(null);
        IDynamicStackHandler stacks = handler.getStacks();

        if (handler != null && stacks.getSlots() > 0 && !stacks.getStackInSlot(0).isEmpty())
            return Optional.of(stacks);

        return Optional.empty();
    }

    public void handle(Supplier<Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            Level level = player.level();
            
            if (player != null) {
                CuriosApi.getCuriosInventory(player).ifPresent(inventory -> {
                    getSlot(inventory, "hardpoint").ifPresent(handler -> {
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
                    getSlot(inventory, "ammobox").ifPresent(handler -> {
                        CompoundTag playerData = player.getPersistentData();
                        long tick = level.getGameTime();

                        if (Math.abs(playerData.getLong("WAlastShot") - tick) > 1L) {
                            CompoundTag nbt = handler.getStackInSlot(0).getOrCreateTag();

                            if (nbt.getInt("AmmoSize") == -1) {
                                int ammo = nbt.getInt("Ammo");

                                if (ammo > 0) {
                                    Vec3 pos = player.getEyePosition();
                                    Vec3 look = player.getLookAngle();
                                    Vec3 frontpos = pos.add(look.scale(1.5));
                                    BulletfireProjectileEntity bullet = new BulletfireProjectileEntity(
                                        CrustyChunksModEntities.BULLETFIRE_PROJECTILE.get(),
                                        frontpos.x, frontpos.y, frontpos.z,
                                        level
                                    );
                                    bullet.shoot(
                                        look.x,
                                        look.y,
                                        look.z,
                                        7.5F,
                                        0.9F
                                    );
                                    bullet.setSilent(true);

                                    if (level.addFreshEntity(bullet)) {
                                        ItemStack item = new ItemStack(CrustyChunksModItems.MEDIUM_CASING.get());
                                        playerData.putLong("WAlastShot", tick);
                                        MuzzleFlashProcedure.execute(level, frontpos.x, frontpos.y, frontpos.z);
                                        CasingDropProcedure.execute(level, pos.x, pos.y, pos.z);
                                        MediumFireSoundProcedure.execute(level, frontpos.x, frontpos.y, frontpos.z);

                                        if (!player.isCreative()) {
                                            player.addEffect(new MobEffectInstance(
                                                MobEffects.BLINDNESS,
                                                20,
                                                0,
                                                false,
                                                false
                                            ));
                                            nbt.putInt("Ammo", ammo - 1);

                                            if (!player.addItem(item))
                                                level.addFreshEntity(new ItemEntity(level, pos.x, pos.y, pos.z, item));
                                        }
                                    }
                                }
                            }
                        }
                    });
                });
            }
        });

        context.get().setPacketHandled(true);
    }
}