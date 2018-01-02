package com.gt22.lttech.config

import net.minecraftforge.common.config.Configuration

class LTConfig(cfg: Configuration) {

    val reactorBurstsPerNaquadah: Int
    val reactorMinEnergyGen: Int
    val reactorMaxEnergyGen: Int

    val ringsEnergyPerTransfer: Int
    val ringsMaxTravelDistance: Int
    init {
        cfg.load()
        reactorBurstsPerNaquadah = cfg["reactor", "burstsPerNaquadah", 90, "Number of energy bursts from one naquadah cell"].int
        reactorMinEnergyGen = cfg["reactor", "minEnergyGen", 4500, "Minimum energy produced by naquadah reactor per burst"].int
        reactorMaxEnergyGen = cfg["reactor", "maxEnergyGen", 5000, "Maximum energy produced by naquadah reactor per burst"].int

        ringsEnergyPerTransfer = cfg["rings", "energyPerTransfer", 10000, "Energy required per one ring transfer"].int
        ringsMaxTravelDistance = cfg["rings", "maxTravelDistance", 60, "Maximum distance for rings. This doesn't apply if rings located in save XZ coordinates (One ring above/below another)"].int
        cfg.save()
    }

}