package com.tonywww.elementalemerge.block;

import com.tonywww.elementalemerge.elements.ElementType;
import com.tonywww.elementalemerge.registeries.ModParticles;
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
                    ElementChargeableBlock.getElementType(state).getParticleOption(),
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

        if (
                age > 0 &&
                        (((int) level.getGameTime()) % (20 / age)) == 0 &&
                        elementType != ElementType.NONE
        ) {
            double x = pos.getX();
            double y = pos.getY();
            double z = pos.getZ();

            double[][] midpoints = {
                    {x + 0.5, y, z}, {x + 0.5, y + 1, z}, {x + 0.5, y, z + 1}, {x + 0.5, y + 1, z + 1},
                    {x, y + 0.5, z}, {x + 1, y + 0.5, z}, {x, y + 0.5, z + 1}, {x + 1, y + 0.5, z + 1},
                    {x, y, z + 0.5}, {x + 1, y, z + 0.5}, {x, y + 1, z + 0.5}, {x + 1, y + 1, z + 0.5}
            };

            for (double[] tPos : midpoints) {
                level.addParticle(
                        elementType.getParticleOption(),
                        tPos[0], tPos[1], tPos[2],
                        0d, 0d, 0d);
            }
        }


        super.animateTick(state, level, pos, randomSource);
    }
}
