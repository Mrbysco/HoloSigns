package com.mrbysco.holosigns.datagen.client;

import com.mrbysco.holosigns.HoloSignsMod;
import com.mrbysco.holosigns.registry.SignReg;
import com.mrbysco.holosigns.registry.SignRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NonNull;

public class SignModelProvider extends ModelProvider {
	public SignModelProvider(PackOutput packOutput) {
		super(packOutput, HoloSignsMod.MOD_ID);
	}

	@Override
	protected void registerModels(@NonNull BlockModelGenerators blockModels, @NonNull ItemModelGenerators itemModels) {
		for (SignReg reg : SignRegistry.SIGNS) {
			blockModels.createHangingSign(Blocks.GLASS, reg.getSign().get(), reg.getWallSign().get());
			blockModels.createHangingSign(Blocks.GLASS, reg.getHangingSign().get(), reg.getWallHangingSign().get());
		}
	}
}
