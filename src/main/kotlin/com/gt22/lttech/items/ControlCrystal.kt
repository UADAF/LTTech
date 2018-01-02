package com.gt22.lttech.items

import com.gt22.lttech.interfaces.IPosReceiver
import com.gt22.lttech.utils.getPos
import com.gt22.lttech.utils.setPos
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.Style
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World


class ControlCrystal : ItemBase("controlCrystal") {

    override fun onItemUseFirst(player: EntityPlayer, worldIn: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, hand: EnumHand): EnumActionResult {
        val stack = player.getHeldItem(hand)!!
        if (player.isSneaking) {
            savePosition(stack, player, pos)
            return EnumActionResult.SUCCESS
        } else {
            val te = worldIn.getTileEntity(pos)
            if(te is IPosReceiver) {
                if (!worldIn.isRemote) {
                    te.savePos(stack.tagCompound?.getPos("pos"), player, hand, hitX, hitY, hitZ)
                    player.sendMessage(TextComponentString(if(hasPosition(stack)) "Position saved" else "Position cleared").setStyle(Style().setColor(TextFormatting.GREEN)))
                }
            }
        }
        return EnumActionResult.PASS
    }

    private fun savePosition(stack: ItemStack, player: EntityPlayer, pos: BlockPos) {
        stack.tagCompound = (stack.tagCompound ?: NBTTagCompound()).apply { setPos("pos", pos) }
        player.openContainer?.detectAndSendChanges()

    }


    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        if (playerIn.isSneaking) {
            playerIn.getHeldItem(handIn).tagCompound?.removeTag("pos")
        }
        return super.onItemRightClick(worldIn, playerIn, handIn)
    }

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        stack.tagCompound?.getPos("pos")?.let {
            with(tooltip) {
                add("X: ${it.x}")
                add("Y: ${it.y}")
                add("Z: ${it.z}")
            }
        }
    }

    override fun hasEffect(stack: ItemStack?): Boolean {
        return hasPosition(stack)
    }


    private fun hasPosition(stack: ItemStack?): Boolean {
        return stack?.tagCompound?.hasKey("pos") == true
    }

}