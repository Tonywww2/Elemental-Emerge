package com.tonywww.elementalemerge.elements;

import com.tonywww.elementalemerge.event.CastElementEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;

import java.util.Objects;

public abstract class BasicElement {
    public final BlockPos pos;
    public final byte power;
    public final ServerLevel level;

    BasicElement(BlockPos pos, byte power, ServerLevel level) {
      this.pos = pos;
      this.power = power;
      this.level = level;
    }

    public abstract boolean doBlockEffects();

    public abstract boolean doEntityEffects(Entity entity);

    /**
     * 获取扩散后的元素，当前元素的power应大于0，新元素的power应大于等于0
     * @param targetPos 新元素的目标位置
     * @return 新元素
     */
    public abstract BasicElement getSpreadElement(BlockPos targetPos);

    public void castEvent() {
         MinecraftForge.EVENT_BUS.post(new CastElementEvent(this));
    }

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
