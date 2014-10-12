package xyz.luan.console.parser.actions.parser;

import java.util.Arrays;
import java.util.List;

import xyz.luan.console.parser.actions.InvalidParameter;

final class BooleanParser implements CustomParser<Boolean> {
    @Override
    public Boolean parse(String s) throws InvalidParameter {
        final List<String> truthValues = Arrays.asList("t", "true", "yes", "y");
        final List<String> falseValues = Arrays.asList("f", "false", "no", "n");

        String loweredArg = s.toLowerCase();
        if (truthValues.contains(loweredArg)) {
            return true;
        }
        if (falseValues.contains(loweredArg)) {
            return false;
        }
        throw new InvalidParameter(Boolean.class, s);
    }
}