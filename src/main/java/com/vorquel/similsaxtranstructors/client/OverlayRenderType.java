package com.vorquel.similsaxtranstructors.client;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class OverlayRenderType extends RenderType {

  public OverlayRenderType(String nameIn, VertexFormat formatIn, VertexFormat.Mode drawModeIn, int bufferSizeIn, boolean useDelegateIn, boolean needsSortingIn, Runnable setupTaskIn, Runnable clearTaskIn) {
    super(nameIn, formatIn, drawModeIn, bufferSizeIn, useDelegateIn, needsSortingIn, setupTaskIn, clearTaskIn);
  }

  public static RenderType overlayRenderer(ResourceLocation resourceLocation) {
    RenderType.CompositeState state = RenderType.CompositeState.builder()
        .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false))
        .setCullState(RenderStateShard.NO_CULL)
        .setShaderState(BLOCK_SHADER)
        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
        .setOutputState(RenderStateShard.TRANSLUCENT_TARGET)
        .createCompositeState(true);
    return create("similsaxtranstructors:overlay_renderer", DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, true, false, state);
  }
}
