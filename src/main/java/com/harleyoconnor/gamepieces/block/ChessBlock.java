package com.harleyoconnor.gamepieces.block;

import com.harleyoconnor.gamepieces.setup.Registry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.EnumProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class ChessBlock extends Block {

    public static final AxisAlignedBB FLAT = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1 / 32.0, 1.0);

    public ChessBlock() {
        super(Properties.of(Material.STONE).strength(2.5F, 10.0F).noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
        return VoxelShapes.create(FLAT);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return Registry.CHESS_TILE_ENTITY.get().create();
    }
}
