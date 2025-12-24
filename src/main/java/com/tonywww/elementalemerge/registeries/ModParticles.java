package com.tonywww.elementalemerge.registeries;

import com.tonywww.elementalemerge.ElementalEmerge;
import com.tonywww.elementalemerge.particle.ElectricParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(Registries.PARTICLE_TYPE, ElementalEmerge.MOD_ID);

    public static final RegistryObject<SimpleParticleType> ELECTRIC_PARTICLE =
            PARTICLES.register("electric_particle", () -> new SimpleParticleType(true));

    public static void setupParticles(RegisterParticleProvidersEvent event) {
        ElementalEmerge.LOGGER.info("Begin register particles.");
        event.registerSpriteSet(ModParticles.ELECTRIC_PARTICLE.get(), ElectricParticle.Factory::new);
    }

    public static void registerCommon(IEventBus eventBus) {
        PARTICLES.register(eventBus);

    }

    public static void registerClient(IEventBus eventBus) {
        eventBus.addListener(ModParticles::setupParticles);

    }

}
