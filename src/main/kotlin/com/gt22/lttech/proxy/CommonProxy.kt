package com.gt22.lttech.proxy

import com.gt22.lttech.LTTech
import com.gt22.lttech.gui.GuiHandler
import com.gt22.lttech.network.NetworkHandler
import com.gt22.lttech.registry.BlockRegistry
import com.gt22.lttech.registry.ItemRegistry
import com.gt22.lttech.registry.TileRegistry
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import ru.pearx.libmc.client.models.IModelProvider

open class CommonProxy {

    open fun preInit(e: FMLPreInitializationEvent) {
        BlockRegistry.reg()
        ItemRegistry.reg()
    }

    open fun init(e: FMLInitializationEvent) {
        TileRegistry.reg()
        NetworkRegistry.INSTANCE.registerGuiHandler(LTTech, GuiHandler)
        NetworkHandler.reg()
    }

    open fun postInit(e: FMLPostInitializationEvent) {

    }

    open fun setupModel(m: IModelProvider) {}

}