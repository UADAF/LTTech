package com.gt22.lttech.interfaces

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos

interface IPosReceiver {
    fun savePos(pos: BlockPos?, player: EntityPlayer? = null, hand: EnumHand? = null, hitX: Float? = null, hitY: Float? = null, hitZ: Float? = null)
}