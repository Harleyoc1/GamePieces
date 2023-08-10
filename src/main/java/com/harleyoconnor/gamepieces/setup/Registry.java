package com.harleyoconnor.gamepieces.setup;

import com.harleyoconnor.gamepieces.GamePieces;
import com.harleyoconnor.gamepieces.block.PiecesBlock;
import com.harleyoconnor.gamepieces.block.PiecesBlockEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Registry {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, GamePieces.MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GamePieces.MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, GamePieces.MOD_ID);

    public static void setup(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
    }

    public static final RegistryObject<PiecesBlock> CHESS_BLOCK = BLOCKS.register("chess", PiecesBlock.Chess::new);
    public static final RegistryObject<PiecesBlock> CHECKERS_BLOCK = BLOCKS.register("checkers", PiecesBlock.Checkers::new);

    public static final RegistryObject<BlockEntityType<PiecesBlockEntity.Chess>> CHESS_BLOCK_ENTITY = BLOCK_ENTITIES.register("chess",
            () -> BlockEntityType.Builder.of(PiecesBlockEntity.Chess::new, CHESS_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<PiecesBlockEntity.Checkers>> CHECKERS_BLOCK_ENTITY = BLOCK_ENTITIES.register("checkers",
            () -> BlockEntityType.Builder.of(PiecesBlockEntity.Checkers::new, CHECKERS_BLOCK.get()).build(null));

}
