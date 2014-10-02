package xyz.luan.console.parser.util;

import java.util.Map;

public class ClassMap {

	public static <T> T getFromClassMap(Map<? extends Class<?>, T> map, Class<?> originalClass) {
		if (originalClass.isPrimitive()) {
			throw new RuntimeException("Primitives not supported as actions and handlers arguments!");
		}
        Class<?> clazz = originalClass;
        T t = map.get(clazz);
        while (t == null) {
            t = map.get(clazz = clazz.getSuperclass());
            if (clazz.equals(Object.class)) {
                break;
            }
        }
        if (t == null) {
            Class<?>[] its = originalClass.getClass().getInterfaces();
            for (Class<?> it : its) {
                t = map.get(it);
                if (t != null) {
                    break;
                }
            }
        }
        return t;
    }
}
