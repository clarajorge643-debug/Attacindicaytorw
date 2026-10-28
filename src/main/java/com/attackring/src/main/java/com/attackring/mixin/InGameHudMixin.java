
package com.attackring.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "renderCrosshair", at = @At("TAIL"))
    private void attackIndicatorRing$render(
            DrawContext context,
            CallbackInfo ci
    ) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null || client.options == null) {
            return;
        }

        if (client.options.getAttackIndicator().getValue()
                == net.minecraft.client.option.AttackIndicator.OFF) {
            return;
        }

        float progress = client.player.getAttackCooldownProgress(0.0F);
        progress = MathHelper.clamp(progress, 0.0F, 1.0F);

        int centerX = context.getScaledWindowWidth() / 2;
        int centerY = context.getScaledWindowHeight() / 2 + 15;

        int radius = 8;
        int segments = 40;
        int activeSegments = Math.round(progress * segments);

        for (int i = 0; i < segments; i++) {
            double angle = -Math.PI / 2.0
                    + (2.0 * Math.PI * i / segments);

            int x = centerX + (int) Math.round(Math.cos(angle) * radius);
            int y = centerY + (int) Math.round(Math.sin(angle) * radius);

            int color = i < activeSegments
                    ? 0xFF55FF55
                    : 0xAA555555;

            context.fill(x, y, x + 2, y + 2, color);
        }
    }
}
