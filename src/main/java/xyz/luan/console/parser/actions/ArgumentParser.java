package xyz.luan.console.parser.actions;

import java.util.HashMap;
import java.util.Map;

import xyz.luan.console.parser.util.ClassMap;

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
    	return ClassMap.getFromClassMap(parsers, originalClass);
    }
}
