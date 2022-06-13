package io.github.jglrxavpok.invokablemounts.mixin;

import io.github.jglrxavpok.invokablemounts.items.PhantomInvocationItem;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {

    private Player player() {
        return (Player) (Object) this;
    }

    @Inject(method = "tryToStartFallFlying()Z", at=@At("HEAD"), require = 1, cancellable = true)
    public void tryToStartFallFlyingHook(CallbackInfoReturnable<Boolean> ci) {
        PhantomInvocationItem.FlyBehaviourOverride flyBehaviour = PhantomInvocationItem.overrideFlyBehaviour(player());
        if(flyBehaviour != PhantomInvocationItem.FlyBehaviourOverride.DEFAULT) {
            ci.setReturnValue(flyBehaviour == PhantomInvocationItem.FlyBehaviourOverride.ALLOW_FLIGHT);
            ci.cancel();
        }
    }
}
