package com.harleyoconnor.gamepieces.block;

import com.harleyoconnor.gamepieces.setup.Registry;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class ChessTileEntity extends TileEntity {

    public static final String PIECES_KEY = "pieces";

    private final ChessPieceData data = new ChessPieceData();

    public ChessTileEntity() {
        super(Registry.CHESS_TILE_ENTITY.get());
    }

    public ChessPieceData getData() {
        return data;
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        super.load(state, tag);
        loadData(tag);
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        super.save(tag);
        tag.putInt(PIECES_KEY, data.toRaw());
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
            data.setFromRaw(tag.getInt(PIECES_KEY));
        }
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return save(new CompoundNBT());
    }

}
