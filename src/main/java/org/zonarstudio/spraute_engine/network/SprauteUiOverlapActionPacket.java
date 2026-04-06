package org.zonarstudio.spraute_engine.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.zonarstudio.spraute_engine.script.ScriptManager;

import java.util.function.Supplier;

public class SprauteUiOverlapActionPacket {
    public final String id1;
    public final String id2;
    public final boolean overlapping;

    public SprauteUiOverlapActionPacket(String id1, String id2, boolean overlapping) {
        this.id1 = id1;
        this.id2 = id2;
        this.overlapping = overlapping;
    }

    public static void encode(SprauteUiOverlapActionPacket msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.id1);
        buf.writeUtf(msg.id2);
        buf.writeBoolean(msg.overlapping);
    }

    public static SprauteUiOverlapActionPacket decode(FriendlyByteBuf buf) {
        return new SprauteUiOverlapActionPacket(buf.readUtf(), buf.readUtf(), buf.readBoolean());
    }

    public static void handle(SprauteUiOverlapActionPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            net.minecraft.server.level.ServerPlayer sender = ctx.get().getSender();
            if (sender != null) {
                ScriptManager manager = ScriptManager.getInstance();
                if (manager != null) {
                    manager.onUiOverlapAction(sender, msg.id1, msg.id2, msg.overlapping);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}