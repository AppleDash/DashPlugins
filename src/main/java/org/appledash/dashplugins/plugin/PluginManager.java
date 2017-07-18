package org.appledash.dashplugins.plugin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.appledash.dashplugins.plugin.dependency.DependencyResolver;
import org.appledash.dashplugins.plugin.dependency.DependencyResolverBasic;
import org.appledash.dashplugins.plugin.loader.PluginLoader;

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
    private final Set<Plugin> enabledPlugins = new HashSet<>();
    private DependencyResolver dependencyResolver = new DependencyResolverBasic();

    /**
     * Register a PluginLoader, which will be used to load Plugins when we call loadPlugins().
     * @param pluginLoader PluginLoader
     */
    public void registerLoader(PluginLoader pluginLoader) {
        if (pluginLoader == null) {
            throw new IllegalArgumentException("pluginLoader cannot be null!");
        }

        this.pluginLoaders.add(pluginLoader);
    }

    /**
     * Load all of the Plugins using all of the PluginLoaders we have registered.
     * This method can only be called once.
     *
     * @throws IllegalStateException if it is called more than once.
     */
    public void loadPlugins() {
        if (!this.loadedPlugins.isEmpty()) {
            throw new IllegalStateException("Cannot load plugins more than once!");
        }

        List<Plugin> plugins = new ArrayList<>();

        for (PluginLoader loader : this.pluginLoaders) {
            plugins.addAll(loader.loadPlugins());
        }

        plugins = this.dependencyResolver.sortPlugins(plugins);

        plugins.forEach(this::loadPlugin);
    }

    /**
     * Load the given Plugin and call its onLoad callback.
     *
     * @param plugin Plugin to load.
     * @throws IllegalStateException if we try to load the same Plugin more than once.
     */
    public void loadPlugin(Plugin plugin) {
        if (this.nameLookup.containsKey(plugin.getPluginMeta().getName().toLowerCase())) {
            throw new IllegalStateException("Cannot register a plugin with the same name twice!");
        }

        LOGGER.info("Loading plugin " + plugin.getPluginMeta().getName() + " version " + plugin.getPluginMeta().getVersion());
        this.loadedPlugins.add(plugin);
        this.nameLookup.put(plugin.getPluginMeta().getName().toLowerCase(), plugin);
        plugin.onLoad();
    }

    /**
     * Disable and unload the given Plugin and call its onLoad callback.
     *
     * @param plugin Plugin to unload.
     * @throws IllegalStateException if we try to unload a Plugin that isn't loaded.
     */
    public void unloadPlugin(Plugin plugin) {
        if (!this.loadedPlugins.contains(plugin)) {
            throw new IllegalStateException("Cannot unload plugin that isn't loaded!");
        }

        this.loadedPlugins.remove(plugin);
        this.nameLookup.remove(plugin.getPluginMeta().getName().toLowerCase());
    }

    /**
     * Get a Plugin by its name, case-insensitively.
     *
     * @param name Plugin name
     * @return Plugin if found, else Optional.empty()
     */
    public Optional<Plugin> getPlugin(String name) {
        return Optional.ofNullable(this.nameLookup.get(name.toLowerCase()));
    }

    /**
     * Check if the given Plugin is enabled.
     *
     * @param plugin Plugin instance.
     * @return True if enabled, false otherwise.
     * @throws IllegalStateException if the Plugin is not loaded.
     */
    public boolean isPluginEnabled(Plugin plugin) {
        if (!this.loadedPlugins.contains(plugin)) {
            throw new IllegalStateException("Cannot check enabled state of a Plugin that isn't loaded!");
        }

        return this.enabledPlugins.contains(plugin);
    }

    /**
     * Set the enabled state of the given Plugin and trigger its onEnable / onDisable callbacks.
     *
     * @param plugin Plugin instance.
     * @param enabled True to enable the plugin, false to disable it.
     * @throws IllegalStateException if we try to set the enabled state of a Plugin that isn't loaded.
     */
    public void setPluginEnabled(Plugin plugin, boolean enabled) {
        if (!this.loadedPlugins.contains(plugin)) {
            throw new IllegalStateException("Cannot set enabled state of a Plugin we don't have loaded!");
        }

        if (enabled) {
            this.enabledPlugins.add(plugin);
            plugin.onEnable();
        } else {
            this.enabledPlugins.remove(plugin);
            plugin.onDisable();
        }
    }

    /**
     * Get an immutable List containing all of the Plugins loaded by this PluginManager.
     * @return List of Plugins.
     */
    public List<Plugin> getLoadedPlugins() {
        return ImmutableList.copyOf(this.loadedPlugins);
    }

    /**
     * Get an immutable Set containing all of the Plugins currently enabled by this PluginManager.
     * @return Set of Plugins.
     */
    public Set<Plugin> getEnabledPlugins() {
        return ImmutableSet.copyOf(this.enabledPlugins);
    }
}
