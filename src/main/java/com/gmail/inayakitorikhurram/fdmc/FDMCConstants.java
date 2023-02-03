package com.gmail.inayakitorikhurram.fdmc;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import net.minecraft.util.ResourceLocation;

public class FDMCConstants {

    public static final float HUE_NEITHER = 0;
    public static final float HUE_KATA = 0;
    public static final float HUE_ANA = 0;
    public static final float HUE_BOTH = 0;

    public static ResourceLocation MOVING_PLAYER_ID = new ResourceLocation("fdmc:moving_player");
    public static ResourceLocation UPDATE_COLLISION_MOVEMENT = new ResourceLocation("fdmc:update_collision_movement");
    public static int STEP_DISTANCE = 1<<18;
    public static int CHUNK_STEP_DISTANCE = STEP_DISTANCE>>4;
    public static int FDMC_CHUNK_SCALE = 2;
    public static int FDMC_BLOCK_SCALE = FDMC_CHUNK_SCALE<<4;

    public static final Logger LOGGER = LoggerFactory.getLogger(FDMCMainEntrypoint.class);

}
