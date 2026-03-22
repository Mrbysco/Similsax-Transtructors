package com.vorquel.similsaxtranstructors.client;

import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.LayeringTransform;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

public class OverlayRenderType{

  private static final Function<Identifier, RenderType> OVERLAY_RENDERER = Util.memoize(
      texture -> {
        RenderSetup rendersetup = RenderSetup.builder(OverlayRenderPipelines.OVERLAY)
            .bufferSize(256)
            .withTexture("Sampler0", texture)
            .useLightmap()
            .useOverlay()
            .setOutputTarget(OutputTarget.MAIN_TARGET)
            .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
            .affectsCrumbling()
            .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
            .createRenderSetup();
        return RenderType.create("similsaxtranstructors:overlay_renderer", rendersetup);
      }
  );
//  public static final RenderStateShard.OutputStateShard TRANSLUCENT_TARGET = new RenderStateShard.OutputStateShard("translucent_target", () -> {
//    RenderTarget rendertarget = Minecraft.getInstance().levelRenderer.getTranslucentTarget();
//    return rendertarget != null ? rendertarget : Minecraft.getInstance().getMainRenderTarget();
//  });

//  public static final Function<Identifier, RenderType> OVERLAY_RENDERER = Util.memoize(
//      texture -> {
//        RenderType.CompositeState state = RenderType.CompositeState.builder()
//            .setTextureState(new RenderStateShard.TextureStateShard(texture, false))
//            .setLightmapState(RenderStateShard.LIGHTMAP)
//            .setOutputState(TRANSLUCENT_TARGET)
//            .createCompositeState(true);
//        return create("similsaxtranstructors:overlay_renderer", 256, true, false, OverlayRenderPipelines.OVERLAY, state);
//      }
//  );

  public static RenderType overlayRenderer(Identifier texture) {
    return OVERLAY_RENDERER.apply(texture);
  }
}
