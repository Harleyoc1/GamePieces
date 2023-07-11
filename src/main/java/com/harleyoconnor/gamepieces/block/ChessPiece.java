package com.harleyoconnor.gamepieces.block;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;

public class ChessPiece implements Piece {

    public enum Type implements IStringSerializable, PieceType {
        NONE,
        PAWN,
        ROOK,
        BISHOP,
        KNIGHT,
        KING,
        QUEEN;

        @Override
        public String getSerializedName() {
            return name();
        }


        @Override
        public boolean shouldRender() {
            return this != NONE;
        }
    }

    public static final ChessPiece EMPTY = new ChessPiece(Type.NONE, PieceColor.WHITE);

    private final Type type;
    private final PieceColor color;

    public ChessPiece(Type type, PieceColor color) {
        this.type = type;
        this.color = color;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public PieceColor getColor() {
        return color;
    }

    public static ChessPiece fromNybble(byte data) {
        return new ChessPiece(Type.values()[MathHelper.clamp(data & 7, 0, 6)], PieceColor.values()[(data >> 3) & 1]);
    }

    public byte toNybble() {
        return (byte) (type.ordinal() | (color.ordinal() << 3));
    }

}