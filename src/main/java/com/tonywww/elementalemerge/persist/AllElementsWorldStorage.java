package com.tonywww.elementalemerge.persist;

import com.tonywww.elementalemerge.elements.BasicElement;
import net.minecraft.core.BlockPos;
import org.cyclops.cyclopscore.init.ModBase;
import org.cyclops.cyclopscore.persist.nbt.NBTPersist;
import org.cyclops.cyclopscore.persist.world.WorldStorage;

import java.util.*;

public class AllElementsWorldStorage extends WorldStorage {

    private static AllElementsWorldStorage INSTANCE = null;

    private static final int RESET_TICKS = 40;
    private static final int RESET_POS_INIT_CAP = 64;

    @NBTPersist
    private Map<BlockPos, BasicElement> allElements = new HashMap<>();

    // 待处理的元素
    @NBTPersist
    private Stack<BasicElement> processingStack = new Stack<>();

    // 已访问的位置
    @NBTPersist
    private Set<BlockPos> visitedPositions = new HashSet<>();

    // 将在RESET_TICKS后重置的位置
    @NBTPersist
    private List<BlockPos>[] toResetPositions = new ArrayList[RESET_TICKS + 1];
    {
        for (int i = 0; i <= RESET_TICKS; i++) {
            toResetPositions[i] = new ArrayList<>(RESET_POS_INIT_CAP);
        }
    }


    public AllElementsWorldStorage(ModBase mod) {
        super(mod);
    }

    public static AllElementsWorldStorage getInstance(ModBase mod) {
        if(INSTANCE == null) {
            INSTANCE = new AllElementsWorldStorage(mod);
        }
        return INSTANCE;
    }

    public synchronized void addElement(BasicElement element, int tick) {
        this.addElementToMap(element);
        this.pushElementToProcessingStack(element);
        this.getToResetPositions(tick).add(element.pos);
    }

    public synchronized void resetPositions(int tick) {
        List<BlockPos> positions = this.getToResetPositions(tick);
        for (BlockPos pos : positions) {
            this.markAsNotVisited(pos);
        }
        this.resetToResetPositions(tick);
    }


    public synchronized boolean isVisited(BlockPos pos) {
        return visitedPositions.contains(pos);
    }

    public synchronized BasicElement getElementAt(BlockPos pos) {
        return allElements.get(pos);
    }

    public synchronized BasicElement popElementFromProcessingStack() {
        if (hasElementsToProcess()) {
            return this.processingStack.pop();
        }
        return null;
    }

    public synchronized void markAsVisited(BlockPos pos) {
        visitedPositions.add(pos);
    }

    private synchronized BasicElement removeElementFromMap(BlockPos pos) {
        return this.allElements.remove(pos);
    }

    private synchronized List<BlockPos> getToResetPositions(int tick) {
        return this.toResetPositions[tick % (RESET_TICKS + 1)];
    }

    private synchronized void resetToResetPositions(int tick) {
        this.toResetPositions[tick % (RESET_TICKS + 1)] = new ArrayList<>(RESET_POS_INIT_CAP);
    }

    private synchronized void addElementToMap(BasicElement element) {
        this.allElements.put(element.pos, element);
    }

    private synchronized void pushElementToProcessingStack(BasicElement element) {
        this.processingStack.push(element);
    }

    public synchronized boolean hasElementsToProcess() {
        return !processingStack.isEmpty();
    }

    private synchronized void markAsNotVisited(BlockPos pos) {
        visitedPositions.remove(pos);
    }


    @Override
    public void reset() {
        this.allElements.clear();
        this.processingStack.clear();
        this.visitedPositions.clear();
    }

    @Override
    protected String getDataId() {
        return "AllElements";
    }
}
