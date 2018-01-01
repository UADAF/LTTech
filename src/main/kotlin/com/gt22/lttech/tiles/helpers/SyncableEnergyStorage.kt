package com.gt22.lttech.tiles.helpers

import cofh.redstoneflux.impl.EnergyStorage

class SyncableEnergyStorage(capacity: Int, maxReceive: Int = capacity, maxExtract: Int = maxReceive, var sync: () -> Unit = {}) : EnergyStorage(capacity, maxReceive, maxExtract) {

    override fun modifyEnergyStored(energy: Int) {
        super.modifyEnergyStored(energy)
        if(energy != 0) sync()
    }

    override fun extractEnergy(maxExtract: Int, simulate: Boolean): Int {
        val delta = super.extractEnergy(maxExtract, simulate)
        if(delta != 0) sync()
        return delta
    }

    override fun receiveEnergy(maxReceive: Int, simulate: Boolean): Int {
        val delta = super.receiveEnergy(maxReceive, simulate)
        if(delta != 0) sync()
        return delta
    }

}