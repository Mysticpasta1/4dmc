package com.gmail.inayakitorikhurram.fdmc.networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class FDMCPacketThree implements IMessage {
    public FDMCPacketThree() {
    }

    public int stepDirection;

    public FDMCPacketThree(int stepDirection) {
        this.stepDirection = stepDirection;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(stepDirection);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        stepDirection = buf.readInt();
    }
}