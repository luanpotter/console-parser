package xyz.luan.console.parser.actions.parser;

final class StringParser implements CustomParser<String> {
    @Override
    public String parse(String s) {
        return s;
    }
}