package net.mattias.minersdream.blocks.custom;

import net.mattias.minersdream.entities.MinersTNTPrimed;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Set;

public class MinersTNTBlock extends TntBlock {
    private static final Set<Block> ALLOWED_BLOCKS = Set.of(
            Blocks.STONE, Blocks.DIRT, Blocks.GRASS_BLOCK, Blocks.DEEPSLATE, Blocks.GRANITE, Blocks.ANDESITE, Blocks.GRAVEL, Blocks.DIORITE, Blocks.SAND, Blocks.TUFF
    );

    public MinersTNTBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void wasExploded(Level world, BlockPos pos, Explosion explosion) {
        if (!world.isClientSide) {
            MinersTNTPrimed customTNT = new MinersTNTPrimed(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, null, ALLOWED_BLOCKS);
            world.addFreshEntity(customTNT);
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (world.hasNeighborSignal(pos) || world.getBlockState(fromPos).getBlock() == Blocks.FIRE) {
            this.explode(world, pos, state, null);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player.getItemInHand(hand).getItem() == Items.FLINT_AND_STEEL || player.getItemInHand(hand).getItem() == Items.FIRE_CHARGE) {
            this.explode(world, pos, state, player);
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
            player.getItemInHand(hand).hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
            return InteractionResult.sidedSuccess(world.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    private void explode(Level world, BlockPos pos, BlockState state, LivingEntity igniter) {
        if (!world.isClientSide) {
            MinersTNTPrimed customTNT = new MinersTNTPrimed(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, igniter, ALLOWED_BLOCKS);
            world.addFreshEntity(customTNT);
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
            customTNT.setFuse(80);
            world.levelEvent(1008, pos, 0);
        }
    }
}
