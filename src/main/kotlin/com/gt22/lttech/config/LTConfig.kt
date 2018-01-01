package com.gt22.lttech.config

import net.minecraftforge.common.config.Configuration

class LTConfig(cfg: Configuration) {

    val burstsPerNaquadah: Int
    val reactorMinEnergyGen: Int
    val reactorMaxEnergyGen: Int
    init {
        cfg.load()
        burstsPerNaquadah = cfg["reactor", "burstsPerNaquadah", 90, "Number of energy bursts from one naquadah cell"].int
        reactorMinEnergyGen = cfg["reactor", "reactorMinEnergyGen", 4500, "Minimum energy produced by naquadah reactor per burst"].int
        reactorMaxEnergyGen = cfg["reactor", "reactorMaxEnergyGen", 5000, "Maximum energy produced by naquadah reactor per burst"].int
        cfg.save()
    }

}