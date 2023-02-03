package com.gmail.inayakitorikhurram.fdmc.networking;

import com.gmail.inayakitorikhurram.fdmc.mixininterfaces.CanStep;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FDMCMessageHandlerThree implements IMessageHandler<FDMCPacketThree, IMessage> {

    public IMessage onMessage(FDMCPacketThree message, MessageContext ctx) {
        ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
            int stepDirection = message.stepDirection;
            ((CanStep) Minecraft.getMinecraft().player).scheduleStep(stepDirection);
        });
        return null;
    }
}
