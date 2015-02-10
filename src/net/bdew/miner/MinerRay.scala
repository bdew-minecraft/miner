/*
 * Copyright (c) bdew, 2015
 * https://github.com/bdew/miner
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.miner

import java.io.File

import cpw.mods.fml.client.registry.RenderingRegistry
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.registry.EntityRegistry
import net.bdew.miner.config.Config
import net.bdew.miner.config.loader.TuningLoader
import net.bdew.miner.miner.{EntityMinerRay, RenderMinerRay}
import org.apache.logging.log4j.Logger

@Mod(modid = MinerRay.modId, version = "MINER_RAY_VER", name = "Miner Ray", dependencies = "required-after:bdlib", modLanguage = "scala")
object MinerRay {
  var log: Logger = null
  var instance = this

  final val modId = "miner_ray"

  var configDir: File = null

  def logInfo(msg: String, args: Any*) = log.info(msg.format(args: _*))
  def logWarn(msg: String, args: Any*) = log.warn(msg.format(args: _*))

  @EventHandler
  def preInit(event: FMLPreInitializationEvent) {
    log = event.getModLog
    configDir = new File(event.getModConfigurationDirectory, "MinerRay")
    TuningLoader.loadConfigFiles()
    Config.load()
  }

  @EventHandler
  def init(event: FMLInitializationEvent): Unit = {
    EntityRegistry.registerModEntity(classOf[EntityMinerRay], "bdewMinerRay", 1, this, 64, 1, true)
    RenderingRegistry.registerEntityRenderingHandler(classOf[EntityMinerRay], new RenderMinerRay)
  }

  @EventHandler
  def postInit(event: FMLPostInitializationEvent): Unit = {
    TuningLoader.loadDelayed()
  }
}