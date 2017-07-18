package org.appledash.dashplugins.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by appledash on 7/18/17.
 * Blackjack is best pony.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DeclarePlugin {
    String name();
    String description();
    String version();
    String[] dependencies() default {};
}
