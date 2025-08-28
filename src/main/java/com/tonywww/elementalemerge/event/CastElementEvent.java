package com.tonywww.elementalemerge.event;

import com.tonywww.elementalemerge.elements.BasicElement;
import net.minecraftforge.eventbus.api.Event;

public class CastElementEvent extends Event {
    private final BasicElement element;

    public CastElementEvent(BasicElement element) {
        this.element = element;
    }

}
