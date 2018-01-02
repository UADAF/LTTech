package com.gt22.lttech.gui.container

import com.gt22.lttech.tiles.NQReactorTile
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class NQReactorContainer(playerInv: IInventory, var entity: NQReactorTile)
    : ContainerWithPlayerInv(playerInv, listOf(SlotItemHandler(entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), 0, 80, 17))) {

    override fun canInteractWith(playerIn: EntityPlayer): Boolean {
        return entity.getDistanceSq(playerIn.posX, playerIn.posY, playerIn.posZ) < 64 // Distance < 8
    }
}