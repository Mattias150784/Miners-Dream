package net.mattias.minersdream.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.HashSet;
import java.util.Set;

public class MinersTNTPrimed extends PrimedTnt {


    private static final Set<Block> DESTROYABLE_BLOCKS = new HashSet<>();

    static {
        DESTROYABLE_BLOCKS.add(Blocks.STONE);
        DESTROYABLE_BLOCKS.add(Blocks.DIRT);
        DESTROYABLE_BLOCKS.add(Blocks.SAND);
        DESTROYABLE_BLOCKS.add(Blocks.DEEPSLATE);
        DESTROYABLE_BLOCKS.add(Blocks.GRANITE);
        DESTROYABLE_BLOCKS.add(Blocks.ANDESITE);
        DESTROYABLE_BLOCKS.add(Blocks.DIORITE);
        DESTROYABLE_BLOCKS.add(Blocks.GRAVEL);
        DESTROYABLE_BLOCKS.add(Blocks.TUFF);



    }

    public MinersTNTPrimed(EntityType<? extends PrimedTnt> entityType, Level level) {
        super(entityType, level);
    }

    public MinersTNTPrimed(Level level, double x, double y, double z, LivingEntity igniter) {
        super(level, x, y, z, igniter);
    }

    @Override
    public void tick() {
        // Custom behavior can be added here
        super.tick();
    }

    @Override
    protected void explode() {
        // Explode method logic
        if (!this.level().isClientSide) {
            // Exploding radius logic
            int explosionRadius = 4; // You can adjust the explosion radius
            BlockPos blockpos = this.blockPosition();

            for (int x = -explosionRadius; x <= explosionRadius; x++) {
                for (int y = -explosionRadius; y <= explosionRadius; y++) {
                    for (int z = -explosionRadius; z <= explosionRadius; z++) {
                        BlockPos targetPos = blockpos.offset(x, y, z);
                        Block block = this.level().getBlockState(targetPos).getBlock();

                        if (DESTROYABLE_BLOCKS.contains(block)) {
                            this.level().destroyBlock(targetPos, true);
                        }
                    }
                }
            }
        }
    }
}