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
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.util.Unit;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSignRenderer.class)
public abstract class AbstractSignRendererMixin<S extends SignRenderState>  {

	@Shadow
	protected abstract void submitSign(PoseStack poseStack, int lightCoords, WoodType type, Model.Simple signModel, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress, SubmitNodeCollector submitNodeCollector);

	@Shadow
	protected abstract Model.Simple getSignModel(S s);

	@Shadow
	protected abstract void submitSignText(S state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, SignText signText);

	@Shadow
	protected abstract SpriteId getSignSprite(WoodType woodType);

	@Shadow
	@Final
	private SpriteGetter sprites;

	@Inject(method = "extractRenderState(Lnet/minecraft/world/level/block/entity/SignBlockEntity;Lnet/minecraft/client/renderer/blockentity/state/SignRenderState;FLnet/minecraft/world/phys/Vec3;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V",
			at = @At(value = "HEAD"))
	public void holosigns$extractRenderState(SignBlockEntity signBlockEntity, SignRenderState renderState,
	                                         float p_445525_, Vec3 p_445758_, ModelFeatureRenderer.CrumblingOverlay p_446708_, CallbackInfo ci) {
		if (renderState instanceof HoloSignStateData holoState) {
			holoState.holosign$setInvisible(signBlockEntity.hasData(SignRegistry.INVISIBLE));
		}
	}

	@Inject(method = "submitSignWithText(Lnet/minecraft/client/renderer/blockentity/state/SignRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;Lnet/minecraft/client/renderer/SubmitNodeCollector;)V",
			at = @At(value = "HEAD"), cancellable = true)
	private void submitSignWithText(
			S state, PoseStack poseStack, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress, SubmitNodeCollector submitNodeCollector, CallbackInfo ci) {
		if (state instanceof HoloSignStateData holoState && holoState.holosign$isInvisible()) {
			poseStack.pushPose();
			poseStack.mulPose(state.transformations.body());
			poseStack.popPose();
			if (state.frontText != null) {
				poseStack.pushPose();
				poseStack.mulPose(state.transformations.frontText());
				this.submitSignText(state, poseStack, submitNodeCollector, state.frontText);
				poseStack.popPose();
			}

			if (state.backText != null) {
				poseStack.pushPose();
				poseStack.mulPose(state.transformations.backText());
				this.submitSignText(state, poseStack, submitNodeCollector, state.backText);
				poseStack.popPose();
			}
			ci.cancel();
		}
	}

	@Inject(method = "submitSign(Lcom/mojang/blaze3d/vertex/PoseStack;ILnet/minecraft/world/level/block/state/properties/WoodType;Lnet/minecraft/client/model/Model$Simple;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;Lnet/minecraft/client/renderer/SubmitNodeCollector;)V",
			at = @At("HEAD"),
			cancellable = true)
	public void holosigns$renderSign(PoseStack poseStack, int lightCoords, WoodType type, Model.Simple signModel,
	                                 ModelFeatureRenderer.CrumblingOverlay crumblingOverlay,
	                                 SubmitNodeCollector submitNodeCollector, CallbackInfo ci) {
		if (type.name().startsWith(HoloSignsMod.MOD_ID) && type.name().endsWith("_stained_glass")) {
			SpriteId sprite = this.getSignSprite(type);
			RenderType rendertype = sprite.renderType(RenderTypes::entityTranslucent);
			submitNodeCollector.submitModel(signModel, Unit.INSTANCE, poseStack, rendertype,
					lightCoords, OverlayTexture.NO_OVERLAY, -1, sprites.get(sprite), 0, crumblingOverlay);
			ci.cancel();
		}
	}
}
