package com.vorquel.similsaxtranstructors.datagen;

import com.vorquel.similsaxtranstructors.datagen.client.SimilsaxItemModelProvider;
import com.vorquel.similsaxtranstructors.datagen.client.SimilsaxLanguageProvider;
import com.vorquel.similsaxtranstructors.datagen.server.SimilsaxRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class SimilsaxDatagen {
  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    PackOutput packOutput = generator.getPackOutput();
    CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
    ExistingFileHelper helper = event.getExistingFileHelper();

    if (event.includeServer()) {
      generator.addProvider(true, new SimilsaxRecipeProvider(packOutput, lookupProvider));
    }

    if (event.includeClient()) {
      generator.addProvider(true, new SimilsaxLanguageProvider(packOutput));
      generator.addProvider(true, new SimilsaxItemModelProvider(packOutput, helper));
    }
  }
}
