package koi.pond.disablehotbarwrap.mixin;

import net.minecraft.client.ScrollWheelHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ScrollWheelHandler.class)
public class ScrollWheelHandlerMixin {

    /**
     * Prevents hotbar from wrapping around when scrolling - also applies to bundles
     */
    @Inject(method = "getNextScrollWheelSelection", at = @At("HEAD"), cancellable = true)
    private static void getNextScrollWheelSelection(double amount, int selectedIndex, int total, CallbackInfoReturnable<Integer> cir) {
        int i = (int)Math.signum(amount);
        selectedIndex -= i;

        /*
        // Unused (?)
        for(selectedIndex = Math.max(-1, selectedIndex); selectedIndex < 0; selectedIndex += total) {
        }

        // Default scrolling behavior
        while(selectedIndex >= total) {
            selectedIndex -= total;
        }
        */

        // Prevent wrapping
        // Note that "total" is important because bundles have variable "total" values
        if (selectedIndex < 0) {
            selectedIndex = 0; // Set to the first index
        } else if (selectedIndex >= total) {
            selectedIndex = total - 1; // Set to the last index
        }

        cir.setReturnValue(selectedIndex);
        //return selectedIndex;
    }
}
