package io.github.mjcro.pill;

import java.util.function.Predicate;

/**
 * Predicate returning true if expected class can be loaded.
 */
public class IsClassPresentPredicate implements Predicate<String> {
    public static final IsClassPresentPredicate INSTANCE = new IsClassPresentPredicate();

    @Override
    public boolean test(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        try {
            Class.forName(s, false, getClass().getClassLoader());
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
