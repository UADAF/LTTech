package com.gt22.lttech.registry

import com.gt22.lttech.LTTech
import com.gt22.lttech.blocks.NaquadahReactor
import com.gt22.lttech.blocks.TransferRings
import net.minecraft.block.Block
import net.minecraft.item.ItemBlock
import net.minecraftforge.fml.common.registry.GameRegistry
import ru.pearx.libmc.client.models.IModelProvider

object BlockRegistry {

    lateinit var nqreactor: NaquadahReactor
    lateinit var transferRings: TransferRings

    fun reg() {
        val r = GameRegistry.findRegistry(Block::class.java)
        BlockRegistry::class.java.declaredFields.filter { field -> Block::class.java.isAssignableFrom(field.type) && !ItemBlock::class.java.isAssignableFrom(field.type) }.forEach { field ->
            val i: Block = field.type.newInstance() as Block
            r.register(i)
            field.set(this, i)
            if(i is IModelProvider) LTTech.proxy.setupModel(i)
        }
    }

}