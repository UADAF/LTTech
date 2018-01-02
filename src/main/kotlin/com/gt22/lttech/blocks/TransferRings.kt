package com.gt22.lttech.blocks

import com.gt22.lttech.registry.ItemRegistry
import com.gt22.lttech.tiles.TransferRingsTile
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class TransferRings : BlockBase(Material.IRON, "transferRings"){

    override fun hasTileEntity(state: IBlockState?): Boolean = true

    override fun createTileEntity(world: World?, state: IBlockState?): TileEntity? = TransferRingsTile()

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {

        return true
    }
}