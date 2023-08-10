package com.harleyoconnor.gamepieces.block;

import com.harleyoconnor.gamepieces.setup.Registry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class PiecesBlockEntity<P extends Piece> extends BlockEntity {

    public static final String PIECES_KEY = "pieces";

    private final PieceData<P> pieces;

    public PiecesBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, PieceData<P> pieces) {
        super(type, pos, state);
        this.pieces = pieces;
    }

    public PieceData<P> getPieces() {
        return pieces;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(PIECES_KEY)) {
            pieces.setFromRaw(tag.getInt(PIECES_KEY));
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(PIECES_KEY, pieces.toRaw());
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.putInt(PIECES_KEY, pieces.toRaw());
        return tag;
    }

    public static final class Chess extends PiecesBlockEntity<ChessPiece> {
        public Chess(BlockPos pos, BlockState state) {
            super(Registry.CHESS_BLOCK_ENTITY.get(), pos, state, new ChessPieceData());
        }
    }

    public static final class Checkers extends PiecesBlockEntity<CheckersPiece> {
        public Checkers(BlockPos pos, BlockState state) {
            super(Registry.CHECKERS_BLOCK_ENTITY.get(), pos, state, new CheckersPieceData());
        }
    }

}
