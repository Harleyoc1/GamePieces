package com.harleyoconnor.gamepieces.block;

import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;

public class CheckersPiece implements Piece {

    public enum Type implements StringRepresentable, PieceType {
        NONE,
        MAN,
        KING;

        @Override
        public String getSerializedName() {
            return name();
        }

        @Override
        public boolean shouldRender() {
            return this != NONE;
        }
    }

    public static final CheckersPiece EMPTY = new CheckersPiece(CheckersPiece.Type.NONE, PieceColor.WHITE);

    private final Type type;
    private final PieceColor color;

    public CheckersPiece(Type type, PieceColor color) {
        this.type = type;
        this.color = color;
    }

    @Override
    public PieceType getType() {
        return type;
    }

    @Override
    public PieceColor getColor() {
        return color;
    }

    public static CheckersPiece fromRaw(byte data) {
        return new CheckersPiece(CheckersPiece.Type.values()[Mth.clamp(data & 3, 0, 2)], PieceColor.values()[(data >> 2) & 1]);
    }

    public byte toRaw() {
        return (byte) (type.ordinal() | (color.ordinal() << 2));
    }

}
