package com.gt22.lttech.tiles

import cofh.redstoneflux.api.IEnergyReceiver
import com.gt22.lttech.R
import com.gt22.lttech.interfaces.IPosReceiver
import com.gt22.lttech.tiles.helpers.SyncableEnergyStorage
import com.gt22.lttech.utils.getPos
import com.gt22.lttech.utils.setPos
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.ITickable
import net.minecraft.util.math.BlockPos
import ru.pearx.libmc.common.tiles.TileSyncable

class TransferRingsTile : TileSyncable(), IEnergyReceiver, ITickable, IPosReceiver {
    override fun savePos(pos: BlockPos?, player: EntityPlayer?, hand: EnumHand?, hitX: Float?, hitY: Float?, hitZ: Float?) {
        when(hand) {
            EnumHand.MAIN_HAND -> upPos = pos
            EnumHand.OFF_HAND -> downPos = pos
        }
        sendUpdatesToClients()
    }

    private var tickCounter: Int = 0
    private var structureState: Boolean = true
    private val eng: SyncableEnergyStorage = SyncableEnergyStorage(600000,10000,0,this::sendUpdatesToClients)
    var upPos: BlockPos? = null
        private set
    var downPos: BlockPos? = null
        private set

    enum class TransferResult {
        SUCCESS,
        NO_POS,
        NO_ENERGY,
        OUT_OF_RANGE
    }

    fun transferUp(player: EntityPlayer): TransferResult = transfer(player, upPos)

    fun transferDown(player: EntityPlayer): TransferResult = transfer(player, downPos)

    private fun transfer(player: EntityPlayer, pos: BlockPos?): TransferResult {
        if(pos != null) {
            if(!checkRange(pos)) return TransferResult.OUT_OF_RANGE
            if(eng.energyStored >= R.cfg.ringsEnergyPerTransfer) {
                player.setPositionAndUpdate(pos.x.toDouble(), pos.y.toDouble() + 1, pos.z.toDouble())
                eng.modifyEnergyStored(-R.cfg.ringsEnergyPerTransfer)
                return TransferResult.SUCCESS
            }
            return TransferResult.NO_ENERGY
        }
        return TransferResult.NO_POS
    }

    private fun checkRange(pos: BlockPos): Boolean {
        if(pos.x == this.pos.x && pos.z == this.pos.z) return true //Allow unlimited travel in vertical line
        return Math.sqrt(getDistanceSq(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble())) < R.cfg.ringsMaxTravelDistance
    }

    override fun update() {
        if(++tickCounter >= 10) {
            tickCounter = 0
            // TODO Check Structure
        }
    }



    override fun writeToNBT(nbt: NBTTagCompound): NBTTagCompound {
        with(nbt) {
            setPos("upPos", upPos)
            setPos("downPos", downPos)
        }
        return super.writeToNBT(nbt)
    }

    override fun readFromNBT(nbt: NBTTagCompound) {
        with(nbt) {
            upPos = getPos("upPos")
            downPos = getPos("downPos")
        }
        super.readFromNBT(nbt)
    }

    override fun getMaxEnergyStored(from: EnumFacing?): Int = eng.maxEnergyStored

    override fun getEnergyStored(from: EnumFacing?): Int = eng.energyStored

    override fun canConnectEnergy(from: EnumFacing?): Boolean = true

    override fun receiveEnergy(from: EnumFacing?, maxReceive: Int, simulate: Boolean): Int = eng.receiveEnergy(maxReceive, simulate)
}