package io.github.jglrxavpok.invokablemounts.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulStealingCandleItem extends Item {

    public SoulStealingCandleItem() {
        super(new Item.Properties()
                .tab(CreativeModeTab.TAB_TRANSPORTATION)
                .stacksTo(1)
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag flag) {
        super.appendHoverText(stack, level, lines, flag);
        lines.add(Component.translatable("item.invokablemounts." + ForgeRegistries.ITEMS.getKey(this).getPath() + ".hover"));
    }
}
