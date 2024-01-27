package me._olios.itemsmerging

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class PlayerInteractListener(private val plugin: ItemsMerging): Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        // Set the PlayerInteract event for merging
        val player = event.player
        if (!isReady(player)) return
        merge(player, MergeConfig(plugin, player).getValue(areEquals(player)))
    }

    private fun isReady(player: Player): Boolean {
        // Check for items in two hands
        val mainHand = player.inventory.itemInMainHand
        val offHand = player.inventory.itemInOffHand

        return mainHand.type != Material.AIR && offHand.type != Material.AIR
    }

    private fun areEquals(player: Player): Int? {
        // Check for equal metadata and return the CustomModelData or null
        val mainHand = player.inventory.itemInMainHand.itemMeta
        val offHand = player.inventory.itemInOffHand.itemMeta

        val hasMeta = (mainHand?.hasCustomModelData() == true && offHand?.hasCustomModelData() == true)
        val equals = (mainHand.equals(offHand))
        return if (hasMeta && equals) {
            mainHand.customModelData
        } else null
    }

    private fun merge(player: Player, setValue: Int?) {
        /* merging the items - remove the offhand item and
         change the CustomModelData for the main hand item
         */
        if (setValue == null) return
        player.inventory.setItemInOffHand(null)

        val item = player.inventory.itemInMainHand
        val itemMeta = item.itemMeta
        itemMeta.setCustomModelData(setValue)
        item.itemMeta = itemMeta

        player.inventory.setItemInMainHand(item)
    }
}