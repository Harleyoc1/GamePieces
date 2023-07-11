package com.harleyoconnor.gamepieces.block;

public interface Piece {

    default boolean shouldRender() {
        return getType().shouldRender();
    }

    PieceType getType();

    PieceColor getColor();

}
