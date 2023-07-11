package com.harleyoconnor.gamepieces.block;

public abstract class PieceData<P> {

    protected final P[] pieces;
    private final P emptyPiece;

    public PieceData(P[] pieces, P emptyPiece) {
        assert pieces.length == 4;
        this.pieces = pieces;
        this.emptyPiece = emptyPiece;
        setEmpty();
    }

    public void setEmpty() {
        pieces[0] = pieces[1] = pieces[2] = pieces[3] = emptyPiece;
    }

    public boolean setPiece(int square, P piece) {
        if (square >= 0 && square <= 3) {
            if (pieces[square] != piece) {
                pieces[square] = piece;
                return true;
            }
        }

        return false;
    }

    public P getPiece(int square) {
        if (square >= 0 && square <= 3) {
            return pieces[square];
        }

        return emptyPiece;
    }

    public abstract int toRaw();

    public abstract void setFromRaw(int raw);

    public void copyFrom(PieceData<P> other) {
        for (int i = 0; i < 4; i++) {
            pieces[i] = other.pieces[i];
        }
    }

}
