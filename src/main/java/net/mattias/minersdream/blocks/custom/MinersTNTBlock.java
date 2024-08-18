package net.mattias.minersdream.blocks.custom;

import net.mattias.minersdream.entities.MinersTNTPrimed;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.Set;

public class MinersTNTBlock extends Block {
    public static final BooleanProperty UNSTABLE = BlockStateProperties.UNSTABLE;

    private static final Set<Block> ALLOWED_BLOCKS = Set.of(
            Blocks.STONE, Blocks.DIRT, Blocks.GRASS_BLOCK, Blocks.DEEPSLATE, Blocks.GRANITE, Blocks.ANDESITE, Blocks.GRAVEL, Blocks.DIORITE, Blocks.SAND, Blocks.TUFF
    );

    public MinersTNTBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(UNSTABLE, false));
    }

    public void onCaughtFire(BlockState state, Level world, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        if (!world.isClientSide) {
            MinersTNTPrimed tntEntity = new MinersTNTPrimed(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, igniter);
            world.addFreshEntity(tntEntity);
            world.playSound(null, tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
            world.gameEvent(igniter, GameEvent.PRIME_FUSE, pos);
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.is(state.getBlock()) && level.hasNeighborSignal(pos)) {
            this.onCaughtFire(state, level, pos, null, null);
            level.removeBlock(pos, false);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (level.hasNeighborSignal(pos)) {
            this.onCaughtFire(state, level, pos, null, null);
            level.removeBlock(pos, false);
        }
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && !player.isCreative() && state.getValue(UNSTABLE)) {
            this.onCaughtFire(state, level, pos, null, null);
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void wasExploded(Level level, BlockPos pos, Explosion explosion) {
        if (!level.isClientSide) {
            MinersTNTPrimed tntEntity = new MinersTNTPrimed(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, explosion.getIndirectSourceEntity());
            int fuse = tntEntity.getFuse();
            tntEntity.setFuse((short)(level.random.nextInt(fuse / 4) + fuse / 8));
            level.addFreshEntity(tntEntity);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.FLINT_AND_STEEL) || itemStack.is(Items.FIRE_CHARGE)) {
            this.onCaughtFire(state, level, pos, hit.getDirection(), player);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
            Item item = itemStack.getItem();
            if (!player.isCreative()) {
                if (itemStack.is(Items.FLINT_AND_STEEL)) {
                    itemStack.hurtAndBreak(1, player, (p_57425_) -> p_57425_.broadcastBreakEvent(hand));
                } else {
                    itemStack.shrink(1);
                }
            }
            player.awardStat(Stats.ITEM_USED.get(item));
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.use(state, level, pos, player, hand, hit);
        }
    }

    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!level.isClientSide) {
            BlockPos blockPos = hit.getBlockPos();
            Entity entity = projectile.getOwner();
            if (projectile.isOnFire() && projectile.mayInteract(level, blockPos)) {
                this.onCaughtFire(state, level, blockPos, null, entity instanceof LivingEntity ? (LivingEntity) entity : null);
                level.removeBlock(blockPos, false);
            }
        }
    }

    @Override
    public boolean dropFromExplosion(Explosion explosion) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UNSTABLE);
    }
}
