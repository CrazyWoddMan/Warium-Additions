package crazywoddman.warium_additions.network;

import net.mcreator.crustychunks.entity.BulletfireProjectileEntity;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

import crazywoddman.warium_additions.compat.curios.CuriosUtil;

public class ShootKeyPacket {
    public ShootKeyPacket() {}

    public void encode(FriendlyByteBuf buffer) {}

    public static ShootKeyPacket decode(FriendlyByteBuf buffer) {
        return new ShootKeyPacket();
    }

    public void handle(Supplier<Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            
            if (player != null) {
                Level level = player.level();
                CompoundTag playerData = player.getPersistentData();
                long tick = level.getGameTime();

                if (Math.abs(playerData.getLong("WAlastShot") - tick) > 1L) {
                    Vec3 pos = player.getEyePosition();
                    Vec3 look = player.getLookAngle();
                    Vec3 frontpos = pos.add(look.scale(1.5));
                    BulletfireProjectileEntity bullet = new BulletfireProjectileEntity(
                        CrustyChunksModEntities.BULLETFIRE_PROJECTILE.get(),
                        frontpos.x, frontpos.y, frontpos.z,
                        level
                    );
                    bullet.setBaseDamage(0.1F);
                    bullet.shoot(
                        look.x,
                        look.y,
                        look.z,
                        7.5F,
                        0.9F
                    );
                    bullet.setSilent(true);

                    if (level.addFreshEntity(bullet)) {
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
                            CuriosUtil.getItem(player, "ammobox", CrustyChunksModItems.MACHINE_GUN_BOX.get()).ifPresent(stack -> {
                                CompoundTag nbt = stack.getOrCreateTag();
                                ItemStack casing = new ItemStack(CrustyChunksModItems.MEDIUM_CASING.get());
                                nbt.putInt("Ammo", nbt.getInt("Ammo") - 1);

                                if (!player.addItem(casing))
                                    level.addFreshEntity(new ItemEntity(level, pos.x, pos.y, pos.z, casing));
                            });
                        }
                    }
                }
            }
        });

        context.get().setPacketHandled(true);
    }
}