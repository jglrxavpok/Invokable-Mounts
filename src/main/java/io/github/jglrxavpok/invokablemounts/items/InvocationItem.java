package io.github.jglrxavpok.invokablemounts.items;

import io.github.jglrxavpok.invokablemounts.InvokableMountsMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class InvocationItem<T extends Entity> extends Item {

    private final boolean isActive;
    private final String baseID;

    public InvocationItem(boolean active, String baseID) {
        super(new Properties()
                .defaultDurability(100)
                .tab(!active ? CreativeModeTab.TAB_TRANSPORTATION : null)
        );
        isActive = active;
        this.baseID = baseID;
    }

    private ResourceLocation makeOppositeID() {
        return new ResourceLocation(InvokableMountsMod.MODID,
                (!isActive ? "active_" : "inactive_") + baseID);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag flag) {
        super.appendHoverText(stack, level, lines, flag);
        lines.add(Component.translatable("item.invokablemounts."+baseID+".hover"));
    }

    private ItemStack toggle(ItemStack currentStack) {
        ItemStack changedStack = new ItemStack(ForgeRegistries.ITEMS.getValue(makeOppositeID()));
        changedStack.setDamageValue(currentStack.getDamageValue());

        if(currentStack.hasCustomHoverName()) {
            changedStack.setHoverName(currentStack.getHoverName());
        }

        if(currentStack.hasTag()) {
            changedStack.setTag(currentStack.getTag());
        }

        return changedStack;
    }

    /**
     * Generate the entity, ready to be ridden by the player (will be teleported by caller)
     */
    public abstract T generateEntity(Level level);

    /**
     * Is the given player currently riding the entity corresponding to this item?
     */
    public abstract boolean isRidingCorrespondingEntity(Player player);

    public InteractionResultHolder<ItemStack> handleUseWhileRiding(Level level, Player player, InteractionHand hand) {
        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);

        if(entity instanceof Player player) {
            if(isActive) {
                if(!player.isPassenger() || !isRidingCorrespondingEntity(player)) {
                    player.getInventory().setItem(slot, toggle(stack));
                } else if(stack.getDamageValue() < stack.getMaxDamage()) {
                    // TODO: config for speed
                    // TODO: random to amortize over multiple ticks
                    if(!player.isCreative()) {
                        stack.setDamageValue(stack.getDamageValue() + 1);
                    }
                } else {
                    player.unRide();
                    player.getInventory().setItem(slot, toggle(stack));
                }
            } else {
                // TODO: config for speed
                // TODO: random to amortize over multiple ticks
                if(stack.getDamageValue() > 0) {
                    stack.setDamageValue(stack.getDamageValue() - 1);
                }
            }
        }

    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if(player.isPassenger()) {
            return handleUseWhileRiding(level, player, hand);
        }

        if(!level.isClientSide) {
            T mount = generateEntity(level);
            if(itemstack.hasCustomHoverName()) {
                mount.setCustomName(itemstack.getHoverName());
                mount.setCustomNameVisible(true);
            }
            mount.setPos(player.getPosition(1.0f));
            player.startRiding(mount, true);

            // TODO: mount.playSound();
            level.addFreshEntity(mount);
            return InteractionResultHolder.success(toggle(itemstack));
        }

        return InteractionResultHolder.success(itemstack);
    }
}
