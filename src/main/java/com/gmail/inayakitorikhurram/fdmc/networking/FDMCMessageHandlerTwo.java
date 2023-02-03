package com.gmail.inayakitorikhurram.fdmc.networking;

import com.gmail.inayakitorikhurram.fdmc.mixininterfaces.CanStep;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FDMCMessageHandlerTwo implements IMessageHandler<FDMCPacketTwo, IMessage> {
    @Override
    public IMessage onMessage(FDMCPacketTwo message, MessageContext ctx) {
        EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
        serverPlayer.getServerWorld().addScheduledTask(() -> {
            CanStep steppingPlayer = (CanStep) Minecraft.getMinecraft().player;
            boolean[] pushableDirection = new boolean[6];
            for (int i = 0; i < 6; i++) {
                pushableDirection[i] = message.pushDirection;
            }
            steppingPlayer.updatePushableDirectionsLocally(pushableDirection);
        });
        return null;
    }
}
