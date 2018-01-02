package com.gt22.lttech.gui.render

import com.gt22.lttech.R
import com.gt22.lttech.gui.container.NQReactorContainer
import com.gt22.lttech.network.NetworkHandler
import com.gt22.lttech.network.packets.ReactorTogglePacket
import com.gt22.lttech.tiles.NQReactorTile
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.ResourceLocation
import ru.pearx.lib.Colors
import ru.pearx.libmc.client.gui.DrawingTools


class NQReactorGui(val tile: NQReactorTile, container: NQReactorContainer) : GuiContainer(container) {

    var texture = ResourceLocation(R.MODID, "textures/gui/nr/nqr.png")

    override fun initGui() {
        super.initGui()
        val res = ScaledResolution(mc)
        val w = res.scaledWidth
        val h = res.scaledHeight
        val activateBtn = object : GuiButton(1, (w/2)-83, (h/2)-78, "o") {
            override fun drawButton(mc: Minecraft, mouseX: Int, mouseY: Int, partialTicks: Float) {
                super.drawButton(mc, mouseX, mouseY, partialTicks)
                if (this@NQReactorGui.tile.isActive)
                    this.packedFGColour = 0x00ff00
                else
                    this.packedFGColour = 0xff0000
            }
        }
        buttonList.add(activateBtn.apply { width = 10; height = width })

    }

    override fun actionPerformed(button: GuiButton?) = NetworkHandler.sendToServer(ReactorTogglePacket(tile.pos))


    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        drawEnergy()
        drawNaquadah()
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        GlStateManager.color(1f, 1f, 1f)
        val k = (this.width - this.xSize) / 2
        val l = (this.height - this.ySize) / 2
        mc.renderEngine.bindTexture(texture)
        drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize)
        GlStateManager.color(1f, 1f, 1f)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        renderHoveredToolTip(mouseX, mouseY)
    }

    private fun drawEnergy() {
        val max = tile.getMaxEnergyStored(null)
        val now = tile.getEnergyStored(null)
        val barWidth = 150
        val barHeight = 7

        val prec = now.toFloat() / max.toFloat()

        mc.renderEngine.bindTexture(texture)
        drawTexturedModalRect(13, 54, 0, 166, Math.round(Math.floor((barWidth * prec).toDouble())).toInt(), barHeight)
        DrawingTools.drawString(now.toString() + "/" + max + " RF", 13, 43, Colors.RED_50)
    }

    private fun drawNaquadah() {
        val max = R.cfg!!.burstsPerNaquadah
        val now = tile.naquadahLeft
        val barWidth = 150
        val barHeight = 7

        val prec = now.toDouble() / max.toDouble()
        mc.renderEngine.bindTexture(texture)
        drawTexturedModalRect(13, 68, 0, 180, Math.round(Math.floor((barWidth * prec))).toInt(), barHeight)
    }






}