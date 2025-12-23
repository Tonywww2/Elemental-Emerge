package com.tonywww.elementalemerge.registeries;

import com.tonywww.elementalemerge.ElementalEmerge;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ElementalEmerge.MOD_ID);

    public static final RegistryObject<SimpleParticleType> ELECTRIC_PARTICLE =
            PARTICLES.register("electric_particle", () -> new SimpleParticleType(true));


    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }
}
