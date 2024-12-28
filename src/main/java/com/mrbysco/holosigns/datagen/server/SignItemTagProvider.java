package com.mrbysco.holosigns.datagen.server;

import com.mrbysco.holosigns.HoloSignsMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class SignItemTagProvider extends ItemTagsProvider {

	public SignItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
	                           TagsProvider<Block> blockTagProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, blockTagProvider.contentsGetter(), HoloSignsMod.MOD_ID, existingFileHelper);
	}

	@Override
	public void addTags(HolderLookup.Provider lookupProvider) {
		this.tag(HoloSignsMod.MEDIUM).add(Items.PHANTOM_MEMBRANE);
	}
}
