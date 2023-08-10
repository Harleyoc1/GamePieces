package com.harleyoconnor.gamepieces.setup;

import com.harleyoconnor.gamepieces.GamePieces;
import com.harleyoconnor.gamepieces.block.CheckersPiece;
import com.harleyoconnor.gamepieces.block.ChessPiece;
import com.harleyoconnor.gamepieces.block.PieceColor;
import com.harleyoconnor.gamepieces.render.PieceBlockEntityRenderer;
import com.harleyoconnor.gamepieces.render.PieceModelManager;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = GamePieces.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    private static final PieceModelManager<ChessPiece> CHESS_MODEL_MANAGER = new PieceModelManager<>("chess", ChessPiece.Type.values(), PieceColor.values());
    private static final PieceModelManager<CheckersPiece> CHECKERS_MODEL_MANAGER = new PieceModelManager<>("checkers", CheckersPiece.Type.values(), PieceColor.values());

    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event) {
        registerRenderLayers();
    }

    private static void registerRenderLayers() {
        ItemBlockRenderTypes.setRenderLayer(Registry.CHESS_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(Registry.CHECKERS_BLOCK.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(Registry.CHESS_BLOCK_ENTITY.get(), context -> new PieceBlockEntityRenderer<>(CHESS_MODEL_MANAGER));
        event.registerBlockEntityRenderer(Registry.CHECKERS_BLOCK_ENTITY.get(), context -> new PieceBlockEntityRenderer<>(CHECKERS_MODEL_MANAGER));
    }

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        CHESS_MODEL_MANAGER.addModels();
        CHECKERS_MODEL_MANAGER.addModels();
    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        CHESS_MODEL_MANAGER.cacheModels();
        CHECKERS_MODEL_MANAGER.cacheModels();
    }

}
