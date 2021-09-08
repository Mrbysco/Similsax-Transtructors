package com.vorquel.similsaxtranstructors;

import com.vorquel.similsaxtranstructors.client.BlockOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(SimilsaxTranstructors.MODID)
public class SimilsaxTranstructors {

	public static final String MODID = "similsaxtranstructors";
	static Logger log = LogManager.getLogger(MODID);

	public SimilsaxTranstructors() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		MinecraftForge.EVENT_BUS.register(this);
		ConfigHandler.loadConfig(ConfigHandler.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MODID + ".toml"));

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			MinecraftForge.EVENT_BUS.register(new BlockOverlay());
		});
	}

	private void setup(final FMLCommonSetupEvent event) {}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {

		@SubscribeEvent
		public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
			event.getRegistry().register(new ItemSimilsax(new Item.Properties().maxStackSize(1)
					.maxDamage(800)).setRegistryName("transtructor_basic"));
			event.getRegistry().register(new ItemSimilsax(new Item.Properties().maxStackSize(1)
					.maxDamage(9000)).setRegistryName("transtructor_advanced"));
		}
	}
}
