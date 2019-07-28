package com.vorquel.similsaxtranstructors.setup;

import net.minecraft.world.World;

public interface IProxy {
	World getClientWorld();


	public void registerBlockOverlay();
}
