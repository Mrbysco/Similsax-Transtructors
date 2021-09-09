package com.vorquel.similsaxtranstructors;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemSimilsax extends Item {

  private static final int[] sidesXY = new int[] { 4, 5, 0, 1 };
  private static final int[] sidesYZ = new int[] { 0, 1, 2, 3 };
  private static final int[] sidesZX = new int[] { 2, 3, 4, 5 };
  public final static float LO = 0.25F;
  public final static float HI = 1 - LO;

  public ItemSimilsax(Properties properties) {
    super(properties.group(ItemGroup.TOOLS));
  }

  @Override
  public ActionResultType onItemUse(ItemUseContext context) {
    PlayerEntity player = context.getPlayer();
    BlockState block = context.getWorld().getBlockState(context.getPos());
    ItemStack blockStack = new ItemStack(block.getBlock());
    if (!player.isCreative() && !player.inventory.hasItemStack(blockStack)) {
      return ActionResultType.PASS;
    }
    Direction side = getSide(context.getFace(), context.getHitVec(), context.getPos());
    int initialRange = isAdvanced() ? ConfigHandler.ADVANCEDRANGE.get() : ConfigHandler.BASICRANGE.get();
    return this.recursiveTower(context.getItem(), player, block.getBlock(), block, context.getWorld(), context.getPos(), side, blockStack, initialRange);
  }

  private boolean isAdvanced() {
    return this == SimilsaxRegistry.advanced;
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    tooltip.add(new TranslationTextComponent(getTranslationKey() + ".tooltip").mergeStyle(TextFormatting.GRAY));
  }

  private ActionResultType recursiveTower(ItemStack stack, PlayerEntity player, Block block, BlockState state, World world, BlockPos pos, Direction side, final ItemStack blockStack, int range) {
    if (range == 0 || pos == null || side == null || blockStack.isEmpty()) {
      return ActionResultType.PASS;
    }
    pos = pos.offset(side);
    final BlockState otherState = world.getBlockState(pos);
    final Block otherBlock = otherState.getBlock();
    final boolean canSkip = this.isAdvanced();
    if (canBuildHere(world, pos)) {
      stack.damageItem(1, player, (p) -> {
        stack.setCount(0);
      });
      if (!player.isCreative()) {
        for (int i = 0; i < player.inventory.mainInventory.size(); ++i) {
          ItemStack localStack = player.inventory.getStackInSlot(i);
          if (!localStack.isEmpty() && localStack.isItemEqual(blockStack)) {
            player.inventory.decrStackSize(i, 1);
            if (player.openContainer != null) {
              player.openContainer.detectAndSendChanges();
            }
            break;
          }
        }
      }
      world.setBlockState(pos, state);
      world.playSound(player, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, block.getSoundType(state, world, pos, player).getPlaceSound(), SoundCategory.BLOCKS, .5F, .5F);
      return ActionResultType.SUCCESS;
    }
    else if (canSkip || doBlocksMatch(block, otherBlock)) {
      //if u cant build here, does it match to keep going
      return recursiveTower(stack, player, block, state, world, pos, side, blockStack, range - 1);
    }
    else {
      return ActionResultType.PASS;
    }
  }

  private boolean canBuildHere(World world, BlockPos pos) {
    return world.isAirBlock(pos);
  }

  private boolean doBlocksMatch(Block block, Block otherBlock) {
    return block == otherBlock;
  }

  public static Direction getSide(Direction sideIn, Vector3d vec, BlockPos pos) {
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
