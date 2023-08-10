package com.harleyoconnor.gamepieces.render;

import com.harleyoconnor.gamepieces.block.Piece;
import com.harleyoconnor.gamepieces.block.PieceData;
import com.harleyoconnor.gamepieces.block.PiecesBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.EmptyModelData;

public class PieceBlockEntityRenderer<P extends Piece> implements BlockEntityRenderer<PiecesBlockEntity<P>> {

    private final PieceModelManager<P> pieceModelManager;

    public PieceBlockEntityRenderer(PieceModelManager<P> pieceModelManager) {
        this.pieceModelManager = pieceModelManager;
    }

    @Override
    public void render(PiecesBlockEntity<P> tileEntity, float partialTick, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        PieceData<P> pieces = tileEntity.getPieces();
        for (int i = 0; i < 4; i++) {
            P piece = pieces.getPiece(i);

            if (piece.shouldRender()) {
                double x = (((i & 1) * 2) - 1) * -0.25;
                double z = ((i & 2) - 1) * 0.25;
                matrixStack.pushPose();
                matrixStack.translate(x, 0, z);
                renderBlockModel(matrixStack, buffer, tileEntity.getBlockState(), pieceModelManager.getModel(piece), combinedLight, combinedOverlay);
                matrixStack.popPose();
            }
        }

    }

    private static void renderBlockModel(PoseStack matrixStack, MultiBufferSource buffer, BlockState state, BakedModel model, int combinedLight, int combinedOverlay) {
        int color = Minecraft.getInstance().getBlockColors().getColor(state, null, null, 0);
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(
                matrixStack.last(),
                buffer.getBuffer(ItemBlockRenderTypes.getRenderType(state, false)),
                state,
                model,
                r, g, b,
                combinedLight,
                combinedOverlay,
                EmptyModelData.INSTANCE
        );
    }

}
