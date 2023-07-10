package com.harleyoconnor.gamepieces.block;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;

public class ChessPiece {

    public enum Man implements IStringSerializable {
        NONE,
        PAWN,
        ROOK,
        BISHOP,
        KNIGHT,
        KING,
        QUEEN;

        private static final Man[] VALID_VALUES = Arrays.copyOfRange(values(), 1, values().length);

        @Override
        public String getSerializedName() {
            return name();
        }

        public static Man[] validValues() {
            return VALID_VALUES;
        }
    }

    public enum Color implements IStringSerializable {
        WHITE,
        BLACK;

        @Override
        public String getSerializedName() {
            return name();
        }
    }

    public static final ChessPiece EMPTY = new ChessPiece(Man.NONE, Color.WHITE);

    private final Man man;
    private final Color color;

    public ChessPiece(Man man, Color color) {
        this.man = man;
        this.color = color;
    }

    public Man getMan() {
        return man;
    }

    public Color getColor() {
        return color;
    }

    public static ChessPiece fromNybble(byte data) {
        return new ChessPiece(Man.values()[MathHelper.clamp(data & 7, 0, 6)], Color.values()[(data >> 3) & 1]);
    }

    public byte toNybble() {
        return (byte) (man.ordinal() | (color.ordinal() << 3));
    }

}