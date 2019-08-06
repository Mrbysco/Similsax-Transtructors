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

  private final static int range = 32;

  @Override
  public ActionResultType onItemUse(ItemUseContext context) {
    PlayerEntity player = context.getPlayer();
    BlockState block = context.getWorld().getBlockState(context.getPos());
    ItemStack blockStack = new ItemStack(block.getBlock());
    if (player.inventory.hasItemStack(blockStack) == false) {
      return ActionResultType.PASS;
    }
    Direction side = getSide(context.getFace(), context.getHitVec(), context.getPos());
    return this.tower(context.getItem(), context.getPlayer(), block.getBlock(), block, context.getWorld(), context.getPos(),
        side, blockStack);
  }

  private ActionResultType tower(ItemStack stack, PlayerEntity player, Block block, BlockState state, World world, BlockPos pos, Direction side, ItemStack blockStack) {
    return tower(stack, player, block, state, world, pos, side, blockStack, range);
  }

  private ActionResultType tower(ItemStack stack, PlayerEntity player, Block block, BlockState state, World world, BlockPos pos, Direction side, ItemStack blockStack, int range) {
    if (range == 0) {
      return ActionResultType.PASS;
    }
    pos = pos.offset(side);
    BlockState otherState = world.getBlockState(pos);
    Block otherBlock = otherState.getBlock();
    if (block == otherBlock // && state.getProperties().equals(otherState.getProperties())
    ) {
      SimilsaxTranstructors.log.info("go to range minus one {}", range);
      return tower(stack, player, block, state, world, pos, side, blockStack, range - 1);
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
      SimilsaxTranstructors.log.info("gbuild at  {}", pos);
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
  final static float lo = .25f;
  final static float hi = .75f;

  public static Direction getSide(Direction sideIn, Vec3d vec, BlockPos pos) {
    int side = sideIn.ordinal();
    double xIn = vec.x - pos.getX(),
        yIn = vec.y - pos.getY(),
        zIn = vec.z - pos.getZ();
    //    SimilsaxTranstructors.log.info("{} :: {}, {}, {}", side, xIn, yIn, zIn);
    //if the middle was clicked, place on the opposite side
    int centeredSides = 0;
    if (side != 0 && side != 1)
      centeredSides += yIn > lo && yIn < hi ? 1 : 0;
    if (side != 2 && side != 3)
      centeredSides += zIn > lo && zIn < hi ? 1 : 0;
    if (side != 4 && side != 5)
      centeredSides += xIn > lo && xIn < hi ? 1 : 0;
    if (centeredSides == 2)
      return Direction.values()[side].getOpposite();
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
    boolean b0 = left > right;
    boolean b1 = left > 1 - right;
    int result = 0;
    if (b0 && b1)
      result = sides[0];
    else if (!b0 && !b1)
      result = sides[1];
    else if (b1)
      result = sides[2];
    else
      result = sides[3];
    return Direction.values()[result].getOpposite();
  }
}
