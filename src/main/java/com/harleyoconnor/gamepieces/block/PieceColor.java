package com.harleyoconnor.gamepieces.block;

import net.minecraft.util.StringRepresentable;

public enum PieceColor implements StringRepresentable {
    WHITE,
    BLACK;

    @Override
    public String getSerializedName() {
        return name();
    }
}
