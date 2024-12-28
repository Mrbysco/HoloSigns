package com.mrbysco.holosigns.registry;

import com.mojang.serialization.Codec;
import com.mrbysco.holosigns.HoloSignsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SignRegistry {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(HoloSignsMod.MOD_ID);
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HoloSignsMod.MOD_ID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, HoloSignsMod.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HoloSignsMod.MOD_ID);
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, HoloSignsMod.MOD_ID);

	public static final List<SignReg> SIGNS = new ArrayList<>();

	public static final SignReg GLASS = registerSign("glass", MapColor.NONE, SoundType.GLASS);
	public static final SignReg WHITE_STAINED_GLASS = registerSign("white_stained_glass", DyeColor.WHITE.getMapColor(), SoundType.GLASS);
	public static final SignReg ORANGE_STAINED_GLASS = registerSign("orange_stained_glass", DyeColor.ORANGE.getMapColor(), SoundType.GLASS);
	public static final SignReg MAGENTA_STAINED_GLASS = registerSign("magenta_stained_glass", DyeColor.MAGENTA.getMapColor(), SoundType.GLASS);
	public static final SignReg LIGHT_BLUE_STAINED_GLASS = registerSign("light_blue_stained_glass", DyeColor.LIGHT_BLUE.getMapColor(), SoundType.GLASS);
	public static final SignReg YELLOW_STAINED_GLASS = registerSign("yellow_stained_glass", DyeColor.YELLOW.getMapColor(), SoundType.GLASS);
	public static final SignReg LIME_STAINED_GLASS = registerSign("lime_stained_glass", DyeColor.LIME.getMapColor(), SoundType.GLASS);
	public static final SignReg PINK_STAINED_GLASS = registerSign("pink_stained_glass", DyeColor.PINK.getMapColor(), SoundType.GLASS);
	public static final SignReg GRAY_STAINED_GLASS = registerSign("gray_stained_glass", DyeColor.GRAY.getMapColor(), SoundType.GLASS);
	public static final SignReg LIGHT_GRAY_STAINED_GLASS = registerSign("light_gray_stained_glass", DyeColor.LIGHT_GRAY.getMapColor(), SoundType.GLASS);
	public static final SignReg CYAN_STAINED_GLASS = registerSign("cyan_stained_glass", DyeColor.CYAN.getMapColor(), SoundType.GLASS);
	public static final SignReg PURPLE_STAINED_GLASS = registerSign("purple_stained_glass", DyeColor.PURPLE.getMapColor(), SoundType.GLASS);
	public static final SignReg BLUE_STAINED_GLASS = registerSign("blue_stained_glass", DyeColor.BLUE.getMapColor(), SoundType.GLASS);
	public static final SignReg BROWN_STAINED_GLASS = registerSign("brown_stained_glass", DyeColor.BROWN.getMapColor(), SoundType.GLASS);
	public static final SignReg GREEN_STAINED_GLASS = registerSign("green_stained_glass", DyeColor.GREEN.getMapColor(), SoundType.GLASS);
	public static final SignReg RED_STAINED_GLASS = registerSign("red_stained_glass", DyeColor.RED.getMapColor(), SoundType.GLASS);
	public static final SignReg BLACK_STAINED_GLASS = registerSign("black_stained_glass", DyeColor.BLACK.getMapColor(), SoundType.GLASS);

	public static final Supplier<AttachmentType<Boolean>> INVISIBLE = ATTACHMENT_TYPES.register(
			"invisible", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).build()
	);

	public static SignReg registerSign(String name, MapColor color, SoundType soundType) {
		SignReg reg = new SignReg(name, color, soundType);
		SIGNS.add(reg);
		return reg;
	}

	public static void addBlockEntityTypes(BlockEntityTypeAddBlocksEvent event) {
		for(SignReg reg : SIGNS) {
			event.modify(BlockEntityType.SIGN, reg.getSign().get(), reg.getWallSign().get());
			event.modify(BlockEntityType.HANGING_SIGN, reg.getHangingSign().get(), reg.getWallHangingSign().get());
		}
	}

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SIGN_TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
			.icon(GLASS.getSignItem()::toStack)
			.title(Component.translatable("itemGroup.holosigns"))
			.displayItems((displayParameters, output) -> {
				List<ItemStack> stacks = SignRegistry.ITEMS.getEntries().stream().map(reg -> new ItemStack(reg.get())).toList();
				output.acceptAll(stacks);
			}).build());
}
