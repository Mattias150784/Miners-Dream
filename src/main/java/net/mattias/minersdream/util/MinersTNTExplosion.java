package net.mattias.minersdream.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

public class MinersTNTExplosion extends Explosion {

    private final Set<Block> allowedBlocks;

    public MinersTNTExplosion(Level world, Entity exploder, double x, double y, double z, float size, boolean causesFire, BlockInteraction interaction, Set<Block> allowedBlocks) {
        super(world, exploder, null, null, x, y, z, size, causesFire, interaction);
        this.allowedBlocks = allowedBlocks;
    }

    @Override
    public void explode() {
        super.explode();

        try {

            Field toBlowField = Explosion.class.getDeclaredField("toBlow");
            toBlowField.setAccessible(true);
            List<BlockPos> toBlow = (List<BlockPos>) toBlowField.get(this);

            Field levelField = Explosion.class.getDeclaredField("level");
            levelField.setAccessible(true);
            Level level = (Level) levelField.get(this);

            for (BlockPos blockPos : toBlow) {
                Block block = level.getBlockState(blockPos).getBlock();
                if (allowedBlocks.contains(block)) {
                    level.destroyBlock(blockPos, true);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
