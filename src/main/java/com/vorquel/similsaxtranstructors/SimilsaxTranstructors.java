package com.vorquel.similsaxtranstructors;

import com.lothrazar.library.render.RenderBlockOverlay;
import com.vorquel.similsaxtranstructors.item.ItemSimilsax;
import com.vorquel.similsaxtranstructors.registry.ConfigHandlerST;
import com.vorquel.similsaxtranstructors.registry.SimilsaxRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SimilsaxTranstructors.MODID)
public class SimilsaxTranstructors {

  public static final String MODID = "similsaxtranstructors";

  public SimilsaxTranstructors() {
    IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    new ConfigHandlerST();
    SimilsaxRegistry.ITEMS.register(eventBus);
    eventBus.addListener(this::addTabContents);
    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
      MinecraftForge.EVENT_BUS.register(new RenderBlockOverlay(MODID + ":overlay_renderer", new ResourceLocation(MODID, "textures/overlay.png"), ItemSimilsax.class));
    });
  }

  private void addTabContents(final BuildCreativeModeTabContentsEvent event) {
    if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
      event.accept(SimilsaxRegistry.BASIC.get());
      event.accept(SimilsaxRegistry.ADVANCED.get());
    }
  }
}
