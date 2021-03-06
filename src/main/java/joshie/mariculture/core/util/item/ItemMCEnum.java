package joshie.mariculture.core.util.item;

import joshie.mariculture.core.helpers.StringHelper;
import joshie.mariculture.core.util.MCTab;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static joshie.mariculture.core.lib.MaricultureInfo.MODID;

public class ItemMCEnum<A extends ItemMCEnum, E extends Enum<E>> extends Item implements MCItem<A> {
    protected final E[] values;
    public ItemMCEnum(Class<E> clazz) {
        this(MCTab.getCore(), clazz);
    }

    public ItemMCEnum(CreativeTabs tab, Class<E> clazz) {
        values = clazz.getEnumConstants();
        setCreativeTab(tab);
        setHasSubtypes(true);
    }

    @Override
    public A register(String name) {
        MCItem.super.register(name);
        return (A) this;
    }

    public ItemStack getStackFromEnum(E e) {
        return new ItemStack(this, 1, e.ordinal());
    }

    public E getEnumFromStack(ItemStack stack) {
        return values[Math.max(0, Math.min(values.length, stack.getItemDamage()))];
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return StringHelper.localize(getUnlocalizedName(stack));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return MODID + "." + getUnlocalizedName() + "." + getEnumFromStack(stack).name().toLowerCase();
    }

    @Override
    public int getSortValue(ItemStack stack) {
        return 500;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
        for (E e: values) {
            list.add(new ItemStack(item, 1, e.ordinal()));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerModels(Item item) {
        for (E e: values) {
            ModelLoader.setCustomModelResourceLocation(item, e.ordinal(), new ModelResourceLocation(getRegistryName(), e.name().toLowerCase()));
        }
    }
}