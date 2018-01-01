package com.gt22.lttech.tiles

import cofh.core.util.helpers.EnergyHelper
import cofh.redstoneflux.api.IEnergyProvider
import com.gt22.lttech.R
import com.gt22.lttech.registry.ItemRegistry
import com.gt22.lttech.tiles.helpers.SyncableEnergyStorage
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler
import ru.pearx.lib.RandomUtils
import ru.pearx.libmc.common.tiles.TileSyncable


class NQReactorTile(var facing: EnumFacing?) : TileSyncable(), ITickable, IEnergyProvider {


    private inner class Handler : ItemStackHandler(1) {

        override fun onContentsChanged(slot: Int) {
            super.onContentsChanged(slot)
            this@NQReactorTile.sendUpdatesToClients()
        }

    }

    constructor() : this(null)

    private val eng: SyncableEnergyStorage = SyncableEnergyStorage(60000000, 100000, sync = this::sendUpdatesToClients)
    private val inv = Handler()
    var isActive = true

    var naquadahLeft: Int = 0
        private set

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean
            = capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing)

    override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T?
            = if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inv) else super.getCapability(capability, facing)

    var tickCounter = 0
    override fun update() {
        if (world.isRemote) return
        transferEnergy()
        if (!isActive) return
        if (++tickCounter >= 10) {
            if (naquadahLeft > 0) {
                if((eng.maxEnergyStored - eng.energyStored) < R.cfg!!.reactorMinEnergyGen) return
                tickCounter = 0
                eng.modifyEnergyStored(RandomUtils.nextInt(R.cfg!!.reactorMinEnergyGen, R.cfg!!.reactorMaxEnergyGen, R.RAND))
                naquadahLeft--
            } else {
                val stack = inv.getStackInSlot(0)
                if(!stack.isEmpty && stack.item == ItemRegistry.naquadah) {
                    stack.grow(-1)
                    naquadahLeft = R.cfg!!.burstsPerNaquadah
                    sendUpdatesToClients()
                }
            }
        }
    }

    private fun transferEnergy() {
        for (f in arrayOf(facing, facing!!.opposite)) {
            val delta = EnergyHelper.insertEnergyIntoAdjacentEnergyReceiver(this, f, Math.min(eng.maxExtract, eng.energyStored), true)
            if (delta != 0) {
                EnergyHelper.insertEnergyIntoAdjacentEnergyReceiver(this, f, delta, false)
                eng.extractEnergy(delta, false)
            }
        }
    }

    override fun getMaxEnergyStored(from: EnumFacing?): Int {
        if(!canConnectEnergy(from)) return 0
        return eng.maxEnergyStored
    }

    override fun getEnergyStored(from: EnumFacing?): Int {
        if(!canConnectEnergy(from)) return 0
        return eng.energyStored
    }

    override fun extractEnergy(from: EnumFacing?, maxExtract: Int, simulate: Boolean): Int {
        println(from)
        if(!canConnectEnergy(from)) return 0
        return eng.extractEnergy(maxExtract, simulate)
    }

    override fun canConnectEnergy(from: EnumFacing?): Boolean {
        return from == null || from == facing || from == facing!!.opposite
    }

    fun toggleState(): Boolean {
        isActive = !isActive
        sendUpdatesToClients()
        return this.isActive
    }

    override fun writeToNBT(nbt: NBTTagCompound): NBTTagCompound {
        nbt.setTag("items", inv.serializeNBT())
        nbt.setTag("energy", eng.writeToNBT(NBTTagCompound()))
        nbt.setBoolean("active", isActive)
        nbt.setInteger("nqleft", naquadahLeft)
        nbt.setString("facing", facing.toString())
        return super.writeToNBT(nbt)
    }

    override fun readFromNBT(nbt: NBTTagCompound) {
        inv.deserializeNBT(nbt.getCompoundTag("items"))
        eng.readFromNBT(nbt.getCompoundTag("energy"))
        isActive = nbt.getBoolean("active")
        naquadahLeft = nbt.getInteger("nqleft")
        facing = EnumFacing.valueOf(nbt.getString("facing").toUpperCase())
        super.readFromNBT(nbt)
    }

}