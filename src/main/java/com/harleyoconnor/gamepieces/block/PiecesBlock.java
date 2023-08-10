package com.harleyoconnor.gamepieces.block;

import com.harleyoconnor.gamepieces.setup.Registry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class PiecesBlock extends Block implements EntityBlock {

    public static final AABB FLAT = new AABB(0.0, 0.0, 0.0, 1.0, 1 / 32.0, 1.0);

    public PiecesBlock() {
        super(Properties.of(Material.STONE).strength(2.5F, 10.0F).noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.create(FLAT);
    }

    @Override
    public abstract BlockEntity newBlockEntity(BlockPos pos, BlockState state);

    public static final class Chess extends PiecesBlock {
        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return Registry.CHESS_BLOCK_ENTITY.get().create(pos, state);
        }
    }

    public static final class Checkers extends PiecesBlock {
        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return Registry.CHECKERS_BLOCK_ENTITY.get().create(pos, state);
        }
    }
}
