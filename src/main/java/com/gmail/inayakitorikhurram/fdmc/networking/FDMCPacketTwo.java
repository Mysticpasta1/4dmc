package com.gmail.inayakitorikhurram.fdmc.networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class FDMCPacketTwo implements IMessage {
    public FDMCPacketTwo() {
    }

    public boolean pushDirection;

    public FDMCPacketTwo(boolean pushDirection) {
        this.pushDirection = pushDirection;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(pushDirection);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pushDirection = buf.readBoolean();
    }
}