/*
 * Copyright (c) bdew, 2015
 * https://github.com/bdew/miner
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.miner.miner

import net.bdew.lib.items.ItemUtils
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityThrowable
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.{MathHelper, MovingObjectPosition}
import net.minecraft.world.World

class EntityMinerRay(w: World) extends EntityThrowable(w) {
  var ttl = 10
  var aoe = 1
  var owner: EntityPlayer = null

  def this(p: EntityPlayer) = {
    this(p.worldObj)

    owner = p

    // code stolen from EntityThrowable ctor because we can't call it from scala...
    this.setSize(0.25F, 0.25F)
    this.setLocationAndAngles(p.posX, p.posY + p.getEyeHeight.toDouble, p.posZ, p.rotationYaw, p.rotationPitch)
    this.posX -= (MathHelper.cos(this.rotationYaw / 180.0F * Math.PI.toFloat) * 0.16F).toDouble
    this.posY -= 0.10000000149011612D
    this.posZ -= (MathHelper.sin(this.rotationYaw / 180.0F * Math.PI.toFloat) * 0.16F).toDouble
    this.setPosition(this.posX, this.posY, this.posZ)
    this.yOffset = 0.0F
    val f = 0.4F
    this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * Math.PI.toFloat) * MathHelper.cos(this.rotationPitch / 180.0F * Math.PI.toFloat) * f).toDouble
    this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * Math.PI.toFloat) * MathHelper.cos(this.rotationPitch / 180.0F * Math.PI.toFloat) * f).toDouble
    this.motionY = (-MathHelper.sin((this.rotationPitch + this.func_70183_g) / 180.0F * Math.PI.toFloat) * f).toDouble
    this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d, 1.0F)
  }

  override def getGravityVelocity = 0
  protected override def func_70182_d = 1.5F
  protected override def func_70183_g = 0F

  override def onUpdate() = {
    super.onUpdate()
    ttl -= 1
    if (ttl <= 0) {
      setDead()
    }
  }

  override def onImpact(pos: MovingObjectPosition) = {
    import scala.collection.JavaConversions._
    if (pos.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
      if (!worldObj.isRemote && owner != null) {
        val dropsList = for {
          x <- (pos.blockX - aoe) to (pos.blockX + aoe)
          y <- (pos.blockY - aoe) to (pos.blockY + aoe)
          z <- (pos.blockZ - aoe) to (pos.blockZ + aoe)
        } yield {
          val bl = worldObj.getBlock(x, y, z)
          if (bl == null || bl.getBlockHardness(worldObj, x, y, z) < 0 || bl.hasTileEntity(worldObj.getBlockMetadata(x, y, z))) {
            List.empty
          } else {
            val drops = bl.getDrops(worldObj, x, y, z, worldObj.getBlockMetadata(x, y, z), 0)
            worldObj.setBlockToAir(x, y, z)
            drops.toList
          }
        }
        for (item <- dropsList.flatten)
          ItemUtils.dropItemToPlayer(worldObj, owner, item)
        worldObj.createExplosion(null, posX, posY, posZ, 0, true)
      }
      setDead()
    }
  }
  override def writeEntityToNBT(tag: NBTTagCompound): Unit = {
    super.writeEntityToNBT(tag)
    tag.setInteger("aoe", aoe)
    tag.setInteger("ttl", ttl)
  }

  override def readEntityFromNBT(tag: NBTTagCompound): Unit = {
    super.readEntityFromNBT(tag)
    aoe = tag.getInteger("aoe")
    ttl = tag.getInteger("ttl")
  }
}
