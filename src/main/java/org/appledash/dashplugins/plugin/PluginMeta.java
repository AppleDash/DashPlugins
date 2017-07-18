package org.appledash.dashplugins.plugin;

import org.appledash.dashplugins.plugin.DeclarePlugin;
import org.appledash.dashplugins.util.SemVer;

/**
 * Created by appledash on 7/18/17.
 * Blackjack is best pony.
 */
public class PluginMeta {
    private final String name;
    private final String description;
    private final SemVer version;

    public PluginMeta(DeclarePlugin declarePlugin) {
        this.name = declarePlugin.name();
        this.description = declarePlugin.description();
        this.version = new SemVer(declarePlugin.version());
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public SemVer getVersion() {
        return version;
    }
}