package com.gmail.inayakitorikhurram.fdmc.mixininterfaces;

import com.gmail.inayakitorikhurram.fdmc.supportstructure.SupportHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public interface CanStep {

    boolean scheduleStep(int moveDirection);
    boolean step(int moveDirection);
    int getStepDirection();
    boolean isStepping();
    void setSteppingLocally(int stepDirection, Vec3d vel);
    void setSteppingGlobally(EntityPlayerMP player, int stepDirection, Vec3d vel);
    boolean[] getPushableDirections();
    void updatePushableDirectionsLocally(boolean[] pushableDirection);
    void updatePushableDirectionsGlobally(EntityPlayerMP player);
    boolean canStep(int stepDirection);
    SupportHandler getSupportHandler();

    boolean doesCollideWithBlocksAt(BlockPos pos);

    boolean doesCollideWithBlocksAt(BlockPos offset, boolean fromOffset);
    boolean doesCollideWithBlocks();
}
