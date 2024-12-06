package net.mattias.minersdream.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.mattias.minersdream.MinersDream;

public class ModModelLayer {

    public static final ModelLayerLocation MINERS_TNT_LAYER = new ModelLayerLocation(
             ResourceLocation.fromNamespaceAndPath(MinersDream.MOD_ID, "miners_tnt"), "main");

    public static LayerDefinition createMinersTNTLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();


        return LayerDefinition.create(meshdefinition, 64, 32);
    }
}