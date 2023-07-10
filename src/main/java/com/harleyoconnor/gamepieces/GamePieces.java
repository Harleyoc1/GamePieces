package com.harleyoconnor.gamepieces;

import com.harleyoconnor.gamepieces.command.GamePiecesCommands;
import com.harleyoconnor.gamepieces.setup.ClientSetup;
import com.harleyoconnor.gamepieces.setup.Registry;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GamePieces.MOD_ID)
public class GamePieces {

    public static final String MOD_ID = "gamepieces";

    public GamePieces() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        Registry.setup(modBus);
        modBus.addListener(ClientSetup::setup);
        modBus.addListener(ClientSetup::onModelRegistry);
        modBus.addListener(ClientSetup::onModelBake);
        MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
    }

    private void registerCommands(RegisterCommandsEvent event) {
        GamePiecesCommands.register(event.getDispatcher());
    }

    public static ResourceLocation location(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

}