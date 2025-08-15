package com.mrbysco.holosigns.datagen.server;

import com.mrbysco.holosigns.HoloSignsMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class SignItemTagProvider extends ItemTagsProvider {

	public SignItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, HoloSignsMod.MOD_ID);
	}

	@Override
	public void addTags(HolderLookup.Provider lookupProvider) {
		this.tag(HoloSignsMod.MEDIUM).add(Items.PHANTOM_MEMBRANE);
	}
}
