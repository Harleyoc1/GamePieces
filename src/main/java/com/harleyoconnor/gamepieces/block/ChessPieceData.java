package com.harleyoconnor.gamepieces.block;

public class ChessPieceData extends PieceData<ChessPiece> {

    public ChessPieceData() {
        super(new ChessPiece[4], ChessPiece.EMPTY);
    }

    @Override
    public void setFromRaw(int raw) {
        for (int i = 0; i < 4; i++) {
            pieces[i] = ChessPiece.fromNybble((byte) ((raw >> (i * 4)) & 0xf));
        }
    }

    @Override
    public int toRaw() {
        int raw = 0;
        for (int i = 0; i < 4; i++) {
            raw |= pieces[i].toNybble() << (i * 4);
        }
        return raw;
    }

}
