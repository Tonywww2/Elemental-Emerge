package com.tonywww.elementalemerge.registeries;

import com.tonywww.elementalemerge.ElementalEmerge;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ElementalEmerge.MOD_ID);

    public static RegistryObject<CreativeModeTab> ELEMENTAL_EMERGE_TAB = CREATIVE_MODE_TABS.register("elemental_emerge_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.TEST_ELECTRIC.get()))
                    .title(Component.translatable("creativetab.elemental_emerge_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.TEST_ELECTRIC.get());

                        pOutput.accept(ModBlocks.TEST_CHARGEABLE.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
