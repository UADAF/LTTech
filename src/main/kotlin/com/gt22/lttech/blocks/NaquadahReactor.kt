package com.gt22.lttech.blocks

import com.gt22.lttech.LTTech
import com.gt22.lttech.gui.GuiHandler
import com.gt22.lttech.tiles.NQReactorTile
import net.minecraft.block.material.Material
import net.minecraft.block.state.BlockFaceShape
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.Mirror
import net.minecraft.util.Rotation
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.items.CapabilityItemHandler
import ru.pearx.libmc.common.blocks.controllers.HorizontalFacingController

class NaquadahReactor : BlockBase(Material.IRON, "nqreactor") {
    override fun hasTileEntity(state: IBlockState): Boolean = true
    override fun createTileEntity(world: World, state: IBlockState): TileEntity? = NQReactorTile(state.getValue(HorizontalFacingController.FACING_H))
    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        playerIn.openGui(LTTech, GuiHandler.NQ_REACTOR, worldIn, pos.x, pos.y, pos.z)
        return true
    }

    override fun onBlockHarvested(worldIn: World, pos: BlockPos, state: IBlockState, player: EntityPlayer) {
        if (worldIn.isRemote) return
        val te: NQReactorTile? = worldIn.getTileEntity(pos) as NQReactorTile?
        val stack: ItemStack? = te?.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)?.getStackInSlot(0)
        stack?.let {
            val ei = EntityItem(worldIn, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), it)
            ei.motionY = 0.3
            worldIn.spawnEntity(ei)
        }
    }

    override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos): AxisAlignedBB {
        val facing = state.getValue(HorizontalFacingController.FACING_H)
        return when (facing) {
            EnumFacing.EAST, EnumFacing.WEST -> AxisAlignedBB(0.0, 0.0, 0.35, 1.0, 0.4, 0.65)
            EnumFacing.NORTH, EnumFacing.SOUTH -> AxisAlignedBB(0.35, 0.0, 0.0, 0.65, 0.4, 1.0)
            else -> super.getBoundingBox(state, source, pos) //Should not happen
        }
    }

    override fun isFullBlock(state: IBlockState): Boolean = false

    override fun isFullCube(state: IBlockState): Boolean = false

    override fun isOpaqueCube(state: IBlockState): Boolean = false

    override fun getBlockFaceShape(p_193383_1_: IBlockAccess, p_193383_2_: IBlockState, p_193383_3_: BlockPos, p_193383_4_: EnumFacing): BlockFaceShape = BlockFaceShape.UNDEFINED

    override fun createBlockState(): BlockStateContainer = BlockStateContainer(this, HorizontalFacingController.FACING_H)

    override fun getStateFromMeta(meta: Int): IBlockState = HorizontalFacingController.getStateFromMeta(defaultState, meta)

    override fun getMetaFromState(state: IBlockState): Int = HorizontalFacingController.getMetaFromState(state)

    override fun getStateForPlacement(world: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase, hand: EnumHand): IBlockState = HorizontalFacingController.getStateForPlacement(defaultState, placer).withRotation(Rotation.CLOCKWISE_90)

    override fun withMirror(state: IBlockState, mirrorIn: Mirror): IBlockState = HorizontalFacingController.withMirror(state, mirrorIn)

    override fun withRotation(state: IBlockState, rot: Rotation): IBlockState = HorizontalFacingController.withRotation(state, rot)
}