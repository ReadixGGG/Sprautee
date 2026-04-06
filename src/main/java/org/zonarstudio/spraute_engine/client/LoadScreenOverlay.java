package org.zonarstudio.spraute_engine.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.zonarstudio.spraute_engine.Spraute_engine;

@Mod.EventBusSubscriber(modid = Spraute_engine.MODID, value = Dist.CLIENT)
public class LoadScreenOverlay {

    public static boolean active = false;
    private static long startTime = 0;

    public static void trigger() {
        active = true;
        startTime = System.currentTimeMillis();
    }

    public static void cancel() {
        active = false;
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        if (!active) return;

        long elapsed = System.currentTimeMillis() - startTime;

        if (elapsed > 5000) {
            active = false;
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        int width = event.getWindow().getGuiScaledWidth();
        int height = event.getWindow().getGuiScaledHeight();
        PoseStack poseStack = event.getPoseStack();

        float darkAlpha = 1.0f;
        if (elapsed > 4000) {
            darkAlpha = 1.0f - ((elapsed - 4000) / 1000f);
        }

        float logoAlpha = 0.0f;
        if (elapsed >= 500 && elapsed < 1500) {
            logoAlpha = (elapsed - 500) / 1000f;
        } else if (elapsed >= 1500 && elapsed < 3000) {
            logoAlpha = 1.0f;
        } else if (elapsed >= 3000 && elapsed < 4000) {
            logoAlpha = 1.0f - ((elapsed - 3000) / 1000f);
        }

        int darkColor = ((int) (darkAlpha * 255) << 24) | 0x000000;
        GuiComponent.fill(poseStack, 0, 0, width, height, darkColor);

        if (logoAlpha > 0) {
            com.mojang.blaze3d.systems.RenderSystem.setShader(net.minecraft.client.renderer.GameRenderer::getPositionTexShader);
            com.mojang.blaze3d.systems.RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, logoAlpha);
            com.mojang.blaze3d.systems.RenderSystem.setShaderTexture(0, new net.minecraft.resources.ResourceLocation(Spraute_engine.MODID, "textures/gui/logo.png"));
            com.mojang.blaze3d.systems.RenderSystem.enableBlend();
            com.mojang.blaze3d.systems.RenderSystem.defaultBlendFunc();

            int texWidth = 1356;
            int texHeight = 470;
            
            // Задаем ширину логотипа на экране (например, 250 пикселей)
            int logoWidth = 250;
            int logoHeight = (int) (logoWidth * ((float) texHeight / texWidth));
            
            int x = (width - logoWidth) / 2;
            int y = (height - logoHeight) / 2;

            poseStack.pushPose();
            GuiComponent.blit(poseStack, x, y, logoWidth, logoHeight, 0, 0, texWidth, texHeight, texWidth, texHeight);
            poseStack.popPose();
            
            com.mojang.blaze3d.systems.RenderSystem.disableBlend();
            com.mojang.blaze3d.systems.RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
