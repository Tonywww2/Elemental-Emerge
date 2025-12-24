package com.tonywww.elementalemerge;

import com.mojang.logging.LogUtils;
import com.tonywww.elementalemerge.event.TickHandler;
import com.tonywww.elementalemerge.persist.AllElementsWorldStorage;
import com.tonywww.elementalemerge.registeries.ModBlocks;
import com.tonywww.elementalemerge.registeries.ModCreativeModTabs;
import com.tonywww.elementalemerge.registeries.ModItems;
import com.tonywww.elementalemerge.registeries.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.cyclops.cyclopscore.init.ModBaseVersionable;
import org.cyclops.cyclopscore.proxy.ClientProxy;
import org.cyclops.cyclopscore.proxy.CommonProxy;
import org.cyclops.cyclopscore.proxy.IClientProxy;
import org.cyclops.cyclopscore.proxy.ICommonProxy;
import org.slf4j.Logger;

@Mod(ElementalEmerge.MOD_ID)
public class ElementalEmerge extends ModBaseVersionable<ElementalEmerge> {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "elementalemerge";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ElementalEmerge _instance;

    @SuppressWarnings("removal")
    public ElementalEmerge() {
        super(MOD_ID, (instance) -> _instance = instance);

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::clientSetup);

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        ModCreativeModTabs.register(eventBus);

        ModParticles.registerCommon(eventBus);

    }

    @Override
    protected void setup(FMLCommonSetupEvent event) {
        super.setup(event);

        MinecraftForge.EVENT_BUS.register(TickHandler.getInstance());
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    @Override
    protected IClientProxy constructClientProxy() {
        return new ClientProxy();
    }

    @Override
    protected ICommonProxy constructCommonProxy() {
        return new CommonProxy();
    }

    @SuppressWarnings("removal")
    public void clientSetup(final FMLClientSetupEvent event) {
        // Some client setup code
        LOGGER.info("HELLO FROM CLIENT SETUP");
        LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        event.enqueueWork(() -> {
            ModParticles.registerClient(bus);

        });
    }
}
