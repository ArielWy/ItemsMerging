package me._olios.itemsmerging

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class ItemsMerging : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        saveDefaultConfig()
        registerListeners()
    }

    private fun registerListeners() {
        Bukkit.getServer().pluginManager.registerEvents(PlayerInteractListener(this), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}