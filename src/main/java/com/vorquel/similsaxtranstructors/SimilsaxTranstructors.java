package com.vorquel.similsaxtranstructors;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//, name = "Similsax Transtructors", updateJSON = "https://raw.githubusercontent.com/PrinceOfAmber/Similsax-Transtructors/master/update.json"
@Mod(SimilsaxTranstructors.MODID)
public class SimilsaxTranstructors {

  public static final String MODID = "similsaxtranstructors";
  //  public static final ItemSimilsaxTranstructor itemBasic = new ItemSimilsaxTranstructor("Basic");
  //  public static final ItemSimilsaxTranstructor itemAdvanced = new ItemSimilsaxTranstructor("Advanced");
  static Logger log = LogManager.getLogger(MODID);

  public SimilsaxTranstructors() {
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    MinecraftForge.EVENT_BUS.register(this);
    MinecraftForge.EVENT_BUS.register(new BlockOverlay());
    ConfigHandler.loadConfig(ConfigHandler.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MODID + ".toml"));
  }

  private void setup(final FMLCommonSetupEvent event) {
  }

  @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class RegistryEvents {

    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
      event.getRegistry().register(new ItemSimilsax(new Item.Properties().maxStackSize(1)
      .group(RegistryThing.itemGroup )).setRegistryName("transtructor_basic"));
    }
  }
}
