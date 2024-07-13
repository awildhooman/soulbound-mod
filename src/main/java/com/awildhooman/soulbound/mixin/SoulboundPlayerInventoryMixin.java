package com.awildhooman.soulbound.mixin;

import com.awildhooman.soulbound.SoulboundConfig;
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
    public boolean checkSoulboundEnchantment(ItemStack stack, Operation<Boolean> original){
        if (EnchantmentHelper.hasAnyEnchantmentsIn(stack, TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of("keeps_items_on_death")))) {
            SoulboundConfig.Configs configs = SoulboundConfig.readJson();
            if (stack.isDamageable() && configs.damageSoulboundItems && configs.maximumDamage >= configs.minimumDamage) {
                configs.minimumDamage = Math.max(0, configs.minimumDamage);
                configs.maximumDamage = Math.min(1, configs.maximumDamage);
                int durability = stack.getMaxDamage() - stack.getDamage();
                int damage = (int) ((Math.random() * (configs.maximumDamage - configs.minimumDamage) + configs.minimumDamage) * durability);
                stack.setDamage(stack.getDamage() + damage);
            }
            return true;
        }
        return stack.isEmpty();
    }
}