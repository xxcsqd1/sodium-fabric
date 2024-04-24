package net.caffeinemc.mods.sodium.client.neoforge;

import net.caffeinemc.mods.sodium.client.SodiumClientMod;
import net.caffeinemc.mods.sodium.client.gui.SodiumOptionsGUI;
import net.caffeinemc.mods.sodium.client.neoforge.iecompat.ImmersiveEngineeringCompat;
import net.caffeinemc.mods.sodium.client.neoforge.iecompat.SodiumConnectionRenderer;
import net.caffeinemc.mods.sodium.client.render.frapi.SodiumRenderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod("sodium")
public class SodiumForgeMod {
    public SodiumForgeMod(IEventBus bus) {
        SodiumClientMod.onInitialization(ModList.get().getModContainerById("sodium").get().getModInfo().getVersion().toString());
        bus.addListener(this::onResourceReload);
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (minecraft, screen) -> SodiumOptionsGUI.createScreen(screen));
        RendererAccess.INSTANCE.registerRenderer(SodiumRenderer.INSTANCE);
    }

    public void onResourceReload(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(SpriteFinderCache.ReloadListener.INSTANCE);
        if (ImmersiveEngineeringCompat.isLoaded) {
            event.registerReloadListener(new SodiumConnectionRenderer());
        }
    }
}