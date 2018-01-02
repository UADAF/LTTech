package com.gt22.lttech.registry

import com.gt22.lttech.R
import com.gt22.lttech.tiles.NQReactorTile
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.fml.common.registry.GameRegistry
import kotlin.reflect.KClass

object TileRegistry {

    private fun <T : TileEntity> r(tile: KClass<T>) {
        GameRegistry.registerTileEntity(tile.java, "${R.MODID}:${tile.simpleName}")
    }

    fun reg() {
        r(NQReactorTile::class)
    }

}