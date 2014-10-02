package xyz.luan.console.parser;

import java.util.ArrayList;
import java.util.List;

public class TestConsole implements Console {

	private List<String> messages = new ArrayList<>();
	private List<String> readList = new ArrayList<>();

	public TestConsole push(String t) {
		readList.add(t);
		return this;
	}
	
	public String[] results() {
		return messages.toArray(new String[messages.size()]);
	}
	
	@Override
	public void message(Object m) {
		messages.add("m:" + m);
	}

	@Override
	public void result(Object m) {
		messages.add("r:" + m);
	}

	@Override
	public void error(Object m) {
		messages.add("e:" + m);
	}

	@Override
	public String read() {
		return readList.remove(0);
	}

	@Override
	public char[] readPassword() {
		return readList.remove(0).toCharArray();
	}

	@Override
	public void exit() {
	}
}
