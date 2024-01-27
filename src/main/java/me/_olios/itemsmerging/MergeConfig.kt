package me._olios.itemsmerging

import me._olios.itemsmerging.ItemsMerging
import org.bukkit.entity.Player

class MergeConfig(private val plugin: ItemsMerging, private val player: Player) {
    private val config = plugin.config

    fun loadConfig() {
        plugin.reloadConfig()
    }

    fun getValue(checkValue: Int?): Int? {
        if (checkValue == null) return null
        val value = config.getMapList("combineItems")
        for (check in value) {
            val getValue = (check["sourceItemID"] as Int)
            val setValue = (check["targetItemID"] as Int)
            if (checkValue == getValue)
                return setValue
        }
        return null
    }
}