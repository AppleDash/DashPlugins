package org.appledash.dashplugins.plugin.loader;

import com.google.common.reflect.ClassPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.appledash.dashplugins.plugin.Plugin;
import org.appledash.dashplugins.plugin.type.DeclarePlugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * This PluginLoader attempts to load every Plugin declared in every JAR file in the folder passed in jarFolder.
 */
public class PluginLoaderJARFolder implements PluginLoader {
    private static final Logger LOGGER = LogManager.getLogger("PluginLoaderJARFolder");
    private final File jarFolder;

    public PluginLoaderJARFolder(File jarFolder) {
        if (jarFolder.exists() && !jarFolder.isDirectory()) {
            throw new IllegalArgumentException("jarFolder must be a directory!");
        }

        if (!jarFolder.exists()) {
            if (!jarFolder.mkdirs()) {
                throw new RuntimeException("jarFolder did not exist and I could not make it!");
            }
        }

        this.jarFolder = jarFolder;
    }


    @Override
    public List<Plugin> loadPlugins() {
        List<Plugin> plugins = new ArrayList<>();

        for (File file : this.jarFolder.listFiles()) {
            if (!file.isFile()) {
                continue;
            }

            if (!isJarFile(file)) {
                continue;
            }

            if (!file.canRead()) {
                LOGGER.warn("Cannot read file " + file.getAbsolutePath() + ", skipping.");
            }

            try {
                // searchClassLoader only knows about classes in the JAR, so we can get only the classes in there.
                // loadClassLoader knows about every class, so when we actually load the class from the JAR, it can see its dependencies (eg: this library).
                ClassLoader searchClassLoader = new URLClassLoader(new URL[] { file.toURI().toURL() }, null);
                ClassLoader loadClassLoader = new URLClassLoader(new URL[] { file.toURI().toURL() });
                ClassPath searchClassPath = ClassPath.from(searchClassLoader);

                for(ClassPath.ClassInfo info : searchClassPath.getAllClasses()) {
                    // TODO: Something seems wrong here.
                    Class clazz = loadClassLoader.loadClass(info.getName());

                    if (!Plugin.class.isAssignableFrom(clazz)) {
                        continue;
                    }

                    if (!clazz.isAnnotationPresent(DeclarePlugin.class)) {
                        LOGGER.warn("Found a class (" + clazz.getName() + ") that extends Plugin but it doesn't have an @DeclarePlugin annotation, skipping it.");
                        continue;
                    }

                    Plugin plugin = (Plugin) clazz.newInstance();
                    plugins.add(plugin);
                }
            } catch (Exception e) {
                LOGGER.error("Failed to load Plugin from file " + file.getAbsolutePath(), e);
            }
        }

        return plugins;
    }

    private boolean isJarFile(File file) {
        String[] parts = file.getAbsolutePath().split("\\.");
        return parts[parts.length - 1].equalsIgnoreCase("jar");
    }
}
