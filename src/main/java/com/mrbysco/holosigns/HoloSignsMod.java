package com.mrbysco.holosigns;

import com.mojang.logging.LogUtils;
import com.mrbysco.holosigns.client.ClientHandler;
import com.mrbysco.holosigns.handler.SignHandler;
import com.mrbysco.holosigns.registry.SignRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

import java.util.Locale;

@Mod(HoloSignsMod.MOD_ID)
public class HoloSignsMod {
	public static final String MOD_ID = "holosigns";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static final TagKey<Item> MEDIUM = ItemTags.create(modLoc("medium"));

	public HoloSignsMod(IEventBus eventBus, Dist dist, ModContainer container) {
		SignRegistry.BLOCKS.register(eventBus);
		SignRegistry.ITEMS.register(eventBus);
		SignRegistry.BLOCK_ENTITIES.register(eventBus);
		SignRegistry.CREATIVE_MODE_TABS.register(eventBus);
		SignRegistry.ATTACHMENT_TYPES.register(eventBus);

		eventBus.addListener(SignRegistry::addBlockEntityTypes);
		NeoForge.EVENT_BUS.register(new SignHandler());
		if (dist.isClient()) {
			eventBus.addListener(ClientHandler::onClientSetup);
		}
	}

	public static ResourceLocation modLoc(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	public static ResourceLocation prefix(String name) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, name.toLowerCase(Locale.ROOT));
	}
}
