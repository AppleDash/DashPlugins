package org.appledash.dashplugins.plugin;

/**
 * This Exception is thrown when there is an issue with a Plugin that prevents it from being loaded.
 */
public class InvalidPluginException extends RuntimeException {
    public InvalidPluginException(String message) {
        super(message);
    }
}
