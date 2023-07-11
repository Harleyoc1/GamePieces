package com.harleyoconnor.gamepieces.datagen;

import com.harleyoconnor.gamepieces.GamePieces;
import com.harleyoconnor.gamepieces.block.CheckersPiece;
import com.harleyoconnor.gamepieces.block.ChessPiece;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;

public class BlockModelGenerator extends BlockStateProvider {

    public BlockModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, GamePieces.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (ChessPiece.Type type : Arrays.copyOfRange(ChessPiece.Type.values(), 1, ChessPiece.Type.values().length)) {
            generateColoredVariants(type.name().toLowerCase(), "gamepieces:block/chess/");
        }

        for (CheckersPiece.Type type : Arrays.copyOfRange(CheckersPiece.Type.values(), 1, CheckersPiece.Type.values().length)) {
            generateColoredVariants(type.name().toLowerCase(), "gamepieces:block/checkers/");
        }
    }

    private void generateColoredVariants(String name, String basePath) {
        String whiteName = "white_" + name;
        String blackName = "black_" + name;
        models().withExistingParent(basePath + whiteName, basePath + name)
                .texture("all", "minecraft:block/white_concrete");
        models().withExistingParent(basePath + blackName, basePath + name)
                .texture("all", "minecraft:block/black_concrete");
    }

}
