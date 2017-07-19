package org.appledash.dashplugins.test.integration;

import org.appledash.dashplugins.plugin.PluginManager;
import org.appledash.dashplugins.plugin.loader.PluginLoaderJARFolder;
import org.appledash.dashplugins.plugin.type.DeclarePlugin;
import org.appledash.dashplugins.plugin.type.JavaPlugin;

/**
 * Created by appledash on 7/18/17.
 * Blackjack is best pony.
 */
public class Tester {
    public static void main(String[] args) {
        PluginManager pluginManager = new PluginManager();
        // pluginManager.registerLoader(() -> ImmutableList.of(new Bar(), new PluginExample(), new Foo()));
        pluginManager.registerLoader(new PluginLoaderJARFolder(new java.io.File("./plugins")));
        pluginManager.loadPlugins();
        pluginManager.enableAllPlugins();
        // System.out.println(pluginManager.getPlugin("Example").get().getPluginMeta().getDescription());
    }

    @DeclarePlugin(name = "Example", description = "Example plugin", version = "0.1.0", dependencies = { "Foo", "Bar" })
    private static class PluginExample extends JavaPlugin {

    }

    @DeclarePlugin(name = "Foo", description = "Foo plugin", version = "0.1.0")
    private static class Foo extends JavaPlugin {

    }

    @DeclarePlugin(name = "Bar", description = "Bar plugin", version = "0.1.0")
    private static class Bar extends JavaPlugin {

    }
}
