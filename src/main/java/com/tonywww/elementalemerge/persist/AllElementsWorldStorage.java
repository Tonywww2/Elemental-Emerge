package com.tonywww.elementalemerge.persist;

import com.tonywww.elementalemerge.elements.BasicElement;
import com.tonywww.elementalemerge.event.TickHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.cyclops.cyclopscore.init.ModBase;
import org.cyclops.cyclopscore.persist.nbt.NBTPersist;
import org.cyclops.cyclopscore.persist.world.WorldStorage;

import java.util.*;

public class AllElementsWorldStorage extends WorldStorage {

    private static AllElementsWorldStorage INSTANCE = null;

    private static final int RESET_TICKS = 40;
    private static final int RESET_POS_INIT_CAP = 256;

    // 按维度分组的所有元素
    @NBTPersist
    private Map<ResourceKey<Level>, Map<BlockPos, BasicElement>> allElements = new HashMap<>();

    // 按维度分组的待处理元素
    @NBTPersist
    private Map<ResourceKey<Level>, Stack<BasicElement>> processingStacks = new HashMap<>();

    // 按维度分组的已访问位置
    @NBTPersist
    private Map<ResourceKey<Level>, Set<BlockPos>> visitedPositions = new HashMap<>();

    // 按维度分组的待重置位置
    @NBTPersist
    private Map<ResourceKey<Level>, List<BlockPos>[]> toResetPositions = new HashMap<>();

    public AllElementsWorldStorage(ModBase mod) {
        super(mod);
    }

    public static AllElementsWorldStorage getInstance(ModBase mod) {
        if(INSTANCE == null) {
            INSTANCE = new AllElementsWorldStorage(mod);
        }
        return INSTANCE;
    }

    private void initializeDimension(ResourceKey<Level> dimension) {
        if (!allElements.containsKey(dimension)) {
            allElements.put(dimension, new HashMap<>());
            processingStacks.put(dimension, new Stack<>());
            visitedPositions.put(dimension, new HashSet<>());

            List<BlockPos>[] resetArray = new ArrayList[RESET_TICKS + 1];
            for (int i = 0; i <= RESET_TICKS; i++) {
                resetArray[i] = new ArrayList<>(RESET_POS_INIT_CAP);
            }
            toResetPositions.put(dimension, resetArray);
        }
    }

    public synchronized void addElement(ServerLevel level, BasicElement element) {
        addElement(level, element, TickHandler.getInstance().getTick());
    }

    public synchronized void addElement(ServerLevel level, BasicElement element, int tick) {
        ResourceKey<Level> dimension = level.dimension();
        initializeDimension(dimension);

        this.addElementToMap(dimension, element);
        this.pushElementToProcessingStack(dimension, element);
        this.getToResetPositions(dimension, tick).add(element.pos);
    }

    public synchronized void resetPositions(ServerLevel level, int tick) {
        ResourceKey<Level> dimension = level.dimension();
        initializeDimension(dimension);

        List<BlockPos> positions = this.getToResetPositions(dimension, tick);
        for (BlockPos pos : positions) {
            this.markAsNotVisited(dimension, pos);
        }
        this.resetToResetPositions(dimension, tick);
    }

    public synchronized boolean isVisited(ServerLevel level, BlockPos pos) {
        ResourceKey<Level> dimension = level.dimension();
        initializeDimension(dimension);
        return visitedPositions.get(dimension).contains(pos);
    }

    public synchronized BasicElement getElementAt(ServerLevel level, BlockPos pos) {
        ResourceKey<Level> dimension = level.dimension();
        initializeDimension(dimension);
        return allElements.get(dimension).get(pos);
    }

    public synchronized BasicElement popElementFromProcessingStack(ServerLevel level) {
        ResourceKey<Level> dimension = level.dimension();
        initializeDimension(dimension);

        Stack<BasicElement> stack = processingStacks.get(dimension);
        if (!stack.isEmpty()) {
            return stack.pop();
        }
        return null;
    }

    public synchronized void markAsVisited(ServerLevel level, BlockPos pos) {
        ResourceKey<Level> dimension = level.dimension();
        initializeDimension(dimension);
        visitedPositions.get(dimension).add(pos);
    }

    private synchronized List<BlockPos> getToResetPositions(ResourceKey<Level> dimension, int tick) {
        return this.toResetPositions.get(dimension)[tick % (RESET_TICKS + 1)];
    }

    private synchronized void resetToResetPositions(ResourceKey<Level> dimension, int tick) {
        this.toResetPositions.get(dimension)[tick % (RESET_TICKS + 1)] = new ArrayList<>(RESET_POS_INIT_CAP);
    }

    private synchronized void addElementToMap(ResourceKey<Level> dimension, BasicElement element) {
        this.allElements.get(dimension).put(element.pos, element);
    }

    private synchronized void pushElementToProcessingStack(ResourceKey<Level> dimension, BasicElement element) {
        this.processingStacks.get(dimension).push(element);
    }

    private synchronized void markAsNotVisited(ResourceKey<Level> dimension, BlockPos pos) {
        visitedPositions.get(dimension).remove(pos);
        allElements.get(dimension).remove(pos);
    }

    @Override
    public void reset() {
        this.allElements.clear();
        this.processingStacks.clear();
        this.visitedPositions.clear();
        this.toResetPositions.clear();
    }

    @Override
    protected String getDataId() {
        return "AllElements";
    }
}