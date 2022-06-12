package io.github.jglrxavpok.invokablemounts.items;

import io.github.jglrxavpok.invokablemounts.InvokableMountsMod;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SoulItem extends Item {

    private final boolean givesBackCandle;

    public SoulItem(boolean givesBackCandle) {
        super(new Item.Properties()
                .tab(CreativeModeTab.TAB_TRANSPORTATION)
                .stacksTo(1)
        );
        this.givesBackCandle = givesBackCandle;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        if(givesBackCandle) {
            return new ItemStack(InvokableMountsMod.Items.OTHERWORLDLY_SOUL_STEALING_CANDLE.get());
        } else {
            return null;
        }
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return givesBackCandle;
    }
}
