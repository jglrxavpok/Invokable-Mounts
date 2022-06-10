package io.github.jglrxavpok.invokablemounts.entities;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class HorseMount extends Horse implements MountEntity {

    private static final EntityDataAccessor<Integer> REMAINING_TICKS = SynchedEntityData.defineId(HorseMount.class, EntityDataSerializers.INT);

    public HorseMount(EntityType<? extends HorseMount> entityType, Level level) {
        super(entityType, level);

        this.inventory.setItem(0, new ItemStack(Items.SADDLE));
        setTamed(true);

        // TODO: config for health on spawn
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        defineSynchedData_Mount();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.removeAllGoals();
    }

    @Override
    public void tick() {
        super.tick();

        tick_Mount();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AbstractHorse.createBaseHorseAttributes();
    }

    @Override
    public EntityDataAccessor<Integer> getRemainingTicksAccessor() {
        return REMAINING_TICKS;
    }
}
