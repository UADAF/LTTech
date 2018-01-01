package com.gt22.lttech.tiles.helpers

import cofh.redstoneflux.api.IEnergyProvider
import cofh.redstoneflux.api.IEnergyStorage
import net.minecraft.util.EnumFacing

class EnergyProviderStorage(val eng: IEnergyStorage) : IEnergyProvider {
    override fun getMaxEnergyStored(from: EnumFacing?): Int {
        if(!canConnectEnergy(from)) return 0
        return eng.maxEnergyStored
    }

    override fun getEnergyStored(from: EnumFacing?): Int {
        println(canConnectEnergy(null))
        if(!canConnectEnergy(from)) return 0
        return eng.energyStored
    }
    override fun extractEnergy(from: EnumFacing?, maxExtract: Int, simulate: Boolean): Int {
        if(!canConnectEnergy(from)) return 0
        return eng.extractEnergy(maxExtract, simulate)
    }

    override fun canConnectEnergy(from: EnumFacing?): Boolean = true
}