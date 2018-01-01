package com.gt22.lttech.registry

import com.gt22.lttech.LTTech
import com.gt22.lttech.R
import com.gt22.lttech.items.ItemNaquadah
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.registries.IForgeRegistry
import ru.pearx.libmc.client.models.IModelProvider
import ru.pearx.libmc.common.items.ItemBlockBase
import java.lang.reflect.Field

object ItemRegistry {

    var naquadah: ItemNaquadah? = null
    var nqreactor: ItemBlockBase? = null
    fun reg() {
        val r = GameRegistry.findRegistry(Item::class.java)
        ItemRegistry::class.java.declaredFields.filter { field -> Item::class.java.isAssignableFrom(field.type) }.forEach { field ->
            if (ItemBlock::class.java.isAssignableFrom(field.type)) {
                regItemBlock(field, r)
            } else {
                regItem(field, r)
            }
        }
    }

    private fun regItem(field: Field, r: IForgeRegistry<Item>) {
        val i: Item = field.type.newInstance() as Item
        r.register(i)
        field.set(this, i)
        if (i is IModelProvider) LTTech.proxy.setupModel(i)
    }

    private fun regItemBlock(field: Field, r: IForgeRegistry<Item>) {
        try {
            val block: Field = BlockRegistry::class.java.getDeclaredField(field.name)
            val ibc: Class<out ItemBlock> = field.type.asSubclass(ItemBlock::class.java)
            block.isAccessible = true
            val ib: ItemBlock = ibc.getDeclaredConstructor(Block::class.java).newInstance(block.get(BlockRegistry) as Block)
            r.register(ib)
            field.set(this, ib)
            if (ib is IModelProvider) LTTech.proxy.setupModel(ib)
        } catch (e: NoSuchFieldException) {
            R.log?.warn("Block ${field.name} not found! Unable to register ItemBlock")
        }

    }

}