package com.harleyoconnor.gamepieces.setup;

import com.harleyoconnor.gamepieces.GamePieces;
import com.harleyoconnor.gamepieces.block.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registry {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, GamePieces.MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GamePieces.MOD_ID);
    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, GamePieces.MOD_ID);

    public static void setup(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        TILE_ENTITIES.register(modEventBus);
    }

    public static final RegistryObject<PiecesBlock> CHESS_BLOCK = BLOCKS.register("chess", PiecesBlock.Chess::new);
    public static final RegistryObject<PiecesBlock> CHECKERS_BLOCK = BLOCKS.register("checkers", PiecesBlock.Checkers::new);

    public static final RegistryObject<TileEntityType<PiecesTileEntity.Chess>> CHESS_TILE_ENTITY = TILE_ENTITIES.register("chess",
            () -> TileEntityType.Builder.of(PiecesTileEntity.Chess::new, CHESS_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<PiecesTileEntity.Checkers>> CHECKERS_TILE_ENTITY = TILE_ENTITIES.register("checkers",
            () -> TileEntityType.Builder.of(PiecesTileEntity.Checkers::new, CHECKERS_BLOCK.get()).build(null));

}
