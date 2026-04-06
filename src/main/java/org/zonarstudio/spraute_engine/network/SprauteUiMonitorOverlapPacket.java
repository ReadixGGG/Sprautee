package org.zonarstudio.spraute_engine.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.zonarstudio.spraute_engine.client.SprauteScriptScreen;

import java.util.function.Supplier;

public class SprauteUiMonitorOverlapPacket {
    public final String id1;
    public final String id2;
    public final boolean monitor;

    public SprauteUiMonitorOverlapPacket(String id1, String id2, boolean monitor) {
        this.id1 = id1;
        this.id2 = id2;
        this.monitor = monitor;
    }

    public static void encode(SprauteUiMonitorOverlapPacket msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.id1);
        buf.writeUtf(msg.id2);
        buf.writeBoolean(msg.monitor);
    }

    public static SprauteUiMonitorOverlapPacket decode(FriendlyByteBuf buf) {
        return new SprauteUiMonitorOverlapPacket(buf.readUtf(), buf.readUtf(), buf.readBoolean());
    }

    public static void handle(SprauteUiMonitorOverlapPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            String pair = msg.id1 + ":" + msg.id2;
            if (msg.monitor) {
                SprauteScriptScreen.monitorOverlaps.add(pair);
            } else {
                SprauteScriptScreen.monitorOverlaps.remove(pair);
                SprauteScriptScreen.overlapState.remove(pair);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}