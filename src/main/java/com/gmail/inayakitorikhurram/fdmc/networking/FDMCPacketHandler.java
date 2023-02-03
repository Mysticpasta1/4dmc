package com.gmail.inayakitorikhurram.fdmc.networking;

import com.gmail.inayakitorikhurram.fdmc.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class FDMCPacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);

    public static void registerNetworking() {
        INSTANCE.registerMessage(FDMCMessageHandler.class, FDMCPacket.class, 0, Side.SERVER);
        INSTANCE.registerMessage(FDMCMessageHandlerTwo.class, FDMCPacketTwo.class, 1, Side.SERVER);
        INSTANCE.registerMessage(FDMCMessageHandlerThree.class, FDMCPacketThree.class, 2, Side.SERVER);
    }
}
