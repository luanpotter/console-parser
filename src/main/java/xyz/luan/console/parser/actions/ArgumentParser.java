package xyz.luan.console.parser.actions;

import java.util.HashMap;
import java.util.Map;

public class ArgumentParser {

    public static final Map<Class<?>, Parser<?>> parsers;
    static {
        parsers = new HashMap<>();

        parsers.put(String.class, new Parser<String>() {
            @Override
            public String parse(String s) {
                return s;
            }
        });
        parsers.put(Integer.class, new Parser<Integer>() {
            @Override
            public Integer parse(String s) throws InvalidParameter {
                try {
                    return Integer.parseInt(s);
                } catch (NumberFormatException ex) {
                    throw new InvalidParameter(Integer.class, s, ex);
                }
            }
        });
    }
    
    public interface Parser<T> {
        T parse(String s) throws InvalidParameter;
    }
    
    public static boolean hasParser(Class<?> c) {
        return getParser(c) != null;
    }
    
    public static Object parse(String value) throws InvalidParameter {
        Parser<?> parser = getParser(value.getClass());
        return parser.parse(value);
    }

    private static Parser<?> getParser(Class<?> originalClass) {
        Class<?> clazz = originalClass;
        Parser<?> parser = parsers.get(clazz);
        while (parser == null) {
            parser = parsers.get(clazz = clazz.getSuperclass());
            if (clazz.equals(Object.class)) {
                break;
            }
        }
        if (parser == null) {
            Class<?>[] its = originalClass.getClass().getInterfaces();
            for (Class<?> it : its) {
                parser = parsers.get(it);
                if (parser != null) {
                    break;
                }
            }
        }
        return parser;
    }
}
