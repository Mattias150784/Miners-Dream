package net.mattias.minersdream.entities;

import net.mattias.minersdream.MinersDream;
import net.mattias.minersdream.entities.MinersTNTPrimed;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MinersDream.MOD_ID);

    public static final RegistryObject<EntityType<MinersTNTPrimed>> MINERS_TNT_PRIMED = ENTITY_TYPES.register("miners_tnt_primed",
            () -> EntityType.Builder.<MinersTNTPrimed>of(MinersTNTPrimed::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .build(MinersDream.MOD_ID + ":miners_tnt"));
}