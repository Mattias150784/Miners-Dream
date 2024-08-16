package net.mattias.minersdream.entities;

import net.mattias.minersdream.util.MinersTNTExplosion;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.network.NetworkHooks;

import java.util.Set;

public class MinersTNTPrimed extends PrimedTnt {
    private static final EntityDataAccessor<Integer> FUSE_ID = SynchedEntityData.defineId(MinersTNTPrimed.class, EntityDataSerializers.INT);
    private final Set<Block> allowedBlocks;

    public MinersTNTPrimed(EntityType<? extends MinersTNTPrimed> entityType, Level world) {
        super(entityType, world);
        this.allowedBlocks = Set.of();
    }

    public MinersTNTPrimed(Level world, double x, double y, double z, LivingEntity igniter, Set<Block> allowedBlocks) {
        super(ModEntities.MINERS_TNT_PRIMED.get(), world);
        this.setPos(x, y, z);
        double d0 = world.random.nextDouble() * Math.PI * 2.0D;
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, 0.2D, -Math.cos(d0) * 0.02D);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.allowedBlocks = allowedBlocks;
        this.setFuse(80);
    }

    @Override
    protected void explode() {
        MinersTNTExplosion explosion = new MinersTNTExplosion(this.level(), this, this.getX(), this.getY(0.0625D), this.getZ(), 4.0F, false, Explosion.BlockInteraction.DESTROY, allowedBlocks);
        explosion.explode();
        explosion.finalizeExplosion(true);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
