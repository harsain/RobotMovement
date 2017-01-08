package com.harsain.robotmovement;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by harsain on 7/1/17.
 */
public enum DirectionEnum {
    NORTH, SOUTH, EAST, WEST;

    private final static Set<String> values = new HashSet<String>(DirectionEnum.values().length);

    static {
        for (DirectionEnum d : DirectionEnum.values()) {
            values.add(d.name());
        }
    }

    public static boolean contains(String value) {
        return values.contains(value);
    }
}
