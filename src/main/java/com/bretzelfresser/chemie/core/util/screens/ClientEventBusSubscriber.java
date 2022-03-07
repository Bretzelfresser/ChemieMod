package com.bretzelfresser.chemie.core.util.screens;

import com.bretzelfresser.chemie.Chemie;
import com.bretzelfresser.chemie.client.render.tileEntity.ReactionChamberRenderer;
import com.bretzelfresser.chemie.client.screens.FridgeScreen;
import com.bretzelfresser.chemie.client.screens.ReactionChamberScreen;
import com.bretzelfresser.chemie.client.screens.SifterScreen;
import com.bretzelfresser.chemie.core.init.ContainerTypeInit;
import com.bretzelfresser.chemie.core.init.TileEntityTypeInit;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Chemie.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		ScreenManager.registerFactory(ContainerTypeInit.FRIDGE_CONTAINER_TYPE.get(), FridgeScreen::new);
		ScreenManager.registerFactory(ContainerTypeInit.SIFTER_CONTAINER_TYPE.get(), SifterScreen::new);
		ScreenManager.registerFactory(ContainerTypeInit.REACTION_CHAMBER_CONTAINER_TYPE.get(),
				ReactionChamberScreen::new);
		
		ClientRegistry.bindTileEntityRenderer(TileEntityTypeInit.REACTION_CHAMBER_TILE_ENTITY.get(), ReactionChamberRenderer::new);
	}

}
