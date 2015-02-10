/*
 * Copyright (c) bdew, 2015
 * https://github.com/bdew/miner
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.miner.miner

import net.minecraft.client.renderer.entity.Render
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.client.renderer.{OpenGlHelper, Tessellator}
import net.minecraft.entity.Entity
import org.lwjgl.opengl.GL11

class RenderMinerRay extends Render {
  def doRender(entity: Entity, x: Double, y: Double, z: Double, p1: Float, subFrame: Float) {
    this.bindEntityTexture(entity)
    GL11.glPushMatrix()
    GL11.glTranslatef(x.toFloat, y.toFloat, z.toFloat)
    GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * subFrame - 90.0F, 0.0F, 1.0F, 0.0F)
    GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * subFrame, 0.0F, 0.0F, 1.0F)

    val T = Tessellator.instance
    val scale = 9 / 160F
    val L = 8D
    val W = 0.5D

    GL11.glScalef(scale, scale, scale)
    GL11.glTranslatef(-4.0F, 0.0F, 0.0F)
    GL11.glColor3f(1F, 0F, 0F)

    GL11.glDisable(GL11.GL_LIGHTING)
    GL11.glDisable(GL11.GL_TEXTURE_2D)

    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680, 0)

    T.startDrawingQuads()
    T.addVertex(-L, -W, W)
    T.addVertex(L, -W, W)
    T.addVertex(L, W, W)
    T.addVertex(-L, W, W)
    T.draw()

    T.startDrawingQuads()
    T.addVertex(L, W, -W)
    T.addVertex(L, -W, -W)
    T.addVertex(-L, -W, -W)
    T.addVertex(-L, W, -W)
    T.draw()

    T.startDrawingQuads()
    T.addVertex(-L, -W, -W)
    T.addVertex(L, -W, -W)
    T.addVertex(L, -W, W)
    T.addVertex(-L, -W, W)
    T.draw()

    T.startDrawingQuads()
    T.addVertex(L, W, W)
    T.addVertex(L, W, -W)
    T.addVertex(-L, W, -W)
    T.addVertex(-L, W, W)
    T.draw()

    T.startDrawingQuads()
    T.addVertex(-L, W, W)
    T.addVertex(-L, W, -W)
    T.addVertex(-L, -W, -W)
    T.addVertex(-L, -W, W)
    T.draw()

    T.startDrawingQuads()
    T.addVertex(L, -W, -W)
    T.addVertex(L, W, -W)
    T.addVertex(L, W, W)
    T.addVertex(L, -W, W)
    T.draw()

    GL11.glColor3f(1F, 1F, 1F)
    GL11.glEnable(GL11.GL_TEXTURE_2D)
    GL11.glEnable(GL11.GL_LIGHTING)
    GL11.glPopMatrix()
  }

  override def getEntityTexture(e: Entity) = TextureMap.locationItemsTexture
}
