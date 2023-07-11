package com.harleyoconnor.gamepieces.block;

import com.harleyoconnor.gamepieces.setup.Registry;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nullable;

public abstract class PiecesTileEntity<P extends Piece> extends TileEntity {

    public static final String PIECES_KEY = "pieces";

    private final PieceData<P> pieces;

    public PiecesTileEntity(TileEntityType<?> tileEntityType, PieceData<P> pieces) {
        super(tileEntityType);
        this.pieces = pieces;
    }

    public PieceData<P> getPieces() {
        return pieces;
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        super.load(state, tag);
        loadData(tag);
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        super.save(tag);
        tag.putInt(PIECES_KEY, pieces.toRaw());
        return tag;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(worldPosition, -1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager networkManager, SUpdateTileEntityPacket packet) {
        loadData(packet.getTag());
    }

    private void loadData(CompoundNBT tag) {
        if (tag.contains(PIECES_KEY)) {
            pieces.setFromRaw(tag.getInt(PIECES_KEY));
        }
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return save(new CompoundNBT());
    }

    public static final class Chess extends PiecesTileEntity<ChessPiece> {
        public Chess() {
            super(Registry.CHESS_TILE_ENTITY.get(), new ChessPieceData());
        }
    }

    public static final class Checkers extends PiecesTileEntity<CheckersPiece> {
        public Checkers() {
            super(Registry.CHECKERS_TILE_ENTITY.get(), new CheckersPieceData());
        }
    }

}
