package org.appledash.dashplugins.plugin.dependency;

import org.appledash.dashplugins.plugin.Plugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a basic, iterative Dependency resolver.
 */
public class DependencyResolverBasic implements DependencyResolver {
    @Override
    public List<Plugin> sortPlugins(List<Plugin> pluginsIn) {
        List<Plugin> plugins = new ArrayList<>(pluginsIn);
        List<Plugin> sorted = new ArrayList<>();

        while (!plugins.isEmpty()) {
            boolean added = false;

            for (Iterator<Plugin> iterator = plugins.iterator(); iterator.hasNext(); ) {
                Plugin plugin = iterator.next();

                if (plugin.getPluginMeta().getDependencies().length == 0) {
                    sorted.add(plugin);
                    iterator.remove();
                    added = true;
                } else if (hasAllDependencies(plugin.getPluginMeta().getDependencies(), sorted)) {
                    sorted.add(plugin);
                    iterator.remove();
                    added = true;
                }
            }

            if (!added && !plugins.isEmpty()) {
                throw new DependencyException("Some Plugins have either unmet or circular dependencies: " + plugins.stream().map(p -> p.getPluginMeta().getName()).collect(Collectors.toList()));
            }
        }

        return sorted;
    }

    // TODO: Make more efficient?
    private boolean hasAllDependencies(String[] depNames, List<Plugin> list) {
        List<Plugin> dependencies = new ArrayList<>();

        for (Plugin plugin : list) {
            for (String depName : depNames) {
                if (plugin.getPluginMeta().getName().equals(depName)) {
                    dependencies.add(plugin);
                    break;
                }
            }
        }

        return dependencies.size() == depNames.length;
    }
}
