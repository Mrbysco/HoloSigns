package com.mrbysco.holosigns.datagen.server;

import com.mrbysco.holosigns.HoloSignsMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class SignBlockTagProvider extends BlockTagsProvider {
	public SignBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, HoloSignsMod.MOD_ID);
	}

	@Override
	protected void addTags(HolderLookup.@NonNull Provider provider) {

	}
}
