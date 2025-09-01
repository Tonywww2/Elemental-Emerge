package com.tonywww.elementalemerge.event;

import com.tonywww.elementalemerge.ElementalEmerge;
import com.tonywww.elementalemerge.persist.AllElementsWorldStorage;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CastElementEventHandler {

    @SubscribeEvent
    public static void onCastElement(CastElementEvent event) {
        AllElementsWorldStorage.getInstance(ElementalEmerge._instance)
                .addElement(event.getElement(), (int) event.getElement().level.getGameTime());
    }
}
