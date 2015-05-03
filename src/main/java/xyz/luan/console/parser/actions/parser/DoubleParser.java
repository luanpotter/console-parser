package xyz.luan.console.parser.actions.parser;

import xyz.luan.console.parser.actions.InvalidParameter;

final class DoubleParser implements CustomParser<Double> {
    @Override
    public Double parse(String s) throws InvalidParameter {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            throw new InvalidParameter(Double.class, s, ex);
        }
    }
}
