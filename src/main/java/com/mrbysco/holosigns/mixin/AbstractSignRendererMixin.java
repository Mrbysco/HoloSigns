package com.mrbysco.holosigns.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrbysco.holosigns.HoloSignsMod;
import com.mrbysco.holosigns.client.HoloSignStateData;
import com.mrbysco.holosigns.registry.SignRegistry;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.AbstractSignRenderer;
import net.minecraft.client.renderer.blockentity.state.SignRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.util.Unit;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSignRenderer.class)
public abstract class AbstractSignRendererMixin {
	@Shadow
	protected abstract float getSignModelRenderScale();

	@Shadow
	protected abstract Material getSignMaterial(WoodType woodType);

	@Shadow
	protected abstract void submitSignText(SignRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, boolean isFront);

	@Shadow
	protected abstract void translateSign(PoseStack poseStack, float yRot, BlockState state);

	@Shadow
	@Final
	private MaterialSet materials;

	@Inject(method = "extractRenderState(Lnet/minecraft/world/level/block/entity/SignBlockEntity;Lnet/minecraft/client/renderer/blockentity/state/SignRenderState;FLnet/minecraft/world/phys/Vec3;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V",
			at = @At(value = "HEAD"))
	public void holosigns$extractRenderState(SignBlockEntity signBlockEntity, SignRenderState renderState,
	                                         float p_445525_, Vec3 p_445758_, ModelFeatureRenderer.CrumblingOverlay p_446708_, CallbackInfo ci) {
		if (renderState instanceof HoloSignStateData holoState) {
			holoState.holosign$setInvisible(signBlockEntity.hasData(SignRegistry.INVISIBLE));
		}
	}

	@Inject(method = "submitSignWithText(Lnet/minecraft/client/renderer/blockentity/state/SignRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/SignBlock;Lnet/minecraft/world/level/block/state/properties/WoodType;Lnet/minecraft/client/model/Model$Simple;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;Lnet/minecraft/client/renderer/SubmitNodeCollector;)V",
			at = @At(value = "HEAD"), cancellable = true)
	private void submitSignWithText(
			SignRenderState renderState, PoseStack poseStack, BlockState blockState, SignBlock sign, WoodType woodType,
			Model.Simple model, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay,
			SubmitNodeCollector nodeCollector, CallbackInfo ci) {
		if (renderState instanceof HoloSignStateData holoState && holoState.holosign$isInvisible()) {
			poseStack.pushPose();
			this.translateSign(poseStack, -sign.getYRotationDegrees(blockState), blockState);
			this.submitSignText(renderState, poseStack, nodeCollector, true);
			this.submitSignText(renderState, poseStack, nodeCollector, false);
			ci.cancel();
			poseStack.popPose();
		}
	}

	@Inject(method = "submitSign(Lcom/mojang/blaze3d/vertex/PoseStack;ILnet/minecraft/world/level/block/state/properties/WoodType;Lnet/minecraft/client/model/Model$Simple;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;Lnet/minecraft/client/renderer/SubmitNodeCollector;)V",
			at = @At("HEAD"),
			cancellable = true)
	public void holosigns$renderSign(PoseStack poseStack, int packedLight, WoodType woodType, Model.Simple model,
	                                 ModelFeatureRenderer.CrumblingOverlay crumblingOverlay,
	                                 SubmitNodeCollector nodeCollector, CallbackInfo ci) {
		if (woodType.name().startsWith(HoloSignsMod.MOD_ID) && woodType.name().endsWith("_stained_glass")) {
			poseStack.pushPose();
			float f = this.getSignModelRenderScale();
			poseStack.scale(f, -f, -f);
			Material material = this.getSignMaterial(woodType);
			RenderType rendertype = material.renderType(RenderTypes::entityTranslucent);
			nodeCollector.submitModel(
					model, Unit.INSTANCE, poseStack, rendertype, packedLight, OverlayTexture.NO_OVERLAY, -1,
					this.materials.get(material), 0, crumblingOverlay
			);
			poseStack.popPose();
			ci.cancel();
		}
	}
}
