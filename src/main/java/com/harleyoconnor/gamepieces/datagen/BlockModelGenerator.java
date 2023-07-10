package com.harleyoconnor.gamepieces.datagen;

import com.harleyoconnor.gamepieces.GamePieces;
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
        for (ChessPiece.Man man : Arrays.copyOfRange(ChessPiece.Man.values(), 1, ChessPiece.Man.values().length)) {
            String name = man.name().toLowerCase();
            String basePath = "gamepieces:block/chess/";
            String whiteName = "white_" + name;
            String blackName = "black_" + name;
            models().withExistingParent(basePath + whiteName, basePath + name)
                    .texture("all", "minecraft:block/white_concrete");
            models().withExistingParent(basePath + blackName, basePath + name)
                    .texture("all", "minecraft:block/black_concrete");
        }
    }

}
