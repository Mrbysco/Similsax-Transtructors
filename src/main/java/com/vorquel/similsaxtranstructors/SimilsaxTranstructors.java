package com.vorquel.similsaxtranstructors;

import com.vorquel.similsaxtranstructors.client.BlockOverlay;
import com.vorquel.similsaxtranstructors.registry.ConfigHandlerST;
import com.vorquel.similsaxtranstructors.registry.SimilsaxRegistry;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(SimilsaxTranstructors.MODID)
public class SimilsaxTranstructors {

  public static final String MODID = "similsaxtranstructors";
  //  static Logger log = LogManager.getLogger(MODID);

  public SimilsaxTranstructors(IEventBus eventBus, ModContainer container, Dist dist) {
    container.registerConfig(ModConfig.Type.COMMON, ConfigHandlerST.CONFIG, MODID + ".toml");
    SimilsaxRegistry.ITEMS.register(eventBus);

    eventBus.addListener(this::addTabContents);

    if (dist.isClient()) {
      NeoForge.EVENT_BUS.register(new BlockOverlay());
    }
  }

  private void addTabContents(final BuildCreativeModeTabContentsEvent event) {
    if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
      event.accept(SimilsaxRegistry.BASIC);
      event.accept(SimilsaxRegistry.ADVANCED);
    }
  }
}
