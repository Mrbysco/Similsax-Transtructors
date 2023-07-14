package com.vorquel.similsaxtranstructors.item;

import com.lothrazar.library.item.ItemFlib;
import com.lothrazar.library.util.PlayerClickBlockfaceUtil;
import com.vorquel.similsaxtranstructors.registry.ConfigHandlerST;
import com.vorquel.similsaxtranstructors.registry.SimilsaxRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ItemSimilsax extends ItemFlib {

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
    Direction side = PlayerClickBlockfaceUtil.getClickLocationDirection(context.getClickedFace(), context.getClickLocation(), context.getClickedPos());
    int initialRange = isAdvanced() ? ConfigHandlerST.ADVANCEDRANGE.get() : ConfigHandlerST.BASICRANGE.get();
    return this.recursiveTower(context.getItemInHand(), player, block.getBlock(), block, context.getLevel(), context.getClickedPos(), side, blockStack, initialRange);
  }

  private boolean isAdvanced() {
    return this == SimilsaxRegistry.ADVANCED.get();
  }

  private InteractionResult recursiveTower(ItemStack stack, Player player, Block block, BlockState state, Level world, BlockPos pos, Direction side, final ItemStack blockStack, int range) {
    if (range == 0 || pos == null || side == null || blockStack.isEmpty()) {
      return InteractionResult.PASS;
    }
    pos = pos.relative(side);
    final BlockState otherState = world.getBlockState(pos);
    final Block otherBlock = otherState.getBlock();
    final boolean canSkip = this.isAdvanced();
    if (canBuildHere(world, pos)) {
      stack.hurtAndBreak(1, player, (p) -> {
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
      world.setBlockAndUpdate(pos, state);
      world.playSound(player, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, block.getSoundType(state, world, pos, player).getPlaceSound(), SoundSource.BLOCKS, .5F, .5F);
      return InteractionResult.SUCCESS;
    }
    else if (canSkip || doBlocksMatch(block, otherBlock)) {
      //if u cant build here, does it match to keep going
      return recursiveTower(stack, player, block, state, world, pos, side, blockStack, range - 1);
    }
    else {
      return InteractionResult.PASS;
    }
  }

  private boolean canBuildHere(Level world, BlockPos pos) {
    return world.isEmptyBlock(pos);
  }

  private boolean doBlocksMatch(Block block, Block otherBlock) {
    return block == otherBlock;
  }
}
