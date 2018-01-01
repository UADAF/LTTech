package com.gt22.lttech.items

import com.gt22.lttech.R
import ru.pearx.libmc.common.items.ItemBase

open class ItemBase(name: String) : ItemBase() {


    init {
        registryName = R.rl(name)
        unlocalizedName = name
        creativeTab = R.TAB
    }

}