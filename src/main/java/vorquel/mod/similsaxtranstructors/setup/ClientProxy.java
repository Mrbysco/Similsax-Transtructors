package vorquel.mod.similsaxtranstructors.setup;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import vorquel.mod.similsaxtranstructors.BlockOverlay;

public class ClientProxy implements IProxy {
	@Override
	public World getClientWorld() {
		return Minecraft.getInstance().world;
	}
	//public void addScheduledTask(Runnable runnable) {
	//    Minecraft.getMinecraft().addScheduledTask(runnable);
	//  }

	@Override
	public void registerBlockOverlay() {
		MinecraftForge.EVENT_BUS.register(new BlockOverlay());
	}
}
