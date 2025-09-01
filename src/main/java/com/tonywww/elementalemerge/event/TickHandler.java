package com.tonywww.elementalemerge.event;

import com.tonywww.elementalemerge.ElementalEmerge;
import com.tonywww.elementalemerge.elements.BasicElement;
import com.tonywww.elementalemerge.persist.AllElementsWorldStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static org.cyclops.cyclopscore.helper.DirectionHelpers.DIRECTIONS;

public class TickHandler {

    private static TickHandler INSTANCE;
    private int tick = 1;
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

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if (event.type != TickEvent.Type.SERVER || event.phase != TickEvent.Phase.END) return;
        int operationCount = 0;
        AllElementsWorldStorage storage = AllElementsWorldStorage.getInstance(ElementalEmerge._instance);

        storage.resetPositions(tick - 1);

        while (operationCount < MAX_OPERATION_PER_TICK) {
            BasicElement currentElement = storage.popElementFromProcessingStack();
            if (currentElement == null) break;

            // 标记为已访问
            storage.markAsVisited(currentElement.pos);

            // 执行元素效果
            currentElement.doBlockEffects();
            currentElement.doEntityEffects();

            // 如果还有扩散能力，向周围扩散
            if (currentElement.power > 0) {
                spreadToNeighbors(currentElement, storage);
            }

            operationCount++;
        }

//        System.out.println("Processed " + operationCount + " operations this tick.");

        this.tick++;

    }

    private void spreadToNeighbors(BasicElement element, AllElementsWorldStorage storage) {
        for (Direction direction : DIRECTIONS) {
            BlockPos neighborPos = element.pos.relative(direction);

            if (!storage.isVisited(neighborPos) && storage.getElementAt(neighborPos) == null) {
                // 创建新的扩散元素（power减1）
                spreadElementToPosition(element, neighborPos, storage);
            }
        }
    }

    private void spreadElementToPosition(BasicElement sourceElement, BlockPos targetPos, AllElementsWorldStorage storage) {
        storage.addElement(sourceElement.getSpreadElement(targetPos), tick);

    }
}
