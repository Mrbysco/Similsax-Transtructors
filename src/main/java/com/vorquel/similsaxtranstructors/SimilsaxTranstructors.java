package com.vorquel.similsaxtranstructors;

import com.vorquel.similsaxtranstructors.client.BlockOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(SimilsaxTranstructors.MODID)
public class SimilsaxTranstructors {

  public static final String MODID = "similsaxtranstructors";
  //  static Logger log = LogManager.getLogger(MODID);

  public SimilsaxTranstructors() {
    IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    ConfigHandler.loadConfig(ConfigHandler.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MODID + ".toml"));

    SimilsaxRegistry.ITEMS.register(eventBus);

    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
      MinecraftForge.EVENT_BUS.register(new BlockOverlay());
    });
  }
}
