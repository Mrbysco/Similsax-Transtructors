package com.vorquel.similsaxtranstructors.client;

import net.minecraft.client.renderer.rendertype.LayeringTransform;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
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

  public static RenderType overlayRenderer(Identifier texture) {
    return OVERLAY_RENDERER.apply(texture);
  }
}
