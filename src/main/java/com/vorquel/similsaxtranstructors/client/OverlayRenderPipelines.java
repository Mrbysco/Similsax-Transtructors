package com.vorquel.similsaxtranstructors.client;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.vorquel.similsaxtranstructors.SimilsaxTranstructors;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;

public class OverlayRenderPipelines {

  public static final RenderPipeline OVERLAY = RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET)
      .withLocation(Identifier.fromNamespaceAndPath(SimilsaxTranstructors.MODID, "pipeline/overlay"))
      .withVertexShader("core/position_tex_color")
      .withFragmentShader("core/position_tex_color")
      .withSampler("Sampler0")
      .withCull(false)
      .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
      .withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, false))
      .withVertexFormat(DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS)
      .build();

  public static void registerRenderPipeline(RegisterRenderPipelinesEvent event) {
    event.registerPipeline(OVERLAY);
  }
}
