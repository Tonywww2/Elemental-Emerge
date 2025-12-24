package com.tonywww.elementalemerge.elements;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class ElectricElement extends BasicElement {

    public ElectricElement(BlockPos pos, byte power, ServerLevel level) {
        super(pos, power, level, ElementType.ELECTRIC);
    }

    @Override
    public boolean doBlockEffects() {
        return super.doBlockEffects();
    }

    @Override
    public boolean doEntityEffects(Entity entity) {
//        entity.hurt(entity.damageSources().inFire(), 1f);
        return true;
    }

    @Override
    public BasicElement getSpreadElement(BlockPos targetPos) {
        return new ElectricElement(targetPos, (byte) (this.power - 1), this.level);
    }
}
