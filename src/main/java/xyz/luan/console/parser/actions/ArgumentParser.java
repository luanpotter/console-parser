package xyz.luan.console.parser.actions;

import java.util.HashMap;
import java.util.Map;

import xyz.luan.console.parser.util.ClassMap;

public class ArgumentParser {

    public static final Map<Class<?>, CustomParser<?>> parsers;
    static {
        parsers = new HashMap<>();

        parsers.put(String.class, new CustomParser<String>() {
            @Override
            public String parse(String s) {
                return s;
            }
        });
        parsers.put(Integer.class, new CustomParser<Integer>() {
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
    
    public interface CustomParser<T> {
        T parse(String s) throws InvalidParameter;
    }
    
    public static boolean hasParser(Class<?> c) {
        return getParser(c) != null;
    }
    
    public static Object parse(String value, Class<?> expected) throws InvalidParameter {
        CustomParser<?> parser = getParser(expected);
        if (parser == null) {
        	throw new RuntimeException("Argument of type " + expected.getSimpleName() + " has no parser attached to it...");
        }
        return parser.parse(value);
    }

    private static CustomParser<?> getParser(Class<?> originalClass) {
    	return ClassMap.getFromClassMap(parsers, originalClass);
    }
}
