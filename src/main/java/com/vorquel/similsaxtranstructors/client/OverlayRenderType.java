package com.vorquel.similsaxtranstructors.client;

import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public abstract class OverlayRenderType extends RenderType {

  public OverlayRenderType(String name, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
    super(name, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
  }

  public static final RenderStateShard.OutputStateShard TRANSLUCENT_TARGET = new RenderStateShard.OutputStateShard("translucent_target", () -> {
    RenderTarget rendertarget = Minecraft.getInstance().levelRenderer.getTranslucentTarget();
    return rendertarget != null ? rendertarget : Minecraft.getInstance().getMainRenderTarget();
  });

  public static final Function<ResourceLocation, RenderType> OVERLAY_RENDERER = Util.memoize(
      texture -> {
        RenderType.CompositeState state = RenderType.CompositeState.builder()
            .setTextureState(new RenderStateShard.TextureStateShard(texture, false))
            .setLightmapState(RenderStateShard.LIGHTMAP)
            .setOutputState(TRANSLUCENT_TARGET)
            .createCompositeState(true);
        return create("similsaxtranstructors:overlay_renderer", 256, true, false, OverlayRenderPipelines.OVERLAY, state);
      }
  );

  public static RenderType overlayRenderer(ResourceLocation texture) {
    return OVERLAY_RENDERER.apply(texture);
  }
}
