package com.tonywww.elementalemerge.block;

import com.tonywww.elementalemerge.elements.ElementType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.common.extensions.IForgeBlock;

public interface ElementChargeableBlock extends IForgeBlock {

    // 定义元素类型属性
    EnumProperty<ElementType> ELEMENT_TYPE = EnumProperty.create("element_type", ElementType.class);
    IntegerProperty AGE = IntegerProperty.create("age", 0, 100);

    /**
     * 获取当前方块的元素类型
     */
    static ElementType getElementType(BlockState state) {
        return state.getValue(ELEMENT_TYPE);
    }

    /**
     * 获取当前方块的元素充能年龄
     */
    static int getAge(BlockState state) {
        return state.getValue(AGE);
    }

    /**
     * 设置方块的元素充能年龄
     */
    static BlockState setAge(BlockState state, int age) {
        return state.setValue(AGE, age);
    }

    /**
     * 设置方块的元素类型
     */
    static BlockState setElementType(BlockState state, ElementType elementType, int age) {
        return state.setValue(ELEMENT_TYPE, elementType).setValue(AGE, age);
    }

    /**
     * 检查方块是否已充能
     */
    static boolean isCharged(BlockState state) {
        return getElementType(state) != ElementType.NONE;
    }

    /**
     * 在实现类中需要重写此方法来添加状态属性
     */
    default void createElementBlockStateDefinition(StateDefinition.Builder<?, ?> builder) {
        builder.add(ELEMENT_TYPE);
    }
}