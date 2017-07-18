package org.appledash.dashplugins.plugin.dependency;

import org.appledash.dashplugins.plugin.InvalidPluginException;

/**
 * Thrown when something goes wrong during resolving of dependencies for a Plugin.
 */
public class DependencyException extends InvalidPluginException {
    public DependencyException(String message) {
        super(message);
    }
}
