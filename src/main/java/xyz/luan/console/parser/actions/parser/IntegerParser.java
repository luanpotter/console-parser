package xyz.luan.console.parser.actions.parser;

import xyz.luan.console.parser.actions.InvalidParameter;

final class IntegerParser implements CustomParser<Integer> {
    @Override
    public Integer parse(String s) throws InvalidParameter {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            throw new InvalidParameter(Integer.class, s, ex);
        }
    }
}