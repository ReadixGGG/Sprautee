package org.zonarstudio.spraute_engine.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * S2C: open scripted overlay (JSON layout). Supports multiple overlays via overlayId.
 */
public class OpenSprauteOverlayPacket {
    private final String overlayId;
    private final String json;

    public OpenSprauteOverlayPacket(String overlayId, String json) {
        this.overlayId = overlayId != null ? overlayId : "";
        this.json = json;
    }

    public String getOverlayId() {
        return overlayId;
    }

    public String getJson() {
        return json;
    }

    public static void encode(OpenSprauteOverlayPacket msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.overlayId, 256);
        buf.writeUtf(msg.json, SprauteUiPackets.MAX_JSON_CHARS);
    }

    public static OpenSprauteOverlayPacket decode(FriendlyByteBuf buf) {
        return new OpenSprauteOverlayPacket(buf.readUtf(256), buf.readUtf(SprauteUiPackets.MAX_JSON_CHARS));
    }

    public static void handle(OpenSprauteOverlayPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                        org.zonarstudio.spraute_engine.client.SprauteScriptScreen.openOverlay(msg.overlayId, msg.json)));
        ctx.get().setPacketHandled(true);
    }
}
