package com.mrbysco.holosigns.handler;

import com.mrbysco.holosigns.HoloSignsMod;
import com.mrbysco.holosigns.registry.SignRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;

public class SignHandler {
	@SubscribeEvent
	public void onSignInteract(RightClickBlock event) {
		Level level = event.getLevel();
		BlockPos pos = event.getPos();
		Player player = event.getEntity();
		ItemStack heldStack = event.getItemStack();
		if (heldStack.is(HoloSignsMod.MEDIUM) && level.getBlockEntity(pos) instanceof SignBlockEntity signBlockEntity) {
			if (!signBlockEntity.hasData(SignRegistry.INVISIBLE)) {
				signBlockEntity.setData(SignRegistry.INVISIBLE, Boolean.TRUE);

				heldStack.consume(1, player);
				event.setCanceled(true);
				event.setCancellationResult(InteractionResult.SUCCESS);
			}
		}
	}
}
