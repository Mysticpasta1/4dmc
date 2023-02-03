package com.gmail.inayakitorikhurram.fdmc;

import com.gmail.inayakitorikhurram.fdmc.mixininterfaces.CanStep;
import com.gmail.inayakitorikhurram.fdmc.networking.FDMCPacket;
import com.gmail.inayakitorikhurram.fdmc.networking.FDMCPacketHandler;
import com.gmail.inayakitorikhurram.fdmc.networking.FDMCPacketThree;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.ticket.ChunkTicketManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.function.Predicate;

import static com.gmail.inayakitorikhurram.fdmc.Reference.*;

@Mod(modid = MODID, name = NAME, version = VERSION)
public class FDMCMainEntrypoint {
	public FDMCMainEntrypoint() {}

	public static final Logger LOGGER = LoggerFactory.getLogger(FDMCMainEntrypoint.class);

	private ArrayList<ChunkPos> forceLoadedChunks = new ArrayList<>();

	@SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = COMMON_PROXY_CLASS)
	public static FDMCCommonEntrypoint proxy;

	@Mod.EventHandler
	private void init(FMLInitializationEvent event) {
		proxy.init();
		FDMCPacketHandler.registerNetworking();
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
		int moveDirection = buf.getInt(0);
		LOGGER.error("Move player command received by server to move in direction " + (moveDirection == 1 ? "kata" : "ana"));

		EntityPlayer playerSP = event.player;

		if(playerSP instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) playerSP;
			Vec3d vel = player.getPositionVector();
			buf.writeDouble(vel.x);
			buf.writeDouble(vel.y);
			buf.writeDouble(vel.z);
			Vec3d newPos = player.getPositionVector().add(moveDirection * FDMCConstants.STEP_DISTANCE, 0, 0);
			double[] pos4 = FDMCMath.toPos4(newPos);
			player.attemptTeleport(newPos.x, newPos.y, newPos.z);
			FDMCPacketHandler.INSTANCE.sendToServer(new FDMCPacket(moveDirection, vel.x, vel.y, vel.z));
			player.sendMessage(new TextComponentString(
					"Moving " + player.getEntityId() + " " + (moveDirection == 1 ? "kata" : "ana") + "\n" +
							pos4[0] + "\n" +
							pos4[1] + "\n" +
							pos4[2] + "\n" +
							pos4[3] + "\n"
			));
			player.getServerWorld().addScheduledTask(() -> {
				updateChunks(player.getServerWorld());
			});
		}
	}

	public void updateChunks(WorldServer world){
		ArrayList<ChunkPos> chunksToAdd = new ArrayList<>();
		ArrayList<ChunkPos> chunksToRemove = new ArrayList<>();
		ChunkProviderServer cm = world.getChunkProvider();
		int simDistance = 10; //TODO get properly

		//find out what chunks should be loaded
		int[] playerPos4;
		int[] chunkPos4;
		for(EntityPlayer playerSP : world.playerEntities) {
			if (playerSP instanceof EntityPlayerMP) {
				EntityPlayerMP player = (EntityPlayerMP) playerSP;
				playerPos4 = FDMCMath.toChunkPos4(new ChunkPos(player.chunkCoordX, player.chunkCoordZ));
				for (int k = 1; k < simDistance / 2; k++) {
					int sliceSimDistance = simDistance - 2 * k;
					for (int i = -sliceSimDistance; i <= sliceSimDistance; i++) {
						for (int j = -sliceSimDistance; j <= sliceSimDistance; j++) {
							if (i * i + j * j < sliceSimDistance * sliceSimDistance) {
								chunkPos4 = playerPos4;
								chunkPos4[0] += i;
								chunkPos4[1] += j;
								chunkPos4[2] += k;
								chunksToAdd.add(FDMCMath.toChunkPos3(chunkPos4));
							}
						}
					}
				}
			}
		}

		/**
		 for(ChunkPos chunkPos : forceLoadedChunks){
		 chunkPos4 = FDMCMath.toChunkPos4(chunkPos);
		 int smallestDistanceSquared = Integer.MAX_VALUE;
		 for(ServerPlayerEntity player : world.getPlayers()){
		 playerPos4 = FDMCMath.toChunkPos4(player.getChunkPos());
		 int chunkDistanceSquared =
		 (chunkPos4[0] - playerPos4[0]) * (chunkPos4[0] - playerPos4[0]) +
		 (chunkPos4[1] - playerPos4[1]) * (chunkPos4[1] - playerPos4[1]) +
		 4 * (chunkPos4[2] - playerPos4[2]) * (chunkPos4[2] - playerPos4[2]);
		 if(chunkDistanceSquared < smallestDistanceSquared){
		 smallestDistanceSquared = chunkDistanceSquared;
		 }
		 }
		 tm.removePersistentTickets(); //TODO only remove those unavailable
		 //tm.addTicketWithLevel(ChunkTicketType.PLAYER, chunkPos, i, chunkPos);
		 }
		 **/
	}
}
