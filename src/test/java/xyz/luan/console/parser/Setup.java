package xyz.luan.console.parser;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import xyz.luan.console.parser.actions.ArgumentParser;
import xyz.luan.console.parser.actions.ArgumentParser.CustomParser;
import xyz.luan.console.parser.actions.InvalidAction;
import xyz.luan.console.parser.actions.InvalidHandler;
import xyz.luan.console.parser.actions.InvalidParameter;
import xyz.luan.console.parser.call.Caller;
import xyz.luan.console.parser.callable.Callable;
import xyz.luan.console.parser.controller.SimpleController;

public final class Setup {

	private Setup() {
		throw new RuntimeException("Should not be instanciated.");
	}
	
	public static TestApplication setupSimpleControllerOnly(boolean enableDefaultAliases) throws InvalidAction, InvalidHandler {
		ArgumentParser.parsers.put(java.awt.Color.class, new CustomParser<java.awt.Color>() {
			@Override
			public Color parse(String s) throws InvalidParameter {
				String[] components = s.substring(1, s.length() - 1).split(",");
				return new Color(getComponent(components[0]), getComponent(components[1]), getComponent(components[2]));
			}

			private int getComponent(String value) {
				return Integer.parseInt(value.trim());
			}
		});

		final TestConsole console = new TestConsole();
		final Context context = new Context();

		Parser parser = defaultParser(enableDefaultAliases);
		Caller caller = defaultCaller(context, console);

		context.setup(parser, caller);
		return new TestApplication(console, context);
	}
	
    public static Parser defaultParser(boolean enableDefaultAliases) {
        return new Parser(enableDefaultAliases, enableDefaultAliases ? new HashMap<>() : allAliases(), defaultCallables());
    }

    public static Caller defaultCaller(Context context, Console console) throws InvalidAction, InvalidHandler {
        Caller caller = new Caller();
        caller.registerClass("simple", new SimpleController().setup(context, console));
        return caller;
    }

    private static ArrayList<Callable> defaultCallables() {
        ArrayList<Callable> callables = new ArrayList<>();
        SimpleController.defaultCallables("simple", callables);
        return callables;
    }

    private static Map<String, String> allAliases() {
        Map<String, String> aliases = new HashMap<>();

        //TODO add simple more in which every alias is mapped to it's String representation
        aliases.put("simple", ":simple");
        aliases.put("greet", ":greet");
        aliases.put("fail", ":fail");
        aliases.put("eval", ":eval");
        aliases.put("age", ":age");
        aliases.put("hex", ":toHex");

        return aliases;
    }
}
