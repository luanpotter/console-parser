package xyz.luan.console.parser;

import xyz.luan.console.parser.call.CallResult;

final class TestApplication extends Application {
	private final TestConsole console;
	private final Context context;

	public TestConsole getConsole() {
		return this.console;
	}
	
	public TestApplication(TestConsole console, Context context) {
		this.console = console;
		this.context = context;
	}

	@Override
	public Console createConsole() {
		return console;
	}

	@Override
	public Context createContext() {
		return context;
	}

	@Override
	public String startMessage() {
		return "Heyy";
	}

	@Override
	public String endMessage() {
		return "Bye then";
	}

	@Override
	public CallResult emptyLineHandler() {
		return CallResult.QUIT;
	}

	public void run() {
		run(new String[] {});
	}
}