package xyz.luan.console.parser.actions.parser;

import xyz.luan.console.parser.actions.InvalidParameter;

public interface CustomParser<T> {
    T parse(String s) throws InvalidParameter;
}