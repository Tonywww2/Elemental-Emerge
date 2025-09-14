package com.tonywww.elementalemerge.block;

import com.tonywww.elementalemerge.elements.ElementType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import javax.annotation.Nullable;

public class BlockTestChargeable extends Block implements ElementChargeableBlock {

    public static final EnumProperty<ElementType> ELEMENT_TYPE = ElementChargeableBlock.ELEMENT_TYPE;
    public static final IntegerProperty AGE = ElementChargeableBlock.AGE;

    public BlockTestChargeable(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(ELEMENT_TYPE, ElementType.NONE)
                .setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ELEMENT_TYPE);
        builder.add(AGE);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(ELEMENT_TYPE, ElementType.NONE)
                .setValue(AGE, 0);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource randomSource) {
        int age = ElementChargeableBlock.getAge(state);
        if (age <= 0) {
            serverLevel.setBlock(pos, ElementChargeableBlock.setElementType(state, ElementType.NONE, 0), 3);
        } else {
            BlockState newState = ElementChargeableBlock.setAge(state, age - 1);
            serverLevel.setBlock(pos, newState, 3);
            serverLevel.sendParticles(
                    ParticleTypes.FLAME,
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    32,
                    1d,
                    1d,
                    1d,
                    0d
            );
        }
        super.randomTick(state, serverLevel, pos, randomSource);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource randomSource) {
        int age = ElementChargeableBlock.getAge(state);
        ElementType elementType = ElementChargeableBlock.getElementType(state);

        if (age > 0 && elementType != ElementType.NONE) {
            level.addParticle(ParticleTypes.FLAME,
                    pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d,
                    0d, 0.05d, 0d);
        }

        super.animateTick(state, level, pos, randomSource);
    }
}
