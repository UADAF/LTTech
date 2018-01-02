package com.gt22.lttech.network

import com.gt22.lttech.R
import com.gt22.lttech.network.packets.ReactorTogglePacket
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import net.minecraftforge.fml.relauncher.Side

object NetworkHandler {
    val ch = SimpleNetworkWrapper(R.MODID)


    fun reg() {
        var id = 0
        ch.registerMessage(ReactorTogglePacket.Handler, ReactorTogglePacket::class.java, id++, Side.SERVER)
    }

    fun sendToAll(msg: IMessage) = ch.sendToAll(msg)

    fun sendTo(msg: IMessage, player: EntityPlayerMP) = ch.sendTo(msg, player)

    fun sendToAllAround(msg: IMessage, pos: BlockPos, range: Double, dimension: Int = 0) = ch.sendToAllAround(msg, NetworkRegistry.TargetPoint(dimension, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), range))

    fun sendToDimension(msg: IMessage, dimension: Int) = ch.sendToDimension(msg, dimension)

    fun sendToServer(msg: IMessage) = ch.sendToServer(msg)
}