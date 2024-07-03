package soulbound.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(PlayerInventory.class)
public class SoulboundPlayerInventoryMixin {
    @Final
    @Shadow
    private List<DefaultedList<ItemStack>> combinedInventory;
    @Final
    @Shadow
    public PlayerEntity player;
    /**
     * @author
     * Me
     * @reason
     * Keep soulbound items in inventory after death
     */
    @Overwrite
    public void dropAll() {
        for (List<ItemStack> list : this.combinedInventory) {
            for (int i = 0; i < list.size(); i++) {
                ItemStack itemStack = (ItemStack)list.get(i);
                if (!itemStack.isEmpty() && !EnchantmentHelper.hasAnyEnchantmentsIn(itemStack, TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of("keeps_items_on_death")))) {
                    this.player.dropItem(itemStack, true, false);
                    list.set(i, ItemStack.EMPTY);
                }
            }
        }
    }
}