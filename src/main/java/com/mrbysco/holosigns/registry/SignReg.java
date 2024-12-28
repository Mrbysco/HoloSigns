package com.mrbysco.holosigns.registry;

import com.mrbysco.holosigns.HoloSignsMod;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class SignReg {
	protected final String NAME;
	protected final BlockSetType SET_TYPE;
	protected final WoodType TYPE;
	protected final DeferredBlock<StandingSignBlock> SIGN;
	protected final DeferredBlock<WallSignBlock> WALL_SIGN;
	protected final DeferredBlock<CeilingHangingSignBlock> HANGING_SIGN;
	protected final DeferredBlock<WallHangingSignBlock> WALL_HANGING_SIGN;
	protected final DeferredItem<BlockItem> SIGN_ITEM;
	protected final DeferredItem<BlockItem> HANGING_SIGN_ITEM;

	public String getName() {
		return NAME;
	}

	public WoodType getWoodType() {
		return TYPE;
	}

	public DeferredBlock<StandingSignBlock> getSign() {
		return SIGN;
	}

	public DeferredBlock<WallSignBlock> getWallSign() {
		return WALL_SIGN;
	}

	public DeferredBlock<CeilingHangingSignBlock> getHangingSign() {
		return HANGING_SIGN;
	}

	public DeferredBlock<WallHangingSignBlock> getWallHangingSign() {
		return WALL_HANGING_SIGN;
	}

	public DeferredItem<BlockItem> getSignItem() {
		return SIGN_ITEM;
	}

	public DeferredItem<BlockItem> getHangingSignItem() {
		return HANGING_SIGN_ITEM;
	}

	public SignReg(String name, MapColor color, SoundType soundType) {
		this.NAME = name;
		this.SET_TYPE = BlockSetType.register(new BlockSetType(HoloSignsMod.prefix("name").toString(),
				true,
				true,
				true,
				BlockSetType.PressurePlateSensitivity.EVERYTHING,
				soundType,
				SoundEvents.WOODEN_DOOR_CLOSE,
				SoundEvents.WOODEN_DOOR_OPEN,
				SoundEvents.WOODEN_TRAPDOOR_CLOSE,
				SoundEvents.WOODEN_TRAPDOOR_OPEN,
				SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF,
				SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON,
				SoundEvents.WOODEN_BUTTON_CLICK_OFF,
				SoundEvents.WOODEN_BUTTON_CLICK_ON));
		this.TYPE = WoodType.register(new WoodType(HoloSignsMod.prefix(name).toString(), this.SET_TYPE));
		this.SIGN = SignRegistry.BLOCKS.registerBlock(name + "_sign", (properties) -> new StandingSignBlock(this.TYPE, properties
				.mapColor(color)
				.forceSolidOn()
				.noCollission()
				.strength(1.0F)
				.ignitedByLava()));
		this.WALL_SIGN = SignRegistry.BLOCKS.registerBlock(name + "_wall_sign", (properties) -> new WallSignBlock(this.TYPE, properties
				.mapColor(color)
				.forceSolidOn()
				.noCollission()
				.strength(1.0F)
				.dropsLike(SIGN.get())
				.ignitedByLava()));
		this.HANGING_SIGN = SignRegistry.BLOCKS.registerBlock(name + "_hanging_sign", (properties) -> new CeilingHangingSignBlock(this.TYPE, properties
				.mapColor(color)
				.forceSolidOn()
				.instrument(NoteBlockInstrument.BASS)
				.noCollission()
				.strength(1.0F)
				.ignitedByLava()));
		this.WALL_HANGING_SIGN = SignRegistry.BLOCKS.registerBlock(name + "_wall_hanging_sign", (properties) -> new WallHangingSignBlock(this.TYPE, properties
				.mapColor(color)
				.forceSolidOn()
				.instrument(NoteBlockInstrument.BASS)
				.noCollission()
				.strength(1.0F)
				.ignitedByLava()
				.dropsLike(HANGING_SIGN.get())));
		this.SIGN_ITEM = SignRegistry.ITEMS.register(name + "_sign", () -> new SignItem(new Item.Properties().stacksTo(16), SIGN.get(), WALL_SIGN.get()));
		this.HANGING_SIGN_ITEM = SignRegistry.ITEMS.register(name + "_hanging_sign", () -> new HangingSignItem(HANGING_SIGN.get(), WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
	}
}
