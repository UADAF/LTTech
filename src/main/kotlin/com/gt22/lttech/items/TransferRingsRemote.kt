package com.gt22.lttech.items

import com.gt22.lttech.tiles.TransferRingsTile
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.Style
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World

class TransferRingsRemote : ItemBase("transferRingsRemote") {

    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        if (!worldIn.isRemote) {
            val rings = worldIn.getTileEntity(playerIn.position.down()) as? TransferRingsTile?
            if (rings != null) {
                val res = if (!playerIn.isSneaking) {
                    rings.transferUp(playerIn)
                } else {
                    rings.transferDown(playerIn)
                }
                val msg = when (res) {
                    TransferRingsTile.TransferResult.NO_POS -> "Position not set"
                    TransferRingsTile.TransferResult.NO_ENERGY -> "Not enough energy"
                    TransferRingsTile.TransferResult.OUT_OF_RANGE -> "Receiver ring is too far away"
                    else -> null
                }
                msg?.let { playerIn.sendMessage(TextComponentString(it).setStyle(Style().setColor(TextFormatting.RED))) }
                return ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn))
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn)
    }

    override fun onItemUse(player: EntityPlayer, worldIn: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val te = worldIn.getTileEntity(pos) as? TransferRingsTile
        if(!worldIn.isRemote && te != null) {
            val msg = """Pos 1: ${formatPos(te.upPos)}
                        |Pos 2: ${formatPos(te.downPos)}
                        |Energy: ${te.getEnergyStored(null)} / ${te.getMaxEnergyStored(null)} RF""".trimMargin()
            player.sendMessage(TextComponentString(msg))
            return EnumActionResult.SUCCESS
        }
        return EnumActionResult.PASS
    }

    private fun formatPos(pos: BlockPos?): String {
        if(pos == null) return "Empty"
        with(pos) {
            return "$x, $y, $z"
        }
    }

}