package com.harleyoconnor.gamepieces.render;

import com.harleyoconnor.gamepieces.block.*;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.client.model.data.EmptyModelData;

public class PieceTileEntityRenderer<P extends Piece> extends TileEntityRenderer<PiecesTileEntity<P>> {

    private final PieceModelManager<P> pieceModelManager;

    public PieceTileEntityRenderer(TileEntityRendererDispatcher tileEntityRenderer, PieceModelManager<P> pieceModelManager) {
        super(tileEntityRenderer);
        this.pieceModelManager = pieceModelManager;
    }

    @Override
    public void render(PiecesTileEntity<P> tileEntity, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
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

    private static void renderBlockModel(MatrixStack matrixStack, IRenderTypeBuffer buffer, BlockState state, IBakedModel model, int combinedLight, int combinedOverlay) {
        int color = Minecraft.getInstance().getBlockColors().getColor(state, null, null, 0);
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(
                matrixStack.last(),
                buffer.getBuffer(RenderTypeLookup.getRenderType(state, false)),
                state,
                model,
                r, g, b,
                combinedLight,
                combinedOverlay,
                EmptyModelData.INSTANCE
        );
    }

}
