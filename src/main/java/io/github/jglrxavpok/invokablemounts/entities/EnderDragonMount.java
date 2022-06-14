package io.github.jglrxavpok.invokablemounts.entities;

import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import io.github.jglrxavpok.invokablemounts.Config;
import io.github.jglrxavpok.invokablemounts.InvokableMountsMod;
import io.github.jglrxavpok.invokablemounts.entities.dragon.DragonRidingPhase;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnderDragonMount extends EnderDragon implements MountEntity {

    public static final String ID = "ender_dragon_mount";
    public static final EnderDragonPhase<DragonRidingPhase> RIDING_PHASE = EnderDragonPhase.create(DragonRidingPhase.class, InvokableMountsMod.MODID+":DragonRidingPhase");
    private static final EntityDataAccessor<Integer> REMAINING_TICKS = SynchedEntityData.defineId(EnderDragonMount.class, EntityDataSerializers.INT);

    public EnderDragonMount(EntityType<? extends EnderDragonMount> entityType, Level level) {
        super(entityType, level);
        getPhaseManager().setPhase(RIDING_PHASE);

        setHealth((float)(double)Config.INSTANCE.ENDER_DRAGON_INVOCATION_HEALTH.get());
    }

    @Override
    public boolean rideableUnderWater() {
        return true;
    }

    @Override
    public EntityType<?> getType() {
        // because the EnderDragon class does not respect the given entity type in its constructor
        return InvokableMountsMod.Entities.ENDER_DRAGON_MOUNT.get();
    }

    public double getPassengersRidingOffset() {
        return 0.0;
    }

    @Override
    public boolean shouldRiderFaceForward(Player player) {
        return true;
    }

    @Override
    public void positionRider(Entity rider) {
        // from EnderDragonRenderer, ensures player appears to stay on the dragon, even while it is turning

        // body Z rotation
        final float zRot = Mth.rotWrap(getLatencyPos(5, 1.0f)[0] - getLatencyPos(10, 1.0f)[0]) * 1.5f;

        Matrix4f stack = new Matrix4f();
        stack.setIdentity();
        float f = (float)getLatencyPos(7, 1.0f)[0];
        float f1 = (float)(getLatencyPos(5, 1.0f)[1] - getLatencyPos(10, 1.0f)[1]) + getXRot();
        stack.multiply(Vector3f.YP.rotationDegrees(-f));
        stack.multiply(Vector3f.XP.rotationDegrees(f1 * 10.0F));
        stack.multiply(Matrix4f.createTranslateMatrix(0.0f, 0.0f, 1.0f));
        stack.multiply(Matrix4f.createScaleMatrix(1.0F, -1.0F, 1.0F));
        stack.multiply(Matrix4f.createTranslateMatrix(0.0f, -1.501f, 0.0f));

        f = this.flapTime;
        f1 = (float)(Math.sin((double)(f * ((float)Math.PI * 2F) - 1.0F)) + 1.0D);
        f1 = (f1 * f1 + f1 * 2.0F) * 0.05f;

        stack.multiply(Matrix4f.createTranslateMatrix(0.0f, f1 - 2.0F, -3.0f));
        stack.multiply(Vector3f.XP.rotationDegrees(f1 * 2.0F));

        stack.multiply(Matrix4f.createTranslateMatrix(0.0f, 1.0f, 0.0f));
        stack.multiply(Vector3f.ZP.rotationDegrees(zRot));
        stack.multiply(Matrix4f.createTranslateMatrix(0.0f, -1.0f, 0.0f));

        final float offset = -0.4f;
        Vector4f v = new Vector4f(0, 0, offset, 1);
        v.transform(stack);
        if(v.w() > 10e-16) {
            v.perspectiveDivide();
        } else {
            v.set(0,0,0,0);
        }

        final double x = v.x();
        final double y = v.y() - 0.5 + rider.getMyRidingOffset() + getPassengersRidingOffset();
        final double z = v.z();

        rider.setPos(this.getX() + x, getY() + y, this.getZ() + z);
    }

    @Nullable
    @Override
    public Entity getControllingPassenger() {
        return null;
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

    @Override
    public void onDespawnParticleSpawn() {
        for (int i = 0; i < 20; i++) {
            spawnSoulSpeedParticle();
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return EnderDragon.createAttributes().add(Attributes.MAX_HEALTH, Config.INSTANCE.ENDER_DRAGON_INVOCATION_HEALTH.get());
    }

    @Override
    public EntityDataAccessor<Integer> getRemainingTicksAccessor() {
        return REMAINING_TICKS;
    }

    @Override
    public void knockBack(List<Entity> p_31132_) {
        /* no op */
    }

    @Override
    protected void hurt(List<Entity> p_31142_) {
        /* no op */
    }

    @Override
    public void aiStep() {
        dragonFight = null;
        super.aiStep();
    }
}
