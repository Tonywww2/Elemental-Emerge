package com.tonywww.elementalemerge.item;

import com.tonywww.elementalemerge.elements.ElectricElement;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class ItemTestElectric extends Item {
    public ItemTestElectric(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        System.out.println("LOG TEST");
        if (context.getLevel() instanceof ServerLevel level) {
            System.out.println("LOG TEST SERVER");
            ElectricElement element = new ElectricElement(context.getClickedPos(), (byte) context.getItemInHand().getCount(), level);
            element.castEvent();

        }
        return super.useOn(context);
    }
}
