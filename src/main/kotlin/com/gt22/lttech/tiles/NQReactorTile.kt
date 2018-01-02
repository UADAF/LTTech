package com.gt22.lttech.tiles

import cofh.core.util.helpers.EnergyHelper
import cofh.redstoneflux.api.IEnergyProvider
import cofh.redstoneflux.impl.EnergyStorage
import com.gt22.lttech.R
import com.gt22.lttech.registry.ItemRegistry
import com.gt22.lttech.tiles.helpers.SyncableEnergyStorage
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
import net.minecraftforge.items.ItemStackHandler
import ru.pearx.lib.RandomUtils
import ru.pearx.libmc.common.tiles.TileSyncable


class NQReactorTile(var facing: EnumFacing? = null) : TileSyncable(), ITickable, IEnergyProvider {

    private inner class Handler : ItemStackHandler(1) {

        override fun onContentsChanged(slot: Int) {
            super.onContentsChanged(slot)
            this@NQReactorTile.sendUpdatesToClients()
        }

    }

    private val eng: SyncableEnergyStorage = SyncableEnergyStorage(60000000, sync = this::sendUpdatesToClients)
    private val inv = Handler()
    private var tickCounter = 0
    val validFaces by lazy { arrayOf(null, facing!!, facing!!.opposite) }
    var isActive = true
        private set
    var naquadahLeft: Int = 0
        private set

    fun toggleState(): Boolean {
        isActive = !isActive
        sendUpdatesToClients()
        return this.isActive
    }

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean =
            capability == ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing)

    override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? =
            if (capability == ITEM_HANDLER_CAPABILITY) ITEM_HANDLER_CAPABILITY.cast(inv) else super.getCapability(capability, facing)

    override fun update() {
        if (world.isRemote) return
        transferEnergy()
        if (!isActive) return
        if (++tickCounter >= 10) {
            tickCounter = 0
            generate()
        }
    }

    private fun transferEnergy() {
        validFaces.forEach {
            if(it != null) {
                val delta = EnergyHelper.insertEnergyIntoAdjacentEnergyReceiver(this, it, Math.min(eng.maxExtract, eng.energyStored), true)
                if (delta != 0) {
                    EnergyHelper.insertEnergyIntoAdjacentEnergyReceiver(this, it, delta, false)
                    eng.extractEnergy(delta, false)
                }
            }
        }
    }

    val EnergyStorage.spaceLeft get() = this.maxEnergyStored - this.energyStored

    private fun generate() {
        if (eng.spaceLeft < R.cfg.reactorMinEnergyGen || !hasNaquadah()) return
        eng.modifyEnergyStored(RandomUtils.nextInt(R.cfg.reactorMinEnergyGen, R.cfg.reactorMaxEnergyGen, R.RAND))
        naquadahLeft--
    }

    private fun hasNaquadah(): Boolean {
        if (naquadahLeft > 0) return true
        val stack = inv.getStackInSlot(0)
        if (!stack.isEmpty && stack.item == ItemRegistry.naquadah) {
            stack.grow(-1)
            naquadahLeft = R.cfg.burstsPerNaquadah
            sendUpdatesToClients()
            return true
        }
        return false
    }

    override fun getMaxEnergyStored(from: EnumFacing?): Int {
        if (!canConnectEnergy(from)) return 0
        return eng.maxEnergyStored
    }

    override fun getEnergyStored(from: EnumFacing?): Int {
        if (!canConnectEnergy(from)) return 0
        return eng.energyStored
    }

    override fun extractEnergy(from: EnumFacing?, maxExtract: Int, simulate: Boolean): Int {
        if (!canConnectEnergy(from)) return 0
        return eng.extractEnergy(maxExtract, simulate)
    }

    override fun canConnectEnergy(from: EnumFacing?): Boolean = from in validFaces

    override fun writeToNBT(nbt: NBTTagCompound): NBTTagCompound {
        with(nbt) {
            setTag("items", inv.serializeNBT())
            setTag("energy", eng.writeToNBT(NBTTagCompound()))
            setBoolean("active", isActive)
            setInteger("nqleft", naquadahLeft)
            setString("facing", facing.toString())
        }
        return super.writeToNBT(nbt)
    }

    override fun readFromNBT(nbt: NBTTagCompound) {
        with(nbt) {
            inv.deserializeNBT(getCompoundTag("items"))
            eng.readFromNBT(getCompoundTag("energy"))
            isActive = getBoolean("active")
            naquadahLeft = getInteger("nqleft")
            facing = EnumFacing.valueOf(getString("facing").toUpperCase())
        }
        super.readFromNBT(nbt)
    }

}