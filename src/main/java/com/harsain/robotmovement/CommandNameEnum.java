package com.harsain.robotmovement;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by harsain on 7/1/17.
 */
public enum CommandNameEnum {
    PLACE, MOVE, LEFT, RIGHT, REPORT, QUIT;

    private final static Set<String> values = new HashSet<String>(CommandNameEnum.values().length);

    static {
        for (CommandNameEnum d : CommandNameEnum.values()) {
            values.add(d.name());
        }
    }

    public static boolean contains(String value) {
        return values.contains(value);
    }
}
