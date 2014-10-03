package xyz.luan.console.parser.controller;

import java.awt.Color;
import java.util.Calendar;
import java.util.List;

import xyz.luan.console.parser.Context;
import xyz.luan.console.parser.Controller;
import xyz.luan.console.parser.ExceptionHandler;
import xyz.luan.console.parser.actions.Action;
import xyz.luan.console.parser.actions.ActionRef;
import xyz.luan.console.parser.actions.Arg;
import xyz.luan.console.parser.call.CallResult;
import xyz.luan.console.parser.callable.ActionCall;
import xyz.luan.console.parser.callable.Callable;
import xyz.luan.console.parser.callable.Pattern;

public class SimpleController extends Controller<Context> {

	@Action("greet")
	public CallResult greet(@Arg("name") String name) {
		console.result("Hello, " + name);
		return CallResult.SUCCESS;
	}
	
	@Action("fail")
	public CallResult fail() {
		console.error("I shall fail!");
		return CallResult.ERROR;
	}
	
	@Action("age")
	public CallResult age(@Arg("birthYear") Integer birthYear) throws TimeTravelNotAllowed {
		int age = Calendar.getInstance().get(Calendar.YEAR) - birthYear;
		if (age < 0)
			throw new TimeTravelNotAllowed();
		console.result("You should be about " + age + " years old.");
		return CallResult.SUCCESS;
	}
	
	@Action("hexValue")
	public CallResult hexValue(@Arg("color") Color color) {
		String hex = getHex(color.getRed()) + getHex(color.getGreen()) + getHex(color.getBlue());
		console.result(hex);
		return CallResult.SUCCESS;
	}

	private String getHex(int component) {
		String s = Integer.toString(component, 16);
		return s.length() == 1 ? "0" + s : s;
	}
	
	public class TimeTravelNotAllowed extends Exception {
		private static final long serialVersionUID = 7763258131330752928L;
	}
	
	@ExceptionHandler(TimeTravelNotAllowed.class)
	public CallResult handleTimeTravelException(TimeTravelNotAllowed ex) {
		console.error("Sorry, our program does not allow time travelers... Come back again after you are born.");
		return CallResult.ERROR;
	}
	
	public static void defaultCallables(String name, List<Callable> callables) {
        callables.add(new ActionCall(name + ":greet", ":simple :greet name", "Greet someone"));
        callables.add(new ActionCall(name + ":fail", ":simple :fail", "Simply fail"));
        callables.add(new ActionCall(name + ":age", ":simple :eval :age birthYear", "Calculate your age"));
        callables.add(new ActionCall(new ActionRef(name, "hexValue"), new Pattern(":simple :toHex color", true), "Calculate a color's hex value"));
    }
}
