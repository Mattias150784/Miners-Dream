package net.mattias.minersdream.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.mattias.minersdream.entities.MinersTNTPrimed;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;


public class MinersTNTRenderer extends EntityRenderer<MinersTNTPrimed> {
    private final BlockRenderDispatcher blockRenderer;

    public MinersTNTRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(MinersTNTPrimed entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.5F, 0.0F);
        int fuse = entity.getFuse();

        if ((float)fuse - partialTicks + 1.0F < 10.0F) {
            float scale = 1.0F - ((float)fuse - partialTicks + 1.0F) / 10.0F;
            scale = net.minecraft.util.Mth.clamp(scale, 0.0F, 1.0F);
            scale *= scale;
            scale *= scale;
            float scaleFactor = 1.0F + scale * 0.3F;
            poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
        }

        poseStack.translate(-0.5F, -0.5F, -0.5F);
        renderCustomTNTBlock(poseStack, buffer, packedLight, fuse / 5 % 2 == 0);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    private void renderCustomTNTBlock(PoseStack poseStack, MultiBufferSource buffer, int packedLight, boolean shouldOverlay) {
        BlockState blockState = Blocks.TNT.defaultBlockState()
                .setValue(BlockStateProperties.UNSTABLE, false);
        blockRenderer.renderSingleBlock(blockState, poseStack, buffer, packedLight, shouldOverlay ? OverlayTexture.pack(OverlayTexture.u(1.0F), 10) : OverlayTexture.NO_OVERLAY);
    }

    @Override
    public ResourceLocation getTextureLocation(MinersTNTPrimed entity) {
        return new ResourceLocation("minersdream", "textures/block/miners_tnt_side");
    }
}
