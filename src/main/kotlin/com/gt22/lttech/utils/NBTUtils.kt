package com.gt22.lttech.utils

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos

fun NBTTagCompound.setPos(name:String, pos: BlockPos?) {
    setIntArray(name, if(pos == null) intArrayOf() else intArrayOf(pos.x,pos.y,pos.z))
}

fun NBTTagCompound.getPos(name: String): BlockPos? {
    if(!hasKey(name))
        return null
    val res = getIntArray(name)
    if(res.isEmpty())
        return null
    return BlockPos(res[0], res[1], res[2])
}