package com.gt22.lttech.gui.container

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack

abstract class ContainerWithPlayerInv(playerInv: IInventory, teslots: List<Slot>) : Container() {

    init {
        teslots.forEach {addSlotToContainer(it)}
        for (y in 0..2) for (x in 0..8) this.addSlotToContainer(Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18))
        for (x in 0..8) this.addSlotToContainer(Slot(playerInv, x, 8 + x * 18, 142))
    }

    override fun transferStackInSlot(playerIn: EntityPlayer, fromSlot: Int): ItemStack {
        var previous = ItemStack.EMPTY
        val slot = this.inventorySlots[fromSlot]
        if (slot.hasStack) {
            val current = slot.stack
            previous = current.copy()
            if (fromSlot < 2) {
                if (!this.mergeItemStack(current, 3, 37, true))
                    return ItemStack.EMPTY
            } else {
                if (!this.mergeItemStack(current, 0, 1, false))
                    return ItemStack.EMPTY
            }
            if (current.count == 0)
                slot.putStack(ItemStack.EMPTY)
            else
                slot.onSlotChanged()

            if (current.count == previous.count)
                return ItemStack.EMPTY
            slot.onTake(playerIn, current)
        }
        return previous
    }


}