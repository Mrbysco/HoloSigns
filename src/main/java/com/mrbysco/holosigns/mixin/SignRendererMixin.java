package com.mrbysco.holosigns.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mrbysco.holosigns.HoloSignsMod;
import com.mrbysco.holosigns.registry.SignRegistry;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SignRenderer.class)
public abstract class SignRendererMixin {
	@Shadow abstract void renderSignText(BlockPos pos, SignText text, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int lineHeight, int maxWidth, boolean isFrontText);

	@Shadow abstract void renderSignModel(PoseStack poseStack, int packedLight, int packedOverlay, Model model, VertexConsumer vertexConsumer);

	@Shadow public abstract float getSignModelRenderScale();

	@Shadow abstract Material getSignMaterial(WoodType woodType);

	@Inject(method = "renderSignWithText(Lnet/minecraft/world/level/block/entity/SignBlockEntity;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/SignBlock;Lnet/minecraft/world/level/block/state/properties/WoodType;Lnet/minecraft/client/model/Model;)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/blockentity/SignRenderer;translateSign(Lcom/mojang/blaze3d/vertex/PoseStack;FLnet/minecraft/world/level/block/state/BlockState;)V",
					shift = Shift.AFTER,
					ordinal = 0),
			cancellable = true)
	public void holosigns$renderSignWithText(SignBlockEntity signEntity, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, BlockState state, SignBlock signBlock, WoodType woodType, Model model, CallbackInfo ci) {
		if(signEntity.hasData(SignRegistry.INVISIBLE)) {
			this.renderSignText(
					signEntity.getBlockPos(),
					signEntity.getFrontText(),
					poseStack,
					buffer,
					packedLight,
					signEntity.getTextLineHeight(),
					signEntity.getMaxTextLineWidth(),
					true
			);
			this.renderSignText(
					signEntity.getBlockPos(),
					signEntity.getBackText(),
					poseStack,
					buffer,
					packedLight,
					signEntity.getTextLineHeight(),
					signEntity.getMaxTextLineWidth(),
					false
			);
			poseStack.popPose();
			ci.cancel();
		}
	}

	@Inject(method = "renderSign(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/world/level/block/state/properties/WoodType;Lnet/minecraft/client/model/Model;)V",
			at = @At("HEAD"),
			cancellable = true)
	public void holosigns$renderSign(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, WoodType woodType, Model model, CallbackInfo ci) {
		if (woodType.name().startsWith(HoloSignsMod.MOD_ID) && woodType.name().endsWith("_stained_glass")) {
			poseStack.pushPose();
			float f = this.getSignModelRenderScale();
			poseStack.scale(f, -f, -f);
			Material material = this.getSignMaterial(woodType);
			VertexConsumer vertexconsumer = material.buffer(buffer, RenderType::entityTranslucent);
			this.renderSignModel(poseStack, packedLight, packedOverlay, model, vertexconsumer);
			poseStack.popPose();
			ci.cancel();
		}
	}
}
