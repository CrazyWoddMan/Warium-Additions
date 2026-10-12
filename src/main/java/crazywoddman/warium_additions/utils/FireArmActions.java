package crazywoddman.warium_additions.utils;

import java.util.HashMap;
import java.util.Map;

import net.mcreator.crustychunks.procedures.ArmorPeelerRightclickedProcedure;
import net.mcreator.crustychunks.procedures.AutoPistolFireScriptProcedure;
import net.mcreator.crustychunks.procedures.AutoRifleDoubleFireProcedure;
import net.mcreator.crustychunks.procedures.BattleRifleFireScriptProcedure;
import net.mcreator.crustychunks.procedures.BoltActionRifleEntitySwingsItemProcedure;
import net.mcreator.crustychunks.procedures.BoltFireScriptProcedure;
import net.mcreator.crustychunks.procedures.BreakActionFireScriptProcedure;
import net.mcreator.crustychunks.procedures.BreechRifleFireScriptProcedure;
import net.mcreator.crustychunks.procedures.BurstRifleDoubleFireProcedure;
import net.mcreator.crustychunks.procedures.CarbineDoubleFireProcedure;
import net.mcreator.crustychunks.procedures.FlameThrowerDoubleFireProcedure;
import net.mcreator.crustychunks.procedures.FlarePistolFireScriptProcedure;
import net.mcreator.crustychunks.procedures.GrenadeLauncherFireProcedure;
import net.mcreator.crustychunks.procedures.LMGDoubleFireProcedure;
import net.mcreator.crustychunks.procedures.LeverGunFireScriptProcedure;
import net.mcreator.crustychunks.procedures.PistolActionProcedure;
import net.mcreator.crustychunks.procedures.RevolverActionProcedure;
import net.mcreator.crustychunks.procedures.RevolverFireScriptProcedure;
import net.mcreator.crustychunks.procedures.RifleActionScriptProcedure;
import net.mcreator.crustychunks.procedures.RifleFireScriptProcedure;
import net.mcreator.crustychunks.procedures.SMGDoubleFireProcedure;
import net.mcreator.crustychunks.procedures.SemiAutomaticPistolEntitySwingsItemProcedure;
import net.mcreator.crustychunks.procedures.ShotgunActionScriptProcedure;
import net.mcreator.crustychunks.procedures.ShotgunFireScriptProcedure;
import net.mcreator.crustychunks.procedures.SingleShotFireScriptProcedure;
import net.mcreator.crustychunks.procedures.StealthPistolFireProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import static net.mcreator.crustychunks.init.CrustyChunksModItems.*;

public record FireArmActions(FireArmAction shoot, FireArmAction bolt) {
    private static final Map<Item, FireArmActions> ACTIONS = new HashMap<>();
    private static final FireArmAction EMPTY = (level, x, y, z, entity, stack) -> {};
    
    static {
        put(AUTO_PISTOL,                        AutoPistolFireScriptProcedure::execute,                 PistolActionProcedure::execute);
        put(SEMI_AUTOMATIC_PISTOL_ANIMATED,     SemiAutomaticPistolEntitySwingsItemProcedure::execute,  PistolActionProcedure::execute);
        put(STEALTH_PISTOL,                     StealthPistolFireProcedure::execute,                    PistolActionProcedure::execute);
        put(REVOLVER_ANIMATED,                  RevolverFireScriptProcedure::execute,                   RevolverActionProcedure::execute);
        put(SMG_ANIMATED,                       SMGDoubleFireProcedure::execute,                        PistolActionProcedure::execute);
        put(MACHINE_CARBINE,                    CarbineDoubleFireProcedure::execute,                    RifleActionScriptProcedure::execute);
        put(LEVER_RIFLE,                        LeverGunFireScriptProcedure::execute,                   RifleActionScriptProcedure::execute);
        put(SEMI_AUTOMATIC_RIFLE_ANIMATED,      RifleFireScriptProcedure::execute,                      RifleActionScriptProcedure::execute);
        put(AUTOMATIC_RIFLE,                    AutoRifleDoubleFireProcedure::execute,                  RifleActionScriptProcedure::execute);
        put(BURST_RIFLE,                        BurstRifleDoubleFireProcedure::execute,                 RifleActionScriptProcedure::execute);
        put(LMG_ANIMATED,                       LMGDoubleFireProcedure::execute,                        RifleActionScriptProcedure::execute);
        put(BATTLE_RIFLE,                       BattleRifleFireScriptProcedure::execute,                RifleActionScriptProcedure::execute);

        var bolt = new FireArmActions(          BoltFireScriptProcedure::execute,                       BoltActionRifleEntitySwingsItemProcedure::execute);
        put(BOLT_ACTION_RIFLE_ANIMATED,         bolt);
        put(SCOPED_BOLT_ACTION_RIFLE_ANIMATED,  bolt);
        
        put(BREAK_ACTION_SHOTGUN_ANIMATED,      BreakActionFireScriptProcedure::execute,                EMPTY);
        put(PUMP_ACTION_SHOTGUN_ANIMATED,       ShotgunFireScriptProcedure::execute,                    ShotgunActionScriptProcedure::execute);

        var breech = new FireArmActions(        BreechRifleFireScriptProcedure::execute,                EMPTY);
        put(BREECH_RIFLE,                       breech);
        put(SCOPED_BREECH_RIFLE,                breech);

        put(SINGLE_SHOT_RIFLE,                  SingleShotFireScriptProcedure::execute,                 EMPTY);
        put(FLAME_THROWER_ANIMATED,             FlameThrowerDoubleFireProcedure::execute,               EMPTY);
        put(GRENADE_LAUNCHER,                   GrenadeLauncherFireProcedure::execute,                  EMPTY);
        put(ARMOR_PEELER_ANIMATED,              ArmorPeelerRightclickedProcedure::execute,              EMPTY);
        put(FLARE_PISTOL,                       FlarePistolFireScriptProcedure::execute,                EMPTY);
        put(ERADICATION,                        SMGDoubleFireProcedure::execute,                        EMPTY);
    }

    private static void put(RegistryObject<Item> gun, FireArmActions actions) {
        ACTIONS.put(gun.get(), actions);
    }

    private static void put(RegistryObject<Item> gun, FireArmAction shoot, FireArmAction bolt) {
        put(gun, new FireArmActions(shoot, bolt));
    }

    private static void put(RegistryObject<Item> gun, FireArmActionAlt shoot, FireArmAction bolt) {
        put(gun, new FireArmActions(shoot, bolt));
    }

    public static FireArmActions get(ItemStack stack) {
        return ACTIONS.get(stack.getItem());
    }

    @FunctionalInterface 
    public interface FireArmAction {
        void accept(Level level, double x, double y, double z, Entity entity, ItemStack stack);
    }

    @FunctionalInterface 
    public interface FireArmActionAlt extends FireArmAction {
        void accept(ItemStack stack);

        default void accept(Level level, double x, double y, double z, Entity entity, ItemStack stack) {
            accept(stack);
        }
    }
}