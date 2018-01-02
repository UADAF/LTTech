package com.gt22.lttech.network.packets

import com.gt22.lttech.tiles.NQReactorTile
import io.netty.buffer.ByteBuf
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import ru.pearx.libmc.common.networking.ByteBufTools

class ReactorTogglePacket(var pos: BlockPos = BlockPos(0, 0, 0)) : IMessage {


    override fun fromBytes(buf: ByteBuf) {
        pos = ByteBufTools.readBlockPos(buf)
    }

    override fun toBytes(buf: ByteBuf) {
        ByteBufTools.writeBlockPos(buf, pos)
    }

    object Handler : IMessageHandler<ReactorTogglePacket, IMessage> {

        override fun onMessage(message: ReactorTogglePacket, ctx: MessageContext): IMessage? {
            (ctx.serverHandler.player.world.getTileEntity(message.pos) as NQReactorTile).toggleState()
            return null
        }

    }
}