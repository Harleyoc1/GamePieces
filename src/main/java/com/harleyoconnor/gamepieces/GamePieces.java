package com.harleyoconnor.gamepieces;

import com.harleyoconnor.gamepieces.command.ChessPiecePositionArgument;
import com.harleyoconnor.gamepieces.command.GamePiecesCommands;
import com.harleyoconnor.gamepieces.setup.Registry;

import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.EmptyArgumentSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GamePieces.MOD_ID)
public class GamePieces {

    public static final String MOD_ID = "gamepieces";

    public GamePieces() {
        Registry.setup(FMLJavaModLoadingContext.get().getModEventBus());
        MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
        
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
    }

    private void registerCommands(RegisterCommandsEvent event) {
        GamePiecesCommands.register(event.getDispatcher());
    }
    
    
    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork( () -> ArgumentTypes.register(new ResourceLocation( GamePieces.MOD_ID, "piece_position").toString(), ChessPiecePositionArgument.class, new EmptyArgumentSerializer<ChessPiecePositionArgument>(ChessPiecePositionArgument::chessPiecePosition)));
    }
    
    public static ResourceLocation location(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

}
