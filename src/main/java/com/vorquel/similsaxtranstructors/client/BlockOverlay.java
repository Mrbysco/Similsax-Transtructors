package com.vorquel.similsaxtranstructors.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.vorquel.similsaxtranstructors.ItemSimilsax;
import com.vorquel.similsaxtranstructors.SimilsaxTranstructors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.DrawHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlockOverlay {

  private final ResourceLocation overlayLocation = new ResourceLocation(SimilsaxTranstructors.MODID, "textures/overlay.png");
  private final Vector3d[] vs = new Vector3d[8];
  {
    for (int i = 0; i < 8; ++i) {
      int x = (i & 1) == 1 ? 1 : 0;
      int y = (i & 2) == 2 ? 1 : 0;
      int z = (i & 4) == 4 ? 1 : 0;
      vs[i] = new Vector3d(x, y, z);
    }
  }
  int arrow1 = 0;
  int arrow2 = 1;
  int arrow3 = 2;
  int arrow4 = 3;
  int cross = 4;
  int bullseye = 5;
  int cancel = 6;
  private final float[][][] uvs = new float[7][4][];
  {
    //ararrow1ow 1
    uvs[arrow1][0] = new float[] { 0, 0 };
    uvs[arrow1][1] = new float[] { 0, .5f };
    uvs[arrow1][2] = new float[] { .5f, .5f };
    uvs[arrow1][3] = new float[] { .5f, 0 };
    //arrow 2
    uvs[arrow2][0] = new float[] { 0, .5f };
    uvs[arrow2][1] = new float[] { .5f, .5f };
    uvs[arrow2][2] = new float[] { .5f, 0 };
    uvs[arrow2][3] = new float[] { 0, 0 };
    //arrow 3
    uvs[arrow3][0] = new float[] { .5f, .5f };
    uvs[arrow3][1] = new float[] { .5f, 0 };
    uvs[arrow3][2] = new float[] { 0, 0 };
    uvs[arrow3][3] = new float[] { 0, .5f };
    //arrow 4
    uvs[arrow4][0] = new float[] { .5f, 0 };
    uvs[arrow4][1] = new float[] { 0, 0 };
    uvs[arrow4][2] = new float[] { 0, .5f };
    uvs[arrow4][3] = new float[] { .5f, .5f };
    //cross
    uvs[cross][0] = new float[] { .5f, 0 };
    uvs[cross][1] = new float[] { .5f, .5f };
    uvs[cross][2] = new float[] { 1, .5f };
    uvs[cross][3] = new float[] { 1, 0 };
    //bullseye
    uvs[bullseye][0] = new float[] { 0, .5f };
    uvs[bullseye][1] = new float[] { 0, 1 };
    uvs[bullseye][2] = new float[] { .5f, 1 };
    uvs[bullseye][3] = new float[] { .5f, .5f };
    //cancel
    uvs[cancel][0] = new float[] { .5f, .5f };
    uvs[cancel][1] = new float[] { .5f, 1 };
    uvs[cancel][2] = new float[] { 1, 1 };
    uvs[cancel][3] = new float[] { 1, .5f };
  }

  @SubscribeEvent
  public void renderOverlay(DrawHighlightEvent.HighlightBlock event) {
    if (shouldSkip(event)) {
      return;
    }
    BlockRayTraceResult result = event.getTarget();
    MatrixStack matrixStack = event.getMatrix();
    IRenderTypeBuffer.Impl renderTypeBuffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
    Vector3d projectedView = Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView();
    BlockPos blockPos = new BlockPos(result.getPos());
    Vector3d hitVec = result.getHitVec();
    Direction indexd;
    int index = 6;
    int[] look = new int[6];
    if (isBadBlock(event)) {
      indexd = Direction.UP;
      index = 6;
      look = new int[] { cancel, cancel, cancel, cancel, cancel, cancel };
    }
    else {
      indexd = ItemSimilsax.getSide(result.getFace(), hitVec, blockPos);
      if (indexd == null) {
        return;
      }
      indexd = indexd.getOpposite();
      index = indexd.ordinal();
      switch (indexd) {
        case DOWN:
          look = new int[] { arrow3, bullseye, arrow2, arrow2, cross, arrow3 };
        break;
        case UP:
          look = new int[] { arrow1, cross, arrow4, arrow4, bullseye, arrow1 };
        break;
        case NORTH:
          look = new int[] { arrow2, arrow3, bullseye, arrow3, arrow2, cross };
        break;
        case SOUTH:
          look = new int[] { arrow4, arrow1, cross, arrow1, arrow4, bullseye };
        break;
        case WEST:
          look = new int[] { bullseye, arrow2, arrow3, cross, arrow3, arrow2 };
        break;
        case EAST://5
          look = new int[] { cross, arrow4, arrow1, bullseye, arrow1, arrow4 };
        break;
        default:
        break;
      }
    }
    matrixStack.push();
    RenderType renderType = OverlayRenderType.overlayRenderer(overlayLocation);
    IVertexBuilder builder = renderTypeBuffer.getBuffer(renderType);
    matrixStack.translate(-projectedView.x, -projectedView.y, -projectedView.z);
    //      SimilsaxTranstructors.log.info("{} ::  mPos {} ({}  {}  {}) ", mPos, indexd, v.x, v.y, v.z);
    double xFix = 0;
    double yFix = 0.08;
    double zFix = 0;
    double yDiff = hitVec.y - blockPos.getY();
    //      if (yDiff > ItemSimilsax.hi && yDiff < ItemSimilsax.lo) {
    //        //edge corner case
    //        return;
    //      }
    //      if (result.getFace() != Direction.DOWN && result.getFace() != Direction.UP) {
    if (yDiff < ItemSimilsax.lo) {
      //low side
      yFix = -1.62;
    }
    else if (yDiff > ItemSimilsax.hi) {
      yFix = -1.62;
    }
    //      }
    if (result.getFace() == Direction.SOUTH) {
      zFix = -1;
    }
    else if (result.getFace() == Direction.EAST) {
      xFix = -1;
    }
    else if (result.getFace() == Direction.UP) {
      yFix += -1;
      if (indexd != Direction.UP) {
        yFix += 1.7;
      }
    }
    else if (result.getFace() == Direction.DOWN) {
      if (indexd != Direction.DOWN) {
        yFix += 1.7;
      }
    }
    matrixStack.translate(blockPos.getX() + xFix, blockPos.getY() + yFix, blockPos.getZ() + zFix);
    //P/N ONLY exist to prevent layer fighting/flashing, push it just outside ontop of the block, so 1 + this fract
    final float P = 1 / 256f, N = -1 / 256f;
    final int X = 1, Y = 2, Z = 4;
    if (index >= 2 && index <= 5) {
      matrixStack.translate(0, -1.7F, 0);//2345
    }
    int TOP = 1, EAST = 0, SOUTH = 2, WEST = 3, BOTTOM = 4, NORTH = 5;
    //draw east
    matrixStack.translate(P, 0, 0);
    drawSide(builder, matrixStack.getLast().getMatrix(), X, Y, Z, uvs[look[EAST]]);// this one has to be est or west side
    //draw top
    matrixStack.translate(N, P, 0);
    drawSide(builder, matrixStack.getLast().getMatrix(), Y, Z, X, uvs[look[TOP]]); // TOP
    //SOUTH
    matrixStack.translate(0, N, P);
    drawSide(builder, matrixStack.getLast().getMatrix(), Z, X, Y, uvs[look[SOUTH]]);
    //WEST
    matrixStack.translate(N, 0, N);
    drawSide(builder, matrixStack.getLast().getMatrix(), 0, Z, Y, uvs[look[WEST]]);
    //BOTTOM
    matrixStack.translate(P, N, 0);
    drawSide(builder, matrixStack.getLast().getMatrix(), 0, X, Z, uvs[look[BOTTOM]]);
    //NORTH
    matrixStack.translate(0, P, N);
    drawSide(builder, matrixStack.getLast().getMatrix(), 0, Y, X, uvs[look[NORTH]]);
    renderTypeBuffer.finish(renderType);
    matrixStack.pop();
  }

  private boolean shouldSkip(DrawHighlightEvent.HighlightBlock event) {
    if (event.getTarget().getType() != RayTraceResult.Type.BLOCK) {
      return true;
    }
    PlayerEntity player = Minecraft.getInstance().player;
    if (player != null) {
      ItemStack mainItemStack = player.getHeldItem(Hand.MAIN_HAND);
      Item mainItem = (mainItemStack.isEmpty()) ? null : mainItemStack.getItem();
      ItemStack offItemStack = player.getHeldItem(Hand.OFF_HAND);
      Item offItem = (offItemStack.isEmpty()) ? null : offItemStack.getItem();
      return !(mainItem instanceof ItemSimilsax || offItem instanceof ItemSimilsax);
    }
    else {
      return true;
    }
  }

  private boolean isBadBlock(DrawHighlightEvent.HighlightBlock event) {
    return false;
    //    BlockPos pos = event.getTarget().getBlockPos();
    //    World world = event.getPlayer().world;
    //    IBlockState state = world.getBlockState(pos);
    //    Block block = state.getBlock();
    //    return block.hasTileEntity(state) || block.isReplaceable(world, pos);
  }

  private void drawSide(IVertexBuilder buffer, Matrix4f matrix, int c, int i, int j, float[][] uv) {
    addVertex(buffer, matrix, uv[0][0], uv[0][1], c);
    addVertex(buffer, matrix, uv[1][0], uv[1][1], c + i);
    addVertex(buffer, matrix, uv[2][0], uv[2][1], c + i + j);
    addVertex(buffer, matrix, uv[3][0], uv[3][1], c + j);
  }

  private void addVertex(IVertexBuilder buffer, Matrix4f matrix, double u, double v, int i) {
    buffer.pos(matrix, (float) vs[i].x, (float) vs[i].y, (float) vs[i].z)
        .color(1.0f, 1.0f, 1.0f, 0.375f)
        .tex((float) u, (float) v).endVertex();
  }
}
