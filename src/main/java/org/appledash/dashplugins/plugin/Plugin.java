package org.appledash.dashplugins.plugin;

/**
 * Created by appledash on 7/18/17.
 * Blackjack is best pony.
 */
public abstract class Plugin {
    protected PluginMeta pluginMeta;
    private boolean enabled;

    protected Plugin() {

    }

    public Plugin(PluginMeta pluginMeta) {
        this.pluginMeta = pluginMeta;
    }

    public PluginMeta getPluginMeta() {
        return pluginMeta;
    }

    public void onLoad() { }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
