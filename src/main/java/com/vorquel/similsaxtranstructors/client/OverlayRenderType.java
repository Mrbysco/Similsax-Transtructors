package com.vorquel.similsaxtranstructors.client;

import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.TriState;

import java.util.function.Function;

public abstract class OverlayRenderType extends RenderType {

  public OverlayRenderType(String name, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
    super(name, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
  }

  public static final Function<ResourceLocation, RenderType> OVERLAY_RENDERER = Util.memoize(
      texture -> {
        RenderType.CompositeState state = RenderType.CompositeState.builder()
            .setTextureState(new RenderStateShard.TextureStateShard(texture, TriState.FALSE, false))
            .setLightmapState(RenderStateShard.LIGHTMAP)
            .setOutputState(RenderStateShard.TRANSLUCENT_TARGET)
            .createCompositeState(true);
        return create("similsaxtranstructors:overlay_renderer", 256, true, false, OverlayRenderPipelines.OVERLAY, state);
      }
  );

  public static RenderType overlayRenderer(ResourceLocation texture) {
    return OVERLAY_RENDERER.apply(texture);
  }
}
