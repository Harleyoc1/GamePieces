package com.harleyoconnor.gamepieces.block;

public class ChessPieceData {

    private final ChessPiece[] pieces = new ChessPiece[4];

    public ChessPieceData() {
        setEmpty();
    }

    public void setEmpty() {
        pieces[0] = pieces[1] = pieces[2] = pieces[3] = ChessPiece.EMPTY;
    }

    public boolean setPiece(int square, ChessPiece piece) {
        if (square >= 0 && square <= 3) {
            if (pieces[square] != piece) {
                pieces[square] = piece;
                return true;
            }
        }

        return false;
    }

    public ChessPiece getPiece(int square) {
        if (square >= 0 && square <= 3) {
            return pieces[square];
        }

        return ChessPiece.EMPTY;
    }

    public void setFromRaw(int raw) {
        for (int i = 0; i < 4; i++) {
            pieces[i] = ChessPiece.fromNybble((byte) ((raw >> (i * 4)) & 0xf));
        }
    }

    public int toRaw() {
        int raw = 0;
        for (int i = 0; i < 4; i++) {
            raw |= pieces[i].toNybble() << (i * 4);
        }
        return raw;
    }

    public void copyFrom(ChessPieceData other) {
        for (int i = 0; i < 4; i++) {
            pieces[i] = other.pieces[i];
        }
    }

    @Override
    public String toString() {
        return Integer.toHexString(toRaw());
    }

}
