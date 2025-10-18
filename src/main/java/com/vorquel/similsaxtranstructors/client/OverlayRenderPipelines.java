package com.vorquel.similsaxtranstructors.client;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.vorquel.similsaxtranstructors.SimilsaxTranstructors;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;

public class OverlayRenderPipelines {

  public static final RenderPipeline OVERLAY = RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET)
      .withLocation(ResourceLocation.fromNamespaceAndPath(SimilsaxTranstructors.MODID, "pipeline/overlay"))
      .withVertexShader("core/position_tex_color")
      .withFragmentShader("core/position_tex_color")
      .withSampler("Sampler0")
      .withCull(false)
      .withBlend(BlendFunction.TRANSLUCENT)
      .withVertexFormat(DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS)
      .build();

  public static void registerRenderPipeline(RegisterRenderPipelinesEvent event) {
    event.registerPipeline(OVERLAY);
  }
}
