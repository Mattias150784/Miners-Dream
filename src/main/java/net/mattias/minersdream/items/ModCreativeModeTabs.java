package net.mattias.minersdream.items;

import net.mattias.minersdream.MinersDream;
import net.mattias.minersdream.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MinersDream.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MINERS_DREAM = CREATIVE_MODE_TABS.register("miners_dream",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MINERS_DREAM.get()))
                    .title(Component.translatable("creativetab.miners_dream"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.MINERS_DREAM.get());

                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}