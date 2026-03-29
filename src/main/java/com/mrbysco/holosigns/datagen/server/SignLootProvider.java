package com.mrbysco.holosigns.datagen.server;

import com.mrbysco.holosigns.registry.SignReg;
import com.mrbysco.holosigns.registry.SignRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SignLootProvider extends LootTableProvider {
	public SignLootProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, Set.of(), List.of(
				new SubProviderEntry(StatueBlocks::new, LootContextParamSets.BLOCK)
		), lookupProvider);
	}

	private static class StatueBlocks extends BlockLootSubProvider {

		protected StatueBlocks(HolderLookup.Provider registries) {
			super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
		}

		@Override
		protected void generate() {
			for (SignReg reg : SignRegistry.SIGNS) {
				dropSelf(reg.getSign().get());
				dropSelf(reg.getHangingSign().get());
			}
		}

		@Override
		protected @NonNull Iterable<Block> getKnownBlocks() {
			return SignRegistry.BLOCKS.getEntries().stream().map(holder -> (Block) holder.get())::iterator;
		}
	}

}
