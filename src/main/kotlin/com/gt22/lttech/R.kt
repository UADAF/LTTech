package com.gt22.lttech

import codechicken.lib.gui.SimpleCreativeTab
import com.gt22.lttech.config.LTConfig
import com.gt22.lttech.registry.ItemRegistry
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import org.apache.logging.log4j.Logger
import java.util.*

object R {

    const val MODID = "lttech"
    const val NAME = "Lantean Technology"
    const val VERSION = "1.0"
    val RAND = Random()
    val TAB: CreativeTabs = SimpleCreativeTab(NAME) { ItemStack(ItemRegistry.naquadah) }

    lateinit var log: Logger
    lateinit var cfg: LTConfig

    fun rl(path: String) = ResourceLocation(MODID, path)

}