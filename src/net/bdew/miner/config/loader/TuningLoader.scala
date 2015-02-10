/*
 * Copyright (c) bdew, 2015
 * https://github.com/bdew/miner
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.miner.config.loader

import java.io.{File, FileWriter}

import net.bdew.lib.recipes.RecipesHelper
import net.bdew.miner.MinerRay

object TuningLoader {
  val loader = new Loader

  def loadDelayed() = loader.processRecipeStatements()

  def loadConfigFiles() {
    if (!MinerRay.configDir.exists()) {
      MinerRay.configDir.mkdir()
      val nl = System.getProperty("line.separator")
      val f = new FileWriter(new File(MinerRay.configDir, "readme.txt"))
      f.write("Any .cfg files in this directory will be loaded after the internal configuration, in alphabetic order" + nl)
      f.write("Files in 'overrides' directory with matching names cab be used to override internal configuration" + nl)
      f.close()
    }

    RecipesHelper.loadConfigs(
      modName = "Miner Ray",
      listResource = "/assets/miner_ray/config/files.lst",
      configDir = MinerRay.configDir,
      resBaseName = "/assets/miner_ray/config/",
      loader = loader)
  }
}
