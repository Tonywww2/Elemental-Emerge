package com.tonywww.elementalemerge.elements;

import com.tonywww.elementalemerge.block.ElementChargeableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ElectricElement extends BasicElement {


    public ElectricElement(BlockPos pos, byte power, ServerLevel level) {
        super(pos, power, level);
    }

    @Override
    public boolean doBlockEffects() {
//        if (!this.level.getBlockState(this.pos).is(Blocks.AIR)) {
//            this.level.setBlock(this.pos, Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
//
//        }
        if (this.level.getBlockState(this.pos).getBlock() instanceof ElementChargeableBlock) {
            BlockState newState = ElementChargeableBlock.setElementType(this.level.getBlockState(this.pos), ElementType.ELECTRIC, 1);
            level.setBlock(pos, newState, 3);
        }
        this.level.sendParticles(
                ParticleTypes.SMOKE,
                this.pos.getX(),
                this.pos.getY(),
                this.pos.getZ(),
                10,
                1d,
                1d,
                1d,
                0d
        );
        return true;
    }

    @Override
    public boolean doEntityEffects(Entity entity) {
        entity.hurt(entity.damageSources().inFire(), 1f);
        return true;
    }

    @Override
    public BasicElement getSpreadElement(BlockPos targetPos) {
        return new ElectricElement(targetPos, (byte) (this.power - 1), this.level);
    }
}
