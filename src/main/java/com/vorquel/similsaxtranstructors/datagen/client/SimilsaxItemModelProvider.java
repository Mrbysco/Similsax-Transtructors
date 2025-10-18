package com.vorquel.similsaxtranstructors.datagen.client;

import com.vorquel.similsaxtranstructors.SimilsaxTranstructors;
import com.vorquel.similsaxtranstructors.registry.SimilsaxRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class SimilsaxItemModelProvider extends ModelProvider {

  public SimilsaxItemModelProvider(PackOutput packOutput) {
    super(packOutput, SimilsaxTranstructors.MODID);
  }

  @Override
  protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
    itemModels.generateFlatItem(SimilsaxRegistry.BASIC.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
    itemModels.generateFlatItem(SimilsaxRegistry.ADVANCED.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
  }
}
