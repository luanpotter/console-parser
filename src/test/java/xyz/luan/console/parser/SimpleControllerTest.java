package xyz.luan.console.parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import xyz.luan.console.parser.actions.InvalidAction;
import xyz.luan.console.parser.actions.InvalidHandler;

public class SimpleControllerTest {

	private TestApplication application;
	
	@Before
	public void setup() throws InvalidAction, InvalidHandler {
		application = Setup.setupSimpleControllerOnly();
	}
	
	private String[] build(String... args) {
		String[] response = new String[args.length + 2];
		response[0] = "m:" + application.startMessage();
		System.arraycopy(args, 0, response, 1, args.length);
		response[response.length - 1] = "m:" + application.endMessage();
		return response;
	}
	
	@Test
	public void testSimpleActionViaArgs() {
		application.run(new String[] { "simple", "greet", "Luan" });

		final String[] expected = { "r:Hello, Luan" };
		Assert.assertArrayEquals(expected, application.getConsole().results());
	}

	@Test
	public void testSimpleActionInteractiveShell() {
		application.getConsole().push("simple greet Karl").push("");
		application.run();

		final String[] expected = build("r:Hello, Karl");
		Assert.assertArrayEquals(expected, application.getConsole().results());
	}

	@Test
	public void testActionThatFails() {
		application.getConsole().push("simple fail").push("");
		application.run();

		final String[] expected = build("e:I shall fail!");
		Assert.assertArrayEquals(expected, application.getConsole().results());
	}

	@Test @Ignore
	public void testIntegerParameter() {
		application.getConsole().push("simple eval age 1990").push("");
		application.run();

		final String[] expected = build("e:I shall fail!");
		Assert.assertArrayEquals(expected, application.getConsole().results());
	}

}
