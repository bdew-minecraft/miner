/*
 * Copyright (c) bdew, 2015
 * https://github.com/bdew/miner
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.miner.miner

import net.bdew.lib.items.SimpleItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World

object ItemMiner extends SimpleItem("Miner") {
  override def onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer) = {
    if (!world.isRemote)
      world.spawnEntityInWorld(new EntityMinerRay(player))
    stack
  }

  override def onItemUse(stack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, side: Int, xOff: Float, yOff: Float, zOff: Float): Boolean = {
    false
  }
}
