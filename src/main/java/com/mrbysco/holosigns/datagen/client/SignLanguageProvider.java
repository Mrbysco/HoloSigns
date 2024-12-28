package com.mrbysco.holosigns.datagen.client;

import com.mrbysco.holosigns.HoloSignsMod;
import com.mrbysco.holosigns.registry.SignReg;
import com.mrbysco.holosigns.registry.SignRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class SignLanguageProvider extends LanguageProvider {

	public SignLanguageProvider(PackOutput packOutput) {
		super(packOutput, HoloSignsMod.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		add("itemGroup.holosigns", "HoloSigns");

		addSign(SignRegistry.GLASS, "Glass");
		addSign(SignRegistry.WHITE_STAINED_GLASS, "White Stained Glass");
		addSign(SignRegistry.ORANGE_STAINED_GLASS, "Orange Stained Glass");
		addSign(SignRegistry.MAGENTA_STAINED_GLASS, "Magenta Stained Glass");
		addSign(SignRegistry.LIGHT_BLUE_STAINED_GLASS, "Light Blue Stained Glass");
		addSign(SignRegistry.YELLOW_STAINED_GLASS, "Yellow Stained Glass");
		addSign(SignRegistry.LIME_STAINED_GLASS, "Lime Stained Glass");
		addSign(SignRegistry.PINK_STAINED_GLASS, "Pink Stained Glass");
		addSign(SignRegistry.GRAY_STAINED_GLASS, "Gray Stained Glass");
		addSign(SignRegistry.LIGHT_GRAY_STAINED_GLASS, "Light Gray Stained Glass");
		addSign(SignRegistry.CYAN_STAINED_GLASS, "Cyan Stained Glass");
		addSign(SignRegistry.PURPLE_STAINED_GLASS, "Purple Stained Glass");
		addSign(SignRegistry.BLUE_STAINED_GLASS, "Blue Stained Glass");
		addSign(SignRegistry.BROWN_STAINED_GLASS, "Brown Stained Glass");
		addSign(SignRegistry.GREEN_STAINED_GLASS, "Green Stained Glass");
		addSign(SignRegistry.RED_STAINED_GLASS, "Red Stained Glass");
		addSign(SignRegistry.BLACK_STAINED_GLASS, "Black Stained Glass");
	}

	/**
	 * Add translations for the signs
	 * @param reg The sign registry object
	 * @param name The name of the sign
	 */
	private void addSign(SignReg reg, String name) {
		add("block.holosigns." + reg.getSign().getId().getPath(), name + " Sign");
		add("block.holosigns." + reg.getWallSign().getId().getPath(), name + " Wall Sign");
		add("block.holosigns." + reg.getHangingSign().getId().getPath(), name + " Hanging Sign");
		add("block.holosigns." + reg.getWallHangingSign().getId().getPath(), name + " Wall Hanging Sign");
	}
}
