package com.harleyoconnor.gamepieces.render;

import com.harleyoconnor.gamepieces.GamePieces;
import com.harleyoconnor.gamepieces.block.ChessPiece;
import com.harleyoconnor.gamepieces.block.ChessPieceData;
import com.harleyoconnor.gamepieces.block.ChessTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.client.model.data.EmptyModelData;

import java.util.Arrays;

public class ChessTileEntityRenderer extends TileEntityRenderer<ChessTileEntity> {

    private static final IBakedModel[] models = new IBakedModel[12];

    public ChessTileEntityRenderer(TileEntityRendererDispatcher tileEntityRenderer) {
        super(tileEntityRenderer);
    }

    public static void cacheModels() {
        int i = 0;
        for (ChessPiece.Man man : Arrays.copyOfRange(ChessPiece.Man.values(), 1, ChessPiece.Man.values().length)) {
            String name = man.name().toLowerCase();
            models[i] = Minecraft.getInstance().getModelManager().getModel(GamePieces.location("block/chess/white_" + name));
            models[(i++) + 6] = Minecraft.getInstance().getModelManager().getModel(GamePieces.location("block/chess/black_" + name));
        }
    }

    @Override
    public void render(ChessTileEntity tileEntity, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        ChessPieceData pieces = tileEntity.getData();
        for (int i = 0; i < 4; i++) {
            ChessPiece piece = pieces.getPiece(i);
            ChessPiece.Man man = piece.getMan();
            ChessPiece.Color color = piece.getColor();


            if (man != ChessPiece.Man.NONE) {
                double x = (((i & 1) * 2) - 1) * -0.25;
                double z = ((i & 2) - 1) * 0.25;
                matrixStack.pushPose();
                matrixStack.translate(x, 0, z);
                renderBlockModel(matrixStack, buffer, tileEntity.getBlockState(), getModel(man, color), combinedLight, combinedOverlay);
                matrixStack.popPose();
            }
        }
    }

    private static IBakedModel getModel(ChessPiece.Man man, ChessPiece.Color color) {
        return models[(man.ordinal() - 1) + (color.ordinal() * 6)];
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
