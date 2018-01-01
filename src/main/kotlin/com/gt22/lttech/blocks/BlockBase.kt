package com.gt22.lttech.blocks

import com.gt22.lttech.R
import net.minecraft.block.material.Material
import ru.pearx.libmc.common.blocks.BlockBase

open class BlockBase(mat: Material, name: String) : BlockBase(mat) {

    init {
        registryName = R.rl(name)
        unlocalizedName = name
        setCreativeTab(R.TAB)
    }

}