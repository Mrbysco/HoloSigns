package com.mrbysco.holosigns.datagen;

import com.mrbysco.holosigns.datagen.client.SignBlockstateProvider;
import com.mrbysco.holosigns.datagen.client.SignLanguageProvider;
import com.mrbysco.holosigns.datagen.server.SignBlockTagProvider;
import com.mrbysco.holosigns.datagen.server.SignItemTagProvider;
import com.mrbysco.holosigns.datagen.server.SignLootProvider;
import com.mrbysco.holosigns.datagen.server.SignRecipeProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class SignDataGenerator {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new SignRecipeProvider(packOutput, lookupProvider));
			SignBlockTagProvider blockTags = new SignBlockTagProvider(packOutput, lookupProvider, helper);
			generator.addProvider(event.includeServer(), blockTags);
			generator.addProvider(event.includeServer(), new SignItemTagProvider(packOutput, lookupProvider, blockTags, helper));
			generator.addProvider(event.includeServer(), new SignLootProvider(packOutput, lookupProvider));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new SignLanguageProvider(packOutput));
			generator.addProvider(event.includeClient(), new SignBlockstateProvider(packOutput, helper));
		}
	}
}
