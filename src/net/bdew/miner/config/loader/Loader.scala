/*
 * Copyright (c) bdew, 2015
 * https://github.com/bdew/miner
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.miner.config.loader

import net.bdew.lib.recipes.RecipeLoader
import net.bdew.lib.recipes.gencfg.GenericConfigLoader
import net.bdew.miner.config.Tuning

class Loader extends RecipeLoader with GenericConfigLoader {
  val cfgStore = Tuning
}
