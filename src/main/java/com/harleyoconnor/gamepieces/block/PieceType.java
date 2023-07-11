package com.harleyoconnor.gamepieces.block;

public interface PieceType {

    boolean shouldRender();

    String name();

    int ordinal();

}
