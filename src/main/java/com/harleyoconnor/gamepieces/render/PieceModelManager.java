package com.harleyoconnor.gamepieces.render;

import com.harleyoconnor.gamepieces.GamePieces;
import com.harleyoconnor.gamepieces.block.Piece;
import com.harleyoconnor.gamepieces.block.PieceColor;
import com.harleyoconnor.gamepieces.block.PieceType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

import java.util.stream.Stream;

public class PieceModelManager<P extends Piece> {

    private final String modelFolderName;
    private final PieceType[] types;
    private final PieceColor[] colors;
    private final IBakedModel[] models;

    public PieceModelManager(String modelFolderName, PieceType[] types, PieceColor[] colors) {
        this.modelFolderName = modelFolderName;
        this.types = Stream.of(types).filter(PieceType::shouldRender).toArray(PieceType[]::new);
        this.colors = colors;
        this.models = new IBakedModel[this.types.length * colors.length];
    }

    public void addModels() {
        for (PieceType type : types) {
            ModelLoader.addSpecialModel(getModelLocation(type));
            for (PieceColor color : colors) {
                ModelLoader.addSpecialModel(getModelLocation(type, color));
            }
        }
    }

    public void cacheModels() {
        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < colors.length; j++) {
                models[i + j * types.length] = getModel(types[i], colors[j]);
            }
        }
    }
    
    private IBakedModel getModel(PieceType type, PieceColor color) {
        return Minecraft.getInstance().getModelManager().getModel(getModelLocation(type, color));
    }

    private ResourceLocation getModelLocation(PieceType type) {
        return GamePieces.location("block/" + modelFolderName + "/" + type.name().toLowerCase());
    }

    private ResourceLocation getModelLocation(PieceType type, PieceColor color) {
        return GamePieces.location("block/" + modelFolderName + "/" + color.name().toLowerCase() + "_" + type.name().toLowerCase());
    }

    public IBakedModel getModel(P piece) {
        return models[(piece.getType().ordinal() - 1) + (piece.getColor().ordinal() * types.length)];
    }

}
