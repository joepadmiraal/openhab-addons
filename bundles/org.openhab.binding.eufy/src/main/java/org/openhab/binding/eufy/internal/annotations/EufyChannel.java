/**
 * Copyright (c) 2010-2022 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.eufy.internal.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Indicator if this property is exposed as a channel
 *
 * @author Iwan Bron
 */
@Retention(RetentionPolicy.RUNTIME)
@NonNullByDefault
public @interface EufyChannel {

    /**
     * if this property is in the default status response
     */
    boolean inStatus() default true;

    /**
     * command to turn the property ON
     */
    String on() default "";

    /**
     * command to turn the property OFF
     */
    String off() default "";

    /**
     * command to get the current state of the property
     */
    String get() default "";

    @Nullable
    Class<?> command() default void.class;
}
