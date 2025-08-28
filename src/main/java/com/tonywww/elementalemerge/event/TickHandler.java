package com.tonywww.elementalemerge.event;

import com.google.common.eventbus.Subscribe;
import com.tonywww.elementalemerge.ElementalEmerge;
import com.tonywww.elementalemerge.persist.AllElementsWorldStorage;
import net.minecraftforge.event.TickEvent;

public class TickHandler {

    private static TickHandler INSTANCE;
    private int tick = 0;
    public boolean ticked = false;

    public static final int MAX_OPERATION_PER_TICK = 1000;

    private TickHandler() {

    }

    public static TickHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TickHandler();
        }
        return INSTANCE;
    }

    @Subscribe
    public void onTick(TickEvent event) {
        if (event.type != TickEvent.Type.SERVER || event.phase != TickEvent.Phase.END) return;
        int operationCount = 0;
        AllElementsWorldStorage.getInstance(ElementalEmerge._instance)


        this.tick++;

    }
}
