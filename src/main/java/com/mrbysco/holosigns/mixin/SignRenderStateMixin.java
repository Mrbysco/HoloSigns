package com.mrbysco.holosigns.mixin;

import com.mrbysco.holosigns.client.HoloSignStateData;
import net.minecraft.client.renderer.blockentity.state.SignRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SignRenderState.class)
public class SignRenderStateMixin implements HoloSignStateData {
	@Unique
	private boolean holosign$invisible = false;

	@Override
	public boolean holosign$isInvisible() {
		return holosign$invisible;
	}

	@Override
	public void holosign$setInvisible(boolean invisible) {
		this.holosign$invisible = invisible;
	}
}
