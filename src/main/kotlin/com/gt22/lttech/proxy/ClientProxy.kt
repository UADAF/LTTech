package com.gt22.lttech.proxy

import com.gt22.lttech.R
import com.gt22.lttech.events.ModelEvents
import net.minecraftforge.client.model.obj.OBJLoader
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import ru.pearx.libmc.client.models.IModelProvider

class ClientProxy : CommonProxy() {

    override fun preInit(e: FMLPreInitializationEvent) {
        super.preInit(e)
        OBJLoader.INSTANCE.addDomain(R.MODID)
        MinecraftForge.EVENT_BUS.register(ModelEvents)
    }

    override fun init(e: FMLInitializationEvent) {
        super.init(e)
    }

    override fun postInit(e: FMLPostInitializationEvent) {
        super.postInit(e)
    }

    override fun setupModel(m: IModelProvider) {
        m.setupModels()
    }
}