package org.appledash.dashplugins.plugin.loader;

import org.appledash.dashplugins.plugin.Plugin;

import java.util.List;

/**
 * Represents an object that can load Plugins. When loadPlugins() is called, it must return a non-null but
 * potentially-empty list containing all of the Plugins loaded.
 */
public interface PluginLoader {
    List<Plugin> loadPlugins();
}
