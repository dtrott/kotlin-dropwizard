package com.davidtrott.example.util;

import java.util.UUID;

/**
 * This class is a dirty hack to avoid type checking on hibernate id fields.
 *
 * https://kotlinlang.org/docs/java-interop.html#null-safety-and-platform-types
 */
public class PlatformType {
    public static final UUID nullUuid = null;
}
