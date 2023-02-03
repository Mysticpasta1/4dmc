package com.gmail.inayakitorikhurram.fdmc;

import com.gmail.inayakitorikhurram.fdmc.mixininterfaces.CanStep;
import com.gmail.inayakitorikhurram.fdmc.networking.FDMCPacket;
import com.gmail.inayakitorikhurram.fdmc.networking.FDMCPacketHandler;
import com.gmail.inayakitorikhurram.fdmc.networking.FDMCPacketThree;
import com.gmail.inayakitorikhurram.fdmc.networking.FDMCPacketTwo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Side.CLIENT)
public class FDMCClientEntrypoint extends FDMCCommonEntrypoint {

    public static final KeyBinding move_kata = new KeyBinding(
            "key.fdmc.moveKata", // The translation key of the keybinding's name
            Keyboard.KEY_Q, // The keycode of the key
            "category.fdmc.movement" // The translation key of the keybinding's category.
    );

    public static final KeyBinding move_ana = new KeyBinding(
            "key.fdmc.moveAna", // The translation key of the keybinding's name
            Keyboard.KEY_E, // The keycode of the key
            "category.fdmc.movement" // The translation key of the keybinding's category.
    );

    @Override
    public void init() {
        ClientRegistry.registerKeyBinding(move_kata);
        ClientRegistry.registerKeyBinding(move_ana);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        int moveDirection = (move_kata.isPressed() ? -1 : 0) + (move_ana.isPressed() ? 1 : 0);

        if (moveDirection != 0 && player != null && ((CanStep) player).canStep(moveDirection)) {
            FDMCPacketHandler.INSTANCE.sendToServer(new FDMCPacketThree(moveDirection));
        }
    }
}
