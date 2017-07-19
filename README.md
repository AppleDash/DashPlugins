DashPlugins
===========

A simple plugin manager for Java projects.

DashPlugins allows you to manager plugins for your Java projects in a simple and lightweight manner, while still being
somewhat featureful.

## How do I use this?

Declare a plugin:

```java
// Name must contain no spaces, and version must follow the SemVer specification.
@DeclarePlugin(name = "MyPlugin", description = "Example plugin for DashPlugins", version = "0.1.0")
public class MyPlugin extends JavaPlugin {
    // You can override various callbacks in here - check the source code for JavaPlugin to learn more.
}
```

Load some plugins from JARs in a folder:
```java
public class DashPluginsExample {}
    public static void main(String[] args) {
        PluginManager pluginManager = new PluginManager();
        pluginManager.registerLoader(new PluginLoaderJARFolder(new File("./plugins")));
        pluginManager.loadPlugins();
        // See the source code of PluginManager for what you can do from here.
    }
}
```