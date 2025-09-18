package com.tonywww.elementalemerge.event;

import com.tonywww.elementalemerge.ElementalEmerge;
import com.tonywww.elementalemerge.elements.BasicElement;
import com.tonywww.elementalemerge.persist.AllElementsWorldStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.HashMap;

import static org.cyclops.cyclopscore.helper.DirectionHelpers.DIRECTIONS;

public class TickHandler {

    private static TickHandler INSTANCE;

    private int tick = 1;

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
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.side != LogicalSide.SERVER || event.phase != TickEvent.Phase.END) return;

        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) return;

        // 为每个维度分别处理
        for (ServerLevel serverLevel : server.getAllLevels()) {
            processElementsInLevel(serverLevel);
        }
        this.tick++;

    }

    private void processElementsInLevel(ServerLevel level) {
        int operationCount = 0;
        HashMap<BlockPos, BasicElement> entityCheckPositions = new HashMap<>();
        AllElementsWorldStorage storage = AllElementsWorldStorage.getInstance(ElementalEmerge._instance);

        storage.resetPositions(level, tick - 1);

        while (operationCount < MAX_OPERATION_PER_TICK) {
            BasicElement currentElement = storage.popElementFromProcessingStack(level);
            if (currentElement == null) break;

            // 标记为已访问
            storage.markAsVisited(level, currentElement.pos);

            // 执行元素效果
            currentElement.doBlockEffects();
            entityCheckPositions.put(currentElement.pos, currentElement);

            // 向周围扩散
            if (currentElement.power > 0) {
                spreadToNeighbors(currentElement, storage, level);
            }

            operationCount++;
        }

        // 处理当前维度的实体
        level.getEntities().getAll().forEach(entity -> {
            BlockPos entityPos = entity.blockPosition();
            if (entityCheckPositions.containsKey(entityPos)) {
                entityCheckPositions.get(entityPos).doEntityEffects(entity);
            }
        });

        entityCheckPositions.clear();
    }

    private void spreadToNeighbors(BasicElement element, AllElementsWorldStorage storage, ServerLevel level) {
        for (Direction direction : DIRECTIONS) {
            BlockPos neighborPos = element.pos.relative(direction);

            if (!storage.isVisited(level, neighborPos) && storage.getElementAt(level, neighborPos) == null) {
                spreadElementToPosition(element, neighborPos, storage, level);
            }
        }
    }

    private void spreadElementToPosition(BasicElement sourceElement, BlockPos targetPos, AllElementsWorldStorage storage, ServerLevel level) {
        storage.addElement(level, sourceElement.getSpreadElement(targetPos), tick);
    }

    public int getTick() {
        return tick;
    }

}
