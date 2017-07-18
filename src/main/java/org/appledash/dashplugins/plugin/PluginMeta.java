package org.appledash.dashplugins.plugin;

import org.appledash.dashplugins.plugin.type.DeclarePlugin;
import org.appledash.dashplugins.util.SemVer;

/**
 * Represents metadata about a Plugin.
 */
public class PluginMeta {
    private final String name;
    private final String description;
    private final SemVer version;
    private String[] dependencies;

    public PluginMeta(DeclarePlugin declarePlugin) {
        this.name = declarePlugin.name();
        this.description = declarePlugin.description();
        this.version = new SemVer(declarePlugin.version());
        this.dependencies = declarePlugin.dependencies();
    }

    /**
     * Get the name of the associated Plugin.
     * @return Plugin name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description of the associated Plugin.
     * @return Plugin description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get a SemVer instance representing the version of the associated Plugin.
     * @return SemVer instance.
     */
    public SemVer getVersion() {
        return version;
    }

    /**
     * Get a String array containing the names of all other Plugins this Plugin depends on.
     * @return Plugin dependencies.
     */
    public String[] getDependencies() {
        return this.dependencies;
    }
}
