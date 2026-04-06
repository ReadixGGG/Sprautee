package org.zonarstudio.spraute_engine.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * S2C: close a specific overlay by id, or all overlays if id is empty.
 */
public class CloseSprauteOverlayPacket {
    private final String overlayId;

    public CloseSprauteOverlayPacket() {
        this.overlayId = "";
    }

    public CloseSprauteOverlayPacket(String overlayId) {
        this.overlayId = overlayId != null ? overlayId : "";
    }

    public static void encode(CloseSprauteOverlayPacket msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.overlayId, 256);
    }

    public static CloseSprauteOverlayPacket decode(FriendlyByteBuf buf) {
        return new CloseSprauteOverlayPacket(buf.readUtf(256));
    }

    public static void handle(CloseSprauteOverlayPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                        org.zonarstudio.spraute_engine.client.SprauteScriptScreen.closeOverlay(msg.overlayId)));
        ctx.get().setPacketHandled(true);
    }
}
