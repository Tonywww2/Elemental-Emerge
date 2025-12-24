package com.tonywww.elementalemerge.elements;

import com.tonywww.elementalemerge.registeries.ModParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum ElementType implements StringRepresentable {
    NONE("none", ParticleTypes.ELECTRIC_SPARK),
    ELECTRIC("electric",  ModParticles.ELECTRIC_PARTICLE.get()),
    ICE("ice", ParticleTypes.SNOWFLAKE),
    FIRE("fire", ParticleTypes.FLAME);

    private final String name;
    private final ParticleOptions particleOption;

    ElementType(String name, ParticleOptions particleOption) {
        this.name = name;
        this.particleOption = particleOption;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public ParticleOptions getParticleOption() {
        return particleOption;
    }
}