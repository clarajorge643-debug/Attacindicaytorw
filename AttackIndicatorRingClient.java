package com.attackring;

import net.fabricmc.api.ClientModInitializer;

public final class AttackIndicatorRingClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // The HUD replacement is handled by InGameHudMixin.
    }
}
