package com.mrbysco.holosigns.datagen;

import com.mrbysco.holosigns.datagen.client.SignLanguageProvider;
import com.mrbysco.holosigns.datagen.client.SignModelProvider;
import com.mrbysco.holosigns.datagen.server.SignBlockTagProvider;
import com.mrbysco.holosigns.datagen.server.SignItemTagProvider;
import com.mrbysco.holosigns.datagen.server.SignLootProvider;
import com.mrbysco.holosigns.datagen.server.SignRecipeProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class SignDataGenerator {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new SignRecipeProvider.Runner(packOutput, lookupProvider));
		generator.addProvider(true, new SignBlockTagProvider(packOutput, lookupProvider));
		generator.addProvider(true, new SignItemTagProvider(packOutput, lookupProvider));
		generator.addProvider(true, new SignLootProvider(packOutput, lookupProvider));

		generator.addProvider(true, new SignLanguageProvider(packOutput));
		generator.addProvider(true, new SignModelProvider(packOutput));
		
	}
}
