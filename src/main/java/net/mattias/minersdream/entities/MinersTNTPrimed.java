package net.mattias.minersdream.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;

public class MinersTNTPrimed extends PrimedTnt {

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


}
