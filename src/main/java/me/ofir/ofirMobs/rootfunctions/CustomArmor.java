package me.ofir.ofirMobs.rootfunctions;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CustomArmor {

    public static final ArmorSet LeatherSet = new ArmorSet(
            new ItemStack(Material.LEATHER_HELMET),
            new ItemStack(Material.LEATHER_CHESTPLATE),
            new ItemStack(Material.LEATHER_LEGGINGS),
            new ItemStack(Material.LEATHER_BOOTS)
    );

    public static final ArmorSet GoldSet = new ArmorSet(
            new ItemStack(Material.GOLDEN_HELMET),
            new ItemStack(Material.GOLDEN_CHESTPLATE),
            new ItemStack(Material.GOLDEN_LEGGINGS),
            new ItemStack(Material.GOLDEN_BOOTS)
    );

    public static final ArmorSet ChainmailSet = new ArmorSet(
            new ItemStack(Material.CHAINMAIL_HELMET),
            new ItemStack(Material.CHAINMAIL_CHESTPLATE),
            new ItemStack(Material.CHAINMAIL_LEGGINGS),
            new ItemStack(Material.CHAINMAIL_BOOTS)
    );

    public static final ArmorSet IronSet = new ArmorSet(
            new ItemStack(Material.IRON_HELMET),
            new ItemStack(Material.IRON_CHESTPLATE),
            new ItemStack(Material.IRON_LEGGINGS),
            new ItemStack(Material.IRON_BOOTS)
    );

    public static final ArmorSet DiamondSet = new ArmorSet(
            new ItemStack(Material.DIAMOND_HELMET),
            new ItemStack(Material.DIAMOND_CHESTPLATE),
            new ItemStack(Material.DIAMOND_LEGGINGS),
            new ItemStack(Material.DIAMOND_BOOTS)
    );

    public static final ArmorSet NetheriteSet = new ArmorSet(
            new ItemStack(Material.NETHERITE_HELMET),
            new ItemStack(Material.NETHERITE_CHESTPLATE),
            new ItemStack(Material.NETHERITE_LEGGINGS),
            new ItemStack(Material.NETHERITE_BOOTS)
    );

    private static final Map<String, ArmorSet> armorSets = new HashMap<>();

    static {
        armorSets.put("leather", LeatherSet);
        armorSets.put("gold", GoldSet);
        armorSets.put("golden", GoldSet);
        armorSets.put("chainmail", ChainmailSet);
        armorSets.put("iron", IronSet);
        armorSets.put("diamond", DiamondSet);
        armorSets.put("netherite", NetheriteSet);
    }

    public static ArmorSet getSetByName(String name) {
        return armorSets.get(name.toLowerCase());
    }

    public static void applyArmorSet(LivingEntity entity, String setName) {
        ArmorSet armorSet = getSetByName(setName);
        if (armorSet != null && entity.getEquipment() != null) {
            entity.getEquipment().setHelmet(armorSet.helmet());
            entity.getEquipment().setChestplate(armorSet.chestplate());
            entity.getEquipment().setLeggings(armorSet.leggings());
            entity.getEquipment().setBoots(armorSet.boots());
        }
    }

    public record ArmorSet(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
    }
}
