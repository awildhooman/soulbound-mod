package com.awildhooman.soulbound.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerInventory.class)
public class SoulboundPlayerInventoryMixin {
    @WrapOperation(method = "dropAll", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"))
    public boolean checkSoulboundEnchantment(ItemStack instance, Operation<Boolean> original){
        return instance.isEmpty() || EnchantmentHelper.hasAnyEnchantmentsIn(instance, TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of("keeps_items_on_death")));
    }
}