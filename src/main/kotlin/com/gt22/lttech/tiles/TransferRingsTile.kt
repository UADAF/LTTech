package com.gt22.lttech.tiles

import cofh.redstoneflux.api.IEnergyReceiver
import com.gt22.lttech.tiles.helpers.SyncableEnergyStorage
import net.minecraft.init.Blocks
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraft.util.math.BlockPos
import ru.pearx.libmc.common.tiles.TileSyncable
import sun.jvm.hotspot.opto.Block

class TransferRingsTile: TileSyncable(), IEnergyReceiver, ITickable {

    private var tickCounter: Int = 0
    private var structureState: Boolean = true

    private var upPos: BlockPos? = null
    private var downPos: BlockPos? = null

    override fun update() {
        if(++tickCounter >= 10) {
            tickCounter = 0
            // TODO Check Structure
        }
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        structureState = compound.getBoolean("structureState")
        upPos = compound.getPos("upPos")
        downPos = compound.getPos("downPos")
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        compound.setBoolean("structureState", structureState)
        compound.setPos("upPos", upPos)
        compound.setPos("downPos", downPos)
        return super.writeToNBT(compound)
    }

    private val storage: SyncableEnergyStorage = SyncableEnergyStorage(600000,10000,0,this::sendUpdatesToClients)

    override fun getMaxEnergyStored(from: EnumFacing?): Int = storage.maxEnergyStored

    override fun getEnergyStored(from: EnumFacing?): Int = storage.energyStored

    override fun canConnectEnergy(from: EnumFacing?): Boolean = true

    override fun receiveEnergy(from: EnumFacing?, maxReceive: Int, simulate: Boolean): Int = storage.receiveEnergy(maxReceive, simulate)

    private fun NBTTagCompound.setPos(name:String, pos: BlockPos?) {
        setIntArray(name, if(pos == null) intArrayOf() else intArrayOf(pos.x,pos.y,pos.z))
    }

    private fun NBTTagCompound.getPos(name: String): BlockPos? {
        if(!hasKey(name))
            return null
        val res = getIntArray(name)
        if(res.isEmpty())
            return null
        return BlockPos(res[0], res[1], res[2])
    }
}