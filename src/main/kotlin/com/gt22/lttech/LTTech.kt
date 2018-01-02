package com.gt22.lttech

import com.gt22.lttech.config.LTConfig
import com.gt22.lttech.proxy.CommonProxy
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

@Mod(modid = R.MODID, name = R.NAME, version = R.VERSION, modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter", modLanguage = "kotlin", dependencies = "required-after:forgelin;")
object LTTech {

    @SidedProxy(clientSide = "com.gt22.lttech.proxy.ClientProxy", serverSide = "com.gt22.lttech.proxy.CommonProxy")
    lateinit var proxy: CommonProxy


    @Mod.EventHandler
    fun preInit(e: FMLPreInitializationEvent) {
        R.cfg = LTConfig(Configuration(e.suggestedConfigurationFile))
        R.log = e.modLog
        proxy.preInit(e)
    }

    @Mod.EventHandler
    fun init(e: FMLInitializationEvent) {
        proxy.init(e)
    }

    @Mod.EventHandler
    fun postInit(e: FMLPostInitializationEvent) {
        proxy.postInit(e)
    }

}