package io.github.jglrxavpok.invokablemounts.mixin;

import io.github.jglrxavpok.invokablemounts.items.PhantomInvocationItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    private LivingEntity entity() {
        return (LivingEntity) (Object) this;
    }

    @Inject(method = "updateFallFlying()V", at=@At("HEAD"), cancellable = true)
    public void updateFallFlyingHook(CallbackInfo ci) {
        if(entity() instanceof Player player) {
            PhantomInvocationItem.FlyBehaviourOverride flyBehaviour = PhantomInvocationItem.overrideFlyBehaviour(player);
            if(flyBehaviour != PhantomInvocationItem.FlyBehaviourOverride.DEFAULT) {
                ci.cancel();
            }
        }
    }
}
