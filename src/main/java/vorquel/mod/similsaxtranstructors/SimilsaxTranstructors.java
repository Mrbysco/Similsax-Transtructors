package vorquel.mod.similsaxtranstructors;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
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
  public static final ItemSimilsaxTranstructor itemBasic = new ItemSimilsaxTranstructor("Basic");
  public static final ItemSimilsaxTranstructor itemAdvanced = new ItemSimilsaxTranstructor("Advanced");
  static Logger log = LogManager.getLogger(MODID);


  public SimilsaxTranstructors() {
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    MinecraftForge.EVENT_BUS.register(this);

    ConfigHandler.loadConfig(ConfigHandler.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MODID + ".toml"));
  }

  private void setup(final FMLCommonSetupEvent event) {
  }

//  @Mod.EventHandler
//  public void preInit(FMLPreInitializationEvent event) {
//    Config.init(event.getSuggestedConfigurationFile());
//    ConfigSynchonizer.init();
//
//    proxy.registerItemModel();
//
//
//    MinecraftForge.EVENT_BUS.register(RegistryThing.class);
// }
//  @Mod.EventHandler
//  public void init(FMLInitializationEvent event) {
//    if (showOverlay){ proxy.registerBlockOverlay();}
//    MinecraftForge.EVENT_BUS.register(new ConfigSynchonizer());


//  @Mod.EventHandler
//  public void serverStarting(FMLServerStartingEvent event) {
//    log.info("Syncing Server configs");
//    itemBasic.setUses(Config.basicUses);
//    itemAdvanced.setUses(Config.advancedUses);
//    itemBasic.range = Config.basicRange;
//    itemAdvanced.range = Config.advancedRange;
//  }
}
