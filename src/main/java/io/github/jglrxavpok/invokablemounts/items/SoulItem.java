package io.github.jglrxavpok.invokablemounts.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class SoulItem extends Item {
    public SoulItem() {
        super(new Item.Properties()
                .tab(CreativeModeTab.TAB_TRANSPORTATION)
                .stacksTo(1)
        );
    }
}
