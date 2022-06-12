package io.github.jglrxavpok.invokablemounts.mixin;

import io.github.jglrxavpok.invokablemounts.InvokableMountsMod;
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
        if(player().getInventory().countItem(InvokableMountsMod.Items.ACTIVE_PHANTOM_INVOKER.get()) > 0
        ) {
            ci.setReturnValue(true);
            ci.cancel();
        }
    }
}
