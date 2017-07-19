package org.appledash.dashplugins.plugin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents a language-agnostic plugin.
 */
public abstract class Plugin {
    private Logger logger;
    protected PluginMeta pluginMeta;

    /**
     * Default constructor for Plugin.
     * Plugins using this default constructor and not the one that takes PluginMeta MUST set this.pluginMeta in their constructor.
     */
    protected Plugin() {

    }

    /**
     * Initialize this Plugin with the given PluginMeta.
     * @param pluginMeta PluginMeta describing this Plugin.
     */
    public Plugin(PluginMeta pluginMeta) {
        this.pluginMeta = pluginMeta;
    }

    /**
     * Get the PluginMeta describing this Plugin.
     * @return PluginMeta
     */
    public PluginMeta getPluginMeta() {
        return pluginMeta;
    }

    /**
     * Get the Logger for this Plugin.
     * @return Logger instance.
     */
    public Logger getLogger() {
        if (this.logger == null) {
            this.logger = LogManager.getLogger(this.getPluginMeta().getName());
        }

        return this.logger;
    }

    /**
     * Called when this Plugin is loaded by a PluginManager.
     */
    public void onLoad() { }

    /**
     * Called when this Plugin is enabled by a PluginManager.
     */
    public void onEnable() { }

    /**
     * Called when this Plugin is disabled by a PluginManager.
     */
    public void onDisable() { }
}
