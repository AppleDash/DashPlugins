package org.appledash.dashplugins.plugin.dependency;

import org.appledash.dashplugins.plugin.Plugin;

import java.util.List;

/**
 * Created by appledash on 7/18/17.
 * Blackjack is best pony.
 */
public interface DependencyResolver {
    List<Plugin> sortPlugins(List<Plugin> plugins);
}
