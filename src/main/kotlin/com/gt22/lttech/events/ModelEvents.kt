package com.gt22.lttech.events

import com.gt22.lttech.R
import com.gt22.lttech.models.NQReactorModel
import com.gt22.lttech.registry.BlockRegistry
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ModelBakeEvent
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import ru.pearx.libmc.client.models.IPXModel

@SideOnly(Side.CLIENT)
object ModelEvents {

    @SubscribeEvent
    fun onModelBake(e: ModelBakeEvent) {
        putModel(e, NQReactorModel(), BlockRegistry.nqreactor!!.registryName!!)
    }

    fun putModel(e: ModelBakeEvent, model: IPXModel, loc: ResourceLocation) {
        model.bake()
        e.modelRegistry.putObject(ModelResourceLocation(loc, "normal"), model)
    }

    @SubscribeEvent
    fun stitch(e: TextureStitchEvent.Pre) {
        e.map.registerSprite(R.rl("obj/nqr"))
    }

}