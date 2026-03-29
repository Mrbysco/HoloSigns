package com.mrbysco.holosigns.client;

import com.mrbysco.holosigns.registry.SignReg;
import com.mrbysco.holosigns.registry.SignRegistry;
import net.minecraft.client.renderer.Sheets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber
public class ClientHandler {

	@SubscribeEvent
	public static void onClientSetup(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			for (SignReg sign : SignRegistry.SIGNS) {
				Sheets.addWoodType(sign.getWoodType());
//				ItemBlockRenderTypes.setRenderLayer(sign.getSign().get(), ChunkSectionLayer.TRANSLUCENT);
//				ItemBlockRenderTypes.setRenderLayer(sign.getWallSign().get(), ChunkSectionLayer.TRANSLUCENT);
//				ItemBlockRenderTypes.setRenderLayer(sign.getHangingSign().get(), ChunkSectionLayer.TRANSLUCENT);
//				ItemBlockRenderTypes.setRenderLayer(sign.getWallHangingSign().get(), ChunkSectionLayer.TRANSLUCENT);
			}
		});
	}
}
