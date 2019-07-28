package com.vorquel.similsaxtranstructors;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class RegistryThing {

  public static ItemGroup itemGroup = new ItemGroup(SimilsaxTranstructors.MODID) {

    @Override
    public ItemStack createIcon() {
      return new ItemStack(Blocks.DIRT);
    }
  };
//  @SubscribeEvent
//  public static void onRegistryEvent(RegistryEvent.Register<Item> event) {
//    event.getRegistry().register(SimilsaxTranstructors.itemBasic.setRegistryName(new ResourceLocation(SimilsaxTranstructors.MOD_ID, "similsaxtranstructorbasic")));
//    event.getRegistry().register(SimilsaxTranstructors.itemAdvanced.setRegistryName(new ResourceLocation(SimilsaxTranstructors.MOD_ID, "similsaxtranstructoradvanced")));
//  }
//  @SubscribeEvent
//  public static void registerModels(ModelRegistryEvent event) {
//    ModelLoader.setCustomModelResourceLocation(itemBasic, 0, new ModelResourceLocation(MOD_ID + ":similsaxtranstructorbasic", "inventory"));
//    ModelLoader.setCustomModelResourceLocation(itemAdvanced, 0, new ModelResourceLocation(MOD_ID + ":similsaxtranstructoradvanced", "inventory"));
//  }
}
