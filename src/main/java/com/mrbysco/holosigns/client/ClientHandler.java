package com.mrbysco.holosigns.client;

import com.mrbysco.holosigns.registry.SignReg;
import com.mrbysco.holosigns.registry.SignRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHandler {

	@SuppressWarnings("deprecation")
	public static void onClientSetup(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			for (SignReg sign : SignRegistry.SIGNS) {
				Sheets.addWoodType(sign.getWoodType());
				ItemBlockRenderTypes.setRenderLayer(sign.getSign().get(), RenderType.TRANSLUCENT);
				ItemBlockRenderTypes.setRenderLayer(sign.getWallSign().get(), RenderType.TRANSLUCENT);
				ItemBlockRenderTypes.setRenderLayer(sign.getHangingSign().get(), RenderType.TRANSLUCENT);
				ItemBlockRenderTypes.setRenderLayer(sign.getWallHangingSign().get(), RenderType.TRANSLUCENT);
			}
		});
	}
}
