package org.appledash.dashplugins.plugin.type;

import org.appledash.dashplugins.plugin.PluginMeta;
import org.appledash.dashplugins.plugin.InvalidPluginException;
import org.appledash.dashplugins.plugin.Plugin;

/**
 * Represents a Plugin written in the Java programming language.
 */
public abstract class JavaPlugin extends Plugin {
    public JavaPlugin() {
        if (!this.getClass().isAnnotationPresent(DeclarePlugin.class)) {
            throw new InvalidPluginException("Plugin must have @DeclarePlugin annotation!");
        }

        this.pluginMeta = new PluginMeta(this.getClass().getAnnotation(DeclarePlugin.class));
    }
}
