package com.gt22.lttech.network.packets

import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler

interface IPacket<T : IPacket<T>> : IMessage {



    fun getHandler(): IMessageHandler<T, *>

}