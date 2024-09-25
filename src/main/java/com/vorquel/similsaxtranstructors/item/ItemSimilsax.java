package com.vorquel.similsaxtranstructors;

import com.vorquel.similsaxtranstructors.registry.ConfigHandlerST;
import com.vorquel.similsaxtranstructors.registry.SimilsaxRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ItemSimilsax extends Item {

  private static final int[] sidesXY = new int[] { 4, 5, 0, 1 };
  private static final int[] sidesYZ = new int[] { 0, 1, 2, 3 };
  private static final int[] sidesZX = new int[] { 2, 3, 4, 5 };
  public final static float LO = 0.25F;
  public final static float HI = 1 - LO;

  public ItemSimilsax(Properties properties) {
    super(properties);
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    Player player = context.getPlayer();
    BlockState block = context.getLevel().getBlockState(context.getClickedPos());
    ItemStack blockStack = new ItemStack(block.getBlock());
    if (!player.isCreative() && !player.getInventory().contains(blockStack)) {
      return InteractionResult.PASS;
    }
    Direction side = getSide(context.getClickedFace(), context.getClickLocation(), context.getClickedPos());
    int initialRange = isAdvanced() ? ConfigHandlerST.ADVANCEDRANGE.get() : ConfigHandlerST.BASICRANGE.get();
    return this.recursiveTower(context.getItemInHand(), player, block.getBlock(), block, context.getLevel(), context.getClickedPos(), side, blockStack, initialRange);
  }

  private boolean isAdvanced() {
    return this == SimilsaxRegistry.ADVANCED.get();
  }

  @Override
  public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
    tooltipComponents.add(Component.translatable(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
  }

  private InteractionResult recursiveTower(ItemStack stack, Player player, Block block, BlockState state, Level level, BlockPos pos, Direction side, final ItemStack blockStack, int range) {
    if (range == 0 || pos == null || side == null || blockStack.isEmpty()) {
      return InteractionResult.PASS;
    }
    pos = pos.relative(side);
    final BlockState otherState = level.getBlockState(pos);
    final Block otherBlock = otherState.getBlock();
    final boolean canSkip = this.isAdvanced();
    if (canBuildHere(level, pos) && !level.isClientSide) {
      stack.hurtAndBreak(1, (ServerLevel) level, player, (p) -> {
        stack.setCount(0);
      });
      if (!player.isCreative()) {
        for (int i = 0; i < player.getInventory().items.size(); ++i) {
          ItemStack localStack = player.getInventory().getItem(i);
          if (!localStack.isEmpty() && ItemStack.isSameItem(localStack, blockStack)) {
            player.getInventory().removeItem(i, 1);
            if (player.containerMenu != null) {
              player.containerMenu.broadcastChanges();
            }
            break;
          }
        }
      }
      level.setBlockAndUpdate(pos, state);
      level.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, block.getSoundType(state, level, pos, player).getPlaceSound(), SoundSource.BLOCKS, .5F, .5F);
      return InteractionResult.SUCCESS;
    }
    else if (canSkip || doBlocksMatch(block, otherBlock)) {
      //if u cant build here, does it match to keep going
      return recursiveTower(stack, player, block, state, level, pos, side, blockStack, range - 1);
    }
    else {
      return InteractionResult.PASS;
    }
  }

  private boolean canBuildHere(Level level, BlockPos pos) {
    return level.isEmptyBlock(pos);
  }

  private boolean doBlocksMatch(Block block, Block otherBlock) {
    return block == otherBlock;
  }

  public static Direction getSide(Direction sideIn, Vec3 vec, BlockPos pos) {
    int side = sideIn.ordinal();
    double xIn = vec.x - pos.getX(),
        yIn = vec.y - pos.getY(),
        zIn = vec.z - pos.getZ();
    //if the middle was clicked, place on the opposite side
    int centeredSides = 0;
    if (side != 0 && side != 1) {
      centeredSides += yIn > LO && yIn < HI ? 1 : 0;
    }
    if (side != 2 && side != 3) {
      centeredSides += zIn > LO && zIn < HI ? 1 : 0;
    }
    if (side != 4 && side != 5) {
      centeredSides += xIn > LO && xIn < HI ? 1 : 0;
    }
    if (centeredSides == 2) {
      return Direction.values()[side].getOpposite();
    }
    //otherwise, place on the nearest side
    double left, right;
    int[] sides;
    switch (sideIn) {
      case DOWN:
      case UP:
        left = zIn;
        right = xIn;
        sides = sidesZX;
      break;
      case NORTH:
      case SOUTH:
        left = xIn;
        right = yIn;
        sides = sidesXY;
      break;
      case WEST:
      case EAST:
        left = yIn;
        right = zIn;
        sides = sidesYZ;
      break;
      default:
        return Direction.UP;
    }
    //    SimilsaxTranstructors.log.info("{} :: {}, are the left/rights ", left, right);
    double cutoff = LO;
    boolean leftCorner = left < cutoff || left > 1 - cutoff;
    boolean rightCorner = right < cutoff || right > 1 - cutoff;
    if (leftCorner && rightCorner) {
      return null;
    }
    boolean b0 = left > right;
    boolean b1 = left > 1 - right;
    int result = 0;
    if (b0 && b1) {
      result = sides[0];
    }
    else if (!b0 && !b1) {
      result = sides[1];
    }
    else if (b1) {
      result = sides[2];
    }
    else {
      result = sides[3];
    }
    return Direction.values()[result].getOpposite();
  }
}
