package me._olios.itemsmerging

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.meta.ItemMeta

class PlayerInteractListener(private val plugin: ItemsMerging): Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        // Set the PlayerInteract event for merging
        val player = event.player
        if (!isReady(player)) return
        merge(player, MergeConfig(plugin, player).getValue(getCustomModelDataIfItemsEqual(player)))
    }

    private fun isReady(player: Player): Boolean {
        // Check for items in two hands
        val mainHand = player.inventory.itemInMainHand
        val offHand = player.inventory.itemInOffHand

        return mainHand.type != Material.AIR && offHand.type != Material.AIR
    }

    private fun areItemsEqual(player: Player): Boolean {
        val mainHand = player.inventory.itemInMainHand.itemMeta
        val offHand = player.inventory.itemInOffHand.itemMeta

        val hasMeta = (mainHand?.hasCustomModelData() == true && offHand?.hasCustomModelData() == true)
        val equals = (mainHand.equals(offHand))
        val item = MergeConfig(plugin, player).getItem()
        val same = player.inventory.itemInMainHand.type == item

        return if (item is Material) {
            hasMeta && equals && same
        } else {
            hasMeta && equals
        }
    }

    private fun getCustomModelDataIfItemsEqual(player: Player): Int? {
        return if (areItemsEqual(player)) {
            player.inventory.itemInMainHand.itemMeta?.customModelData
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