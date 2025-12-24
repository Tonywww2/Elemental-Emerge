package com.tonywww.elementalemerge.elements;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class IceElement extends BasicElement {

    IceElement(BlockPos pos, byte power, ServerLevel level) {
        super(pos, power, level, ElementType.ICE);
    }

    @Override
    public boolean doBlockEffects() {
        return super.doBlockEffects();
    }

    @Override
    public boolean doEntityEffects(Entity entity) {
        return false;
    }

    @Override
    public BasicElement getSpreadElement(BlockPos targetPos) {
        return new IceElement(targetPos, (byte) (this.power - 1), this.level);
    }
}
