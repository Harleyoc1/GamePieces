package com.harleyoconnor.gamepieces.setup;

import com.harleyoconnor.gamepieces.GamePieces;
import com.harleyoconnor.gamepieces.block.ChessPiece;
import com.harleyoconnor.gamepieces.render.ChessTileEntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static void setup(FMLClientSetupEvent event) {
        registerRenderLayers();
        registerTileEntityRenderers();
    }

    private static void registerRenderLayers() {
        RenderTypeLookup.setRenderLayer(Registry.CHESS_BLOCK.get(), RenderType.cutout());
    }

    private static void registerTileEntityRenderers() {
        ClientRegistry.bindTileEntityRenderer(Registry.CHESS_TILE_ENTITY.get(), ChessTileEntityRenderer::new);
    }

    public static void onModelRegistry(ModelRegistryEvent event) {
        for (ChessPiece.Man man : ChessPiece.Man.validValues()) {
            String name = man.name().toLowerCase();
            ModelLoader.addSpecialModel(GamePieces.location("block/chess/" + name));
            ModelLoader.addSpecialModel(GamePieces.location("block/chess/white_" + name));
            ModelLoader.addSpecialModel(GamePieces.location("block/chess/black_" + name));
        }
    }

    public static void onModelBake(ModelBakeEvent event) {
        ChessTileEntityRenderer.cacheModels();
    }

}
