package com.tonywww.elementalemerge.registeries;

import com.tonywww.elementalemerge.ElementalEmerge;
import com.tonywww.elementalemerge.block.BlockTestChargeable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ElementalEmerge.MOD_ID);

    public static final RegistryObject<Block> TEST_CHARGEABLE = registerBlocks("test_chargeable",
            () -> new BlockTestChargeable(BlockBehaviour.Properties
                    .copy(Blocks.STONE)
                    .strength(2f, 4f)
                    .randomTicks()
            ));

    private static <T extends Block> RegistryObject<T> registerBlocks(String name, Supplier<T> block) {
        RegistryObject<T> tRegistryObject = BLOCKS.register(name, block);

        registerBlockItem(name, tRegistryObject);

        return tRegistryObject;

    }

    private static <T extends Block> void registerBlockItem(String name, Supplier<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));

    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);

    }
}
