package me.fenix.mixins;

import me.fenix.EventClaim;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Shadow
    private static Minecraft theMinecraft;

    @Inject(method = "startGame", at = @At("HEAD"))
    private void startGame(CallbackInfo callbackInfo) {
        EventClaim instance = EventClaim.getInstance();
        instance.setMinecraft(theMinecraft);
    }

    @Inject(method = "shutdownMinecraftApplet", at = @At("HEAD"))
    private void shutdownMinecraftApplet(CallbackInfo callbackInfo) {
        EventClaim.getInstance().shutdownExecutor();
    }
}
