package koi.pond.disablehotbarwrap.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {

    @Shadow
    public int selectedSlot;

    /**
     * Prevents hotbar from wrapping around when scrolling
     */
    @Inject(method = "scrollInHotbar", at = @At("HEAD"), cancellable = true)
    public void onScrollInHotbar(double scrollAmount, CallbackInfo ci) {
        // Minus sign (-) scroll in default vanilla direction
        // Plus sign (+) scroll in reverse non-vanilla direction
        int newSlot = MathHelper.floor(this.selectedSlot - scrollAmount);

        // Clamp the end slots to prevent wrapping
        if (newSlot < 0) {
            newSlot = 0;
        } else if (newSlot >= 9) {
            newSlot = 8;
        }

        // Update the selected slot without wrapping
        this.selectedSlot = newSlot;

        ci.cancel();
    }

}
