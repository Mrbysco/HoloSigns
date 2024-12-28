package com.mrbysco.holosigns.datagen.client;

import com.mrbysco.holosigns.HoloSignsMod;
import com.mrbysco.holosigns.registry.SignReg;
import com.mrbysco.holosigns.registry.SignRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class SignBlockstateProvider extends BlockStateProvider {
	public SignBlockstateProvider(PackOutput packOutput, ExistingFileHelper helper) {
		super(packOutput, HoloSignsMod.MOD_ID, helper);
	}

	@Override
	protected void registerStatesAndModels() {
		for (SignReg reg : SignRegistry.SIGNS) {
			signBlock(reg.getSign().get(), reg.getWallSign().get(), mcLoc("block/glass"));
			hangingSignBlock(reg.getHangingSign().get(), reg.getWallHangingSign().get(), mcLoc("block/glass"));

			itemModels().basicItem(reg.getSignItem().asItem());
			itemModels().basicItem(reg.getHangingSignItem().asItem());
		}
	}

}
