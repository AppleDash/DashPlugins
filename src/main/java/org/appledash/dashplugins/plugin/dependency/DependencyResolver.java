package org.appledash.dashplugins.plugin.dependency;

import org.appledash.dashplugins.plugin.Plugin;

import java.util.List;

/**
 * Created by appledash on 7/18/17.
 * Blackjack is best pony.
 */
public interface DependencyResolver {
    /**
     * Takes in a list of all loaded Plugins, and returns the list sorted in an order in which it would be appropriate
     * to load the Plugins sequentially so as to have all Plugins' dependencies met.
     * Throws an exception if this is impossible.
     *
     * @param plugins List of Plugins
     * @return Sorted List of Plugins
     */
    List<Plugin> sortPlugins(List<Plugin> plugins);
}
