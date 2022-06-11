package io.github.jglrxavpok.invokablemounts.entities.dragon;

import io.github.jglrxavpok.invokablemounts.entities.EnderDragonMount;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Allows to override the Dragon AI to let the rider control its movements.
 */
public class DragonRidingPhase extends AbstractDragonPhaseInstance {
    private final EnderDragonMount mount;

    public DragonRidingPhase(EnderDragon dragon) {
        super(dragon);
        if(!(dragon instanceof EnderDragonMount)) {
            throw new IllegalArgumentException("This phase is only compatible with the Ender Dragon Invocation mount.");
        }
        this.mount = (EnderDragonMount)dragon;
    }

    @Override
    public boolean isSitting() {
        return false;
    }

    @Override
    public void doClientTick() {

    }

    @Override
    public void doServerTick() {

    }

    @Override
    public void onCrystalDestroyed(EndCrystal p_31315_, BlockPos p_31316_, DamageSource p_31317_, @Nullable Player p_31318_) {

    }

    @Override
    public void begin() {

    }

    @Override
    public void end() {

    }

    @Override
    public float getFlySpeed() {
        return 20.5f;
    }

    public float getTurnSpeed() {
        return super.getTurnSpeed();
    }

    @Override
    public EnderDragonPhase<? extends DragonPhaseInstance> getPhase() {
        return EnderDragonMount.RIDING_PHASE;
    }

    @Nullable
    @Override
    public Vec3 getFlyTargetLocation() {
        Entity controllingEntity = mount.getFirstPassenger();
        if(controllingEntity instanceof LivingEntity rider) {
            double speedMultiplier = 20.0;

            // TODO: add a tiny bit of inertia, stopping immediately looks weird
            Vec3 lookDir = rider.getViewVector(1.0f);
            if(lookDir.lengthSqr() < 10e-16) {
                return null;
            }

            lookDir = new Vec3(lookDir.x, lookDir.y * 5, lookDir.z); // x5 on Y to boost vertical angle

            Vec3 forward = lookDir.scale(rider.zza);
            Vec3 strafe = lookDir.cross(rider.getUpVector(1.0f)).scale(-rider.xxa);
            Vec3 wantedDirection = forward.add(strafe);
            if(wantedDirection.lengthSqr() < 10e-6) {
                return null;
            }

            wantedDirection = wantedDirection.normalize();

            return mount.getPosition(1.0f).add(wantedDirection.x * speedMultiplier, wantedDirection.y * speedMultiplier, wantedDirection.z * speedMultiplier);
        }
        return null;
    }

    @Override
    public float onHurt(DamageSource p_31313_, float p_31314_) {
        return 0;
    }
}
