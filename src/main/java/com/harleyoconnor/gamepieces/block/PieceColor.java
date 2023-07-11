package com.harleyoconnor.gamepieces.block;

import net.minecraft.util.IStringSerializable;

public enum PieceColor implements IStringSerializable {
    WHITE,
    BLACK;

    @Override
    public String getSerializedName() {
        return name();
    }
}
