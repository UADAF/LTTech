package com.gt22.lttech.items

import com.gt22.lttech.tiles.TransferRingsTile
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.text.Style
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World

class TransferRingsRemote : ItemBase("transferRingsRemote") {

    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        if(!worldIn.isRemote) {
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
                msg?.let {playerIn.sendMessage(TextComponentString(it).setStyle(Style().setColor(TextFormatting.RED)))}
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn)
    }

}