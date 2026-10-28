package com.attackring.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.AttackIndicator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    private static final int RING_RADIUS = 6;
    private static final int DOT_SIZE = 2;
    private static final int SEGMENTS = 64;
    private static final int BACKGROUND_COLOR = 0xA0202020;
    private static final int PROGRESS_COLOR = 0xFFF2F2F2;

    @Inject(method = "renderAttackIndicator", at = @At("HEAD"), cancellable = true)
    private void attackIndicatorRing$replaceIndicator(DrawContext context, int scaledWidth, int scaledHeight, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options == null) {
            return;
        }

        AttackIndicator status = client.options.getAttackIndicator().getValue();
        if (status == AttackIndicator.OFF) {
            return;
        }

        // Cancel the vanilla bar/icon and draw our ring just below the crosshair.
        ci.cancel();
        float cooldown = client.player.getAttackCooldownProgress(0.0F);
        int centerX = scaledWidth / 2;
        int centerY = scaledHeight / 2 + 12;

        drawRing(context, centerX, centerY, 0.0F, 1.0F, BACKGROUND_COLOR);
        if (cooldown > 0.0F) {
            drawRing(context, centerX, centerY, 0.0F, Math.min(1.0F, cooldown), PROGRESS_COLOR);
        }
    }

    private static void drawRing(DrawContext context, int centerX, int centerY, float startProgress, float endProgress, int color) {
        int first = Math.max(0, (int) Math.floor(startProgress * SEGMENTS));
        int last = Math.min(SEGMENTS, (int) Math.ceil(endProgress * SEGMENTS));
        for (int i = first; i < last; i++) {
            double angle = (Math.PI * 2.0 * i / SEGMENTS) - (Math.PI / 2.0);
            int x = centerX + (int) Math.round(Math.cos(angle) * RING_RADIUS);
            int y = centerY + (int) Math.round(Math.sin(angle) * RING_RADIUS);
            context.fill(x, y, x + DOT_SIZE, y + DOT_SIZE, color);
        }
    }
}
