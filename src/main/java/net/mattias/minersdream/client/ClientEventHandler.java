package net.mattias.minersdream.client;

import net.mattias.minersdream.MinersDream;
import net.mattias.minersdream.client.MinersTNTRenderer;
import net.mattias.minersdream.entities.ModEntities;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class ClientEventHandler {
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.MINERS_TNT_PRIMED.get(), MinersTNTRenderer::new);
    }
}
