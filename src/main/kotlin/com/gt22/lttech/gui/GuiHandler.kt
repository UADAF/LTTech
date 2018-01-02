package com.gt22.lttech.gui

import com.gt22.lttech.gui.container.NQReactorContainer
import com.gt22.lttech.gui.render.NQReactorGui
import com.gt22.lttech.tiles.NQReactorTile
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

object GuiHandler : IGuiHandler {

    val NQ_REACTOR = 0

    override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        val tile: TileEntity? = world.getTileEntity(BlockPos(x, y, z))
        return when(ID) {
            NQ_REACTOR -> NQReactorContainer(player.inventory, tile as NQReactorTile)
            else -> null
        }
    }

    override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        val tile: TileEntity? = world.getTileEntity(BlockPos(x, y, z))
        return when(ID) {
            NQ_REACTOR -> NQReactorGui(tile as NQReactorTile, NQReactorContainer(player.inventory, tile))
            else -> null
        }
    }
}