package crazywoddman.warium_additions.compat.create;

import java.util.function.BiFunction;

import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Shaper {
   public static Shaper.Builder shape(VoxelShape shape) {
      return new Shaper.Builder(shape);
   }

   public static Shaper.Builder shape(double x1, double y1, double z1, double x2, double y2, double z2) {
      return shape(cuboid(x1, y1, z1, x2, y2, z2));
   }

   public static VoxelShape cuboid(double x1, double y1, double z1, double x2, double y2, double z2) {
      return Block.box(x1, y1, z1, x2, y2, z2);
   }

   public static class Builder {
      VoxelShape shape;

      public Builder(VoxelShape shape) {
         this.shape = shape;
      }

      public Shaper.Builder add(VoxelShape shape) {
         this.shape = Shapes.or(this.shape, shape);
         return this;
      }

      public Shaper.Builder add(double x1, double y1, double z1, double x2, double y2, double z2) {
         return this.add(Shaper.cuboid(x1, y1, z1, x2, y2, z2));
      }

      public VoxelShaper build(BiFunction<VoxelShape, Direction, VoxelShaper> factory, Direction direction) {
         return factory.apply(this.shape, direction);
      }

      public VoxelShaper forDirectional(Direction direction) {
         return this.build(VoxelShaper::forDirectional, direction);
      }

      public VoxelShaper forDirectional() {
         return this.forDirectional(Direction.NORTH);
      }
   }
}
