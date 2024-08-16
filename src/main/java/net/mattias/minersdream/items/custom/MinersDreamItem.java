package net.mattias.minersdream.items.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.Blocks;

public class MinersDreamItem extends Item {
    public MinersDreamItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (!level.isClientSide) {
            Vec3 lookVec = context.getPlayer().getLookAngle();
            clearArea(level, pos, lookVec);
        }
        // Remove the item from the player's inventory after use
        context.getItemInHand().shrink(1);
        return InteractionResult.SUCCESS;
    }

    private void clearArea(Level level, BlockPos startPos, Vec3 direction) {
        int width = 2;  // Half-width for 5x5 area
        int height = 3; // Height to clear above the clicked position
        int depth = 15; // Depth of the area

        // Determine the primary direction the player is looking
        int dx = Math.abs(direction.x) > Math.abs(direction.z) ? (int) Math.signum(direction.x) : 0;
        int dz = Math.abs(direction.z) > Math.abs(direction.x) ? (int) Math.signum(direction.z) : 0;

        for (int x = -width; x <= width; x++) {
            for (int z = 0; z <= depth; z++) {
                // Calculate the position based on the player's view direction
                BlockPos currentPos = startPos.offset(dx * z + dz * x, 0, dz * z + dx * x);
                // Offset vertically upwards only
                for (int y = -1; y <= height; y++) {
                    BlockPos posToClear = currentPos.above(y);
                    if (shouldRemoveBlock(level, posToClear)) {
                        level.setBlock(posToClear, Blocks.AIR.defaultBlockState(), 3);
                    }
                }
            }
        }
    }

    private boolean shouldRemoveBlock(Level level, BlockPos pos) {
        return level.getBlockState(pos).is(Blocks.STONE) ||
                level.getBlockState(pos).is(Blocks.ANDESITE) ||
                level.getBlockState(pos).is(Blocks.DEEPSLATE) ||
                level.getBlockState(pos).is(Blocks.GRANITE) ||
                level.getBlockState(pos).is(Blocks.DIORITE) ||
                level.getBlockState(pos).is(Blocks.DIRT) ||
                level.getBlockState(pos).is(Blocks.GRAVEL) ||
                level.getBlockState(pos).is(Blocks.TUFF) ||
                level.getBlockState(pos).is(Blocks.SAND);

    }
}
