package com.mrbysco.holosigns.mixin;

import net.minecraft.client.renderer.blockentity.SignRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SignRenderer.class)
public abstract class SignRendererMixin {
 //TODO: Fix translucent rendering of glass based signs
}
