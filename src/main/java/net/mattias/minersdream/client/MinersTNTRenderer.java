package net.mattias.minersdream.client;

import net.mattias.minersdream.blocks.ModBlocks;
import net.mattias.minersdream.entities.MinersTNTPrimed;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;

public class MinersTNTRenderer extends EntityRenderer<MinersTNTPrimed> {
    private final BlockRenderDispatcher blockRenderer;

    public MinersTNTRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(MinersTNTPrimed entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(-0.5D, 0.0D, -0.5D);

        BlockState blockState = ModBlocks.MINERS_TNT.get().defaultBlockState(); // Replace with your custom block
        blockRenderer.renderSingleBlock(blockState, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(MinersTNTPrimed entity) {
        return null;
    }
}
