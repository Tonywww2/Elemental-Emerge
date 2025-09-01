package com.tonywww.elementalemerge.registeries;

import com.tonywww.elementalemerge.ElementalEmerge;
import com.tonywww.elementalemerge.item.ItemTestElectric;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ElementalEmerge.MOD_ID);

    public static final RegistryObject<Item> TEST_ELECTRIC = ITEMS.register("test_electric",
            () -> new ItemTestElectric(new Item.Properties()
                    .stacksTo(5)
            ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);

    }
}
