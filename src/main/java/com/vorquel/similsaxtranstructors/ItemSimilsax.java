package com.vorquel.similsaxtranstructors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemSimilsax extends Item {

  public ItemSimilsax(Properties properties) {
    super(properties);
  }

  public int range = 0;

  @Override
  public ActionResultType onItemUse(ItemUseContext context) {
    PlayerEntity player = context.getPlayer();
    BlockState block = context.getWorld().getBlockState(context.getPos());
    ItemStack blockStack = new ItemStack(block.getBlock());
    if (player.inventory.hasItemStack(blockStack) == false) {
      return ActionResultType.PASS;
    }
    range = 32;
    int side=getSide(context.getFace().ordinal(), context.getHitVec());

    return this.tower(context.getItem(), context.getPlayer(), block.getBlock(), block, context.getWorld(), context.getPos(),
        Direction.values()[side], blockStack);
  }

  private ActionResultType tower(ItemStack stack, PlayerEntity player, Block block, BlockState state, World world, BlockPos pos, Direction side, ItemStack blockStack) {
    return tower(stack, player, block, state, world, pos, side, blockStack, range);
  }

  private ActionResultType tower(ItemStack stack, PlayerEntity player, Block block, BlockState state, World world, BlockPos pos, Direction side, ItemStack blockStack, int range) {
    if (range == 0) return ActionResultType.PASS;
    pos = pos.offset(side);
    BlockState otherState = world.getBlockState(pos);
    Block otherBlock = otherState.getBlock();
    if (block == otherBlock && state.getProperties().equals(otherState.getProperties())) {
      return tower(stack, player, block, state, world, pos.offset(side), side, blockStack, range - 1);
    }
    else if (world.isAirBlock(pos)) {
      //      if (!world.mayPlace(block, pos, false, side.getOpposite(), null)) return ActionResultType.PASS;
      //      stack.damageItem(1, player);
      if (stack.getCount() == 0)
        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT,
            SoundCategory.PLAYERS, 1f, 1f);
      if (!player.isCreative()) {
        for (int i = 0; i < player.inventory.mainInventory.size(); ++i) {
          ItemStack localStack = player.inventory.getStackInSlot(i);
          if (localStack.isEmpty()) continue;
          if (localStack.isItemEqual(blockStack)) {
            player.inventory.decrStackSize(i, 1);
            player.openContainer.detectAndSendChanges();
            break;
          }
        }
      }
      world.setBlockState(pos, state);
      world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
          block.getSoundType(
              state, world, pos, player).getPlaceSound(),
          SoundCategory.BLOCKS, .5F, .5F);
      return ActionResultType.SUCCESS;
    }
    else
      return ActionResultType.PASS;
  }

  private static final int[] sidesXY = new int[] { 4, 5, 0, 1 };
  private static final int[] sidesYZ = new int[] { 0, 1, 2, 3 };
  private static final int[] sidesZX = new int[] { 2, 3, 4, 5 };

  public static int getSide(int side, Vec3d vec) {
    double xIn = vec.x, yIn = vec.y, zIn = vec.z;
    //if the middle was clicked, place on the opposite side
    float lo = .25f, hi = .75f;
    int centeredSides = 0;
    if (side != 0 && side != 1)
      centeredSides += yIn > lo && yIn < hi ? 1 : 0;
    if (side != 2 && side != 3)
      centeredSides += zIn > lo && zIn < hi ? 1 : 0;
    if (side != 4 && side != 5)
      centeredSides += xIn > lo && xIn < hi ? 1 : 0;
    if (centeredSides == 2)
      return side;
    //otherwise, place on the nearest side
    double left, right;
    int[] sides;
    switch (side) {
      case 0:
      case 1:
        left = zIn;
        right = xIn;
        sides = sidesZX;
        break;
      case 2:
      case 3:
        left = xIn;
        right = yIn;
        sides = sidesXY;
        break;
      case 4:
      case 5:
        left = yIn;
        right = zIn;
        sides = sidesYZ;
        break;
      default:
        return -1;
    }
    boolean b0 = left > right;
    boolean b1 = left > 1 - right;
    if (b0 && b1)
      return sides[0];
    else if (!b0 && !b1)
      return sides[1];
    else if (b1)
      return sides[2];
    else
      return sides[3];
  }
}


