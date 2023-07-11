package com.harleyoconnor.gamepieces.block;

public class CheckersPieceData extends PieceData<CheckersPiece> {

    public CheckersPieceData() {
        super(new CheckersPiece[4], CheckersPiece.EMPTY);
    }

    @Override
    public void setFromRaw(int raw) {
        for (int i = 0; i < 4; i++) {
            pieces[i] = CheckersPiece.fromRaw((byte) ((raw >> (i * 3)) & 0x7));
        }
    }

    @Override
    public int toRaw() {
        int raw = 0;
        for (int i = 0; i < 4; i++) {
            raw |= pieces[i].toRaw() << (i * 3);
        }
        return raw;
    }
}
