package com.gmail.inayakitorikhurram.fdmc.networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class FDMCPacket implements IMessage {
  public FDMCPacket(){}

  public int stepDirection;
  public double vecX;
  public double vecY;
  public double vecZ;


  public FDMCPacket(int stepDirection, double x, double y, double z) {
    this.stepDirection = stepDirection;
    this.vecX = x;
    this.vecY = y;
    this.vecZ = z;
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeInt(stepDirection);
    buf.writeDouble(vecX);
    buf.writeDouble(vecY);
    buf.writeDouble(vecZ);
  }

  @Override public void fromBytes(ByteBuf buf) {
    stepDirection = buf.readInt();
    vecX = buf.readDouble();
    vecY = buf.readDouble();
    vecZ = buf.readDouble();
  }
}