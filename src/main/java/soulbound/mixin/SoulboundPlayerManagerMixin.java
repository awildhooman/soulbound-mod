package soulbound.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerManager.class)
public class SoulboundPlayerManagerMixin {
    private PlayerInventory savedInventory;

    @Inject(method = "respawnPlayer", at = @At("HEAD"))
    private void saveInventory(ServerPlayerEntity player, boolean alive, Entity.RemovalReason removalReason, CallbackInfoReturnable<ServerPlayerEntity> cir) {
        savedInventory = player.getInventory();
    }
    @ModifyVariable(method = "respawnPlayer", at = @At("TAIL"), ordinal = 1)
    private ServerPlayerEntity setInventory(ServerPlayerEntity value){
        value.getInventory().clone(savedInventory);
        return value;
    }
}