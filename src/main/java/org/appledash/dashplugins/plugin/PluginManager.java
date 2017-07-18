package org.appledash.dashplugins.plugin;

import com.google.common.collect.ImmutableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.appledash.dashplugins.plugin.InvalidPluginException;
import org.appledash.dashplugins.plugin.Plugin;
import org.appledash.dashplugins.plugin.PluginLoader;

import java.util.*;

/**
 * Created by appledash on 7/18/17.
 * Blackjack is best pony.
 */
public class PluginManager {
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private final List<PluginLoader> pluginLoaders = new ArrayList<>();
    private final List<Plugin> loadedPlugins = new ArrayList<>();
    private final Map<String, Plugin> nameLookup = new HashMap<>();

    public void registerLoader(PluginLoader pluginLoader) {
        this.pluginLoaders.add(pluginLoader);
    }

    public void loadPlugins() {
        for (PluginLoader loader : this.pluginLoaders) {
            loader.loadPlugins().forEach(this::loadPlugin);
        }
    }

    public void loadPlugin(Plugin plugin) {
        if (this.nameLookup.containsKey(plugin.getPluginMeta().getName().toLowerCase())) {
            throw new InvalidPluginException("Cannot register a plugin with the same name twice!");
        }

        LOGGER.info("Loading plugin " + plugin.getPluginMeta().getName() + " version " + plugin.getPluginMeta().getVersion());
        this.loadedPlugins.add(plugin);
        this.nameLookup.put(plugin.getPluginMeta().getName(), plugin);
        plugin.onLoad();
    }

    public void unloadPlugin(Plugin plugin) {
        if (!this.loadedPlugins.contains(plugin)) {
            throw new IllegalStateException("Cannot unload plugin that isn't loaded!");
        }

        this.loadedPlugins.remove(plugin);
        this.nameLookup.remove(plugin.getPluginMeta().getName().toLowerCase());
    }

    public Optional<Plugin> getPlugin(String name) {
        return Optional.ofNullable(this.nameLookup.get(name.toLowerCase()));
    }

    public List<Plugin> getLoadedPlugins() {
        return ImmutableList.copyOf(this.loadedPlugins);
    }
}
