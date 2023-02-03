package com.gmail.inayakitorikhurram.fdmc.networking;

import com.gmail.inayakitorikhurram.fdmc.FDMCConstants;
import com.gmail.inayakitorikhurram.fdmc.mixininterfaces.CanStep;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FDMCMessageHandler implements IMessageHandler<FDMCPacket, IMessage> {

    @Override
    public IMessage onMessage(FDMCPacket message, MessageContext ctx) {
        EntityPlayerMP serverPlayer = ctx.getServerHandler().player;

        serverPlayer.getServerWorld().addScheduledTask(() -> {
            CanStep steppingPlayer = (CanStep) Minecraft.getMinecraft().player;
            int stepDirection = message.stepDirection;
            if (stepDirection != 0) { //start stepping tick
                Vec3d vel = new Vec3d(
                        message.vecX,
                        message.vecY,
                        message.vecZ);
                steppingPlayer.setSteppingLocally(stepDirection, vel);
            } else {
                steppingPlayer.setSteppingLocally(0, null);
            }
        });
        return null;
    }
}
