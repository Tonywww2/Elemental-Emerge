package com.tonywww.elementalemerge.elements;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.Objects;

public abstract class BasicElement {
    public final BlockPos pos;
    public final short power;
    public final ServerLevel level;

    BasicElement(BlockPos pos, short power, ServerLevel level) {
      this.pos = pos;
      this.power = power;
      this.level = level;
    }

    public abstract boolean doBlockEffects();

    public abstract boolean doEntityEffects();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicElement that)) return false;
        return power == that.power && Objects.equals(pos, that.pos);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pos);
    }
}
