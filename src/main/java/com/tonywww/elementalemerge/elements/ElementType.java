package com.tonywww.elementalemerge.elements;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum ElementType implements StringRepresentable {
    NONE("none"),
    ELECTRIC("electric"),
    ICE("ice"),
    FIRE("fire");

    private final String name;

    ElementType(String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}