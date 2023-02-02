import java.io.*;
import java.util.*;

public class Terminal {
	private File curDir, defaultDir;
	private Parser parser;
	private String[] argument;
	private String commandName = "";

	Terminal() {
		File path = new File("");
		defaultDir = new File(path.getAbsolutePath());
		curDir = defaultDir;
		parser = null;
	}

	public void setParser(Parser p) {
		parser = p;
	}

	public void echo(String[] input) {
		String res = "";
		argument = parser.getArgs();

		for (int i = 1; i < parser.getArgs().length; i++) {
			res = res.concat(argument[i] + " ");
		}

		res = res.substring(0, res.length() - 1);

		System.out.println(res);
	}

	public void pwd() {
		System.out.println(curDir.getAbsolutePath());

	}

	public void cd(String path) {
		if (path.equals("~"))
			curDir = defaultDir;
		else if (path.equals(".."))
			curDir = curDir.getParentFile();
		else
			curDir = new File(path);
	}

	public void ls() {
		String arr[] = curDir.list();
		for (String str : arr)
			System.out.println(str);
	}

	public void lsr() {
		String arr[] = curDir.list();
		Collections.reverse(Arrays.asList(arr));
		for (String str : arr) {
			System.out.println(str);
		}
	};

	public void mkdir(String name) {
		File file = new File(name);
		file.mkdir();
	}

	public void rmdir(String name) {
		File file = new File(name);
		file.delete();
	}

	public void rmdirAll(File path) {
		path = curDir;
		File[] allContents = path.listFiles();
		for (File file : allContents) {
			rmdirAll(file);
		}
		path.delete();

	}

	public void touch(String name) {
		File file = new File(name);
		try {
			file.createNewFile();
		} catch (IOException e) {
			System.out.println("Wrong path.");
		}

	};

	public void cp() {
	};

	public void cpr() {

	}

	public void rm(String name) {
		File file = new File(name);
		file.delete();
	}

	public void cat(String filePath) {
		try {
			File file = new File(filePath);
			Scanner in = new Scanner(file);
			while (in.hasNextLine())
				System.out.println(in.nextLine());
			in.close();
		} catch (Exception e) {
			System.out.println("Error");
		}
	}

	public void args() {
		System.out.println("pwd");
		System.out.println("echo");
		System.out.println("cd ~");
		System.out.println("cd");
		System.out.println("ls");
		System.out.println("ls-r");
		System.out.println("mkdir");
		System.out.println("rmdir");
		System.out.println("touch");
		System.out.println("cp");
		System.out.println("cp-r");
		System.out.println("rm");
		System.out.println("cat");
		System.out.println("args");
		System.out.println("help");
		System.out.println("clear");
		System.out.println("exit");
	}

	public void help() {
		System.out.println("pwd - Shows the current path.");
		System.out.println("echo [args] - Prints its arguments.");
		System.out.println("cd ~ - Change the directory to home.");
		System.out.println(
				"cd [args] - If given argument (..) changes the directory to the privous one, if given a directory name it changes the path to this directory.");
		System.out.println("ls - List the files in the current directory");
		System.out.println("ls -r - List the files in the current diretory in reverse.");
		System.out.println("mkdir [args] - Creates new directory");
		System.out.println(
				"rmdir [ args] - If given argument (*) it removes all empty directories, if given a directory name it removes this directory.");
		System.out.println("touch [args] - Creates a file in the current directory.");
		System.out.println("cp [args] - Copies two files the first is copied onto the second file.");
		System.out.println("cp -r [args] - Copies two directories the first is copied onto the second directory.");
		System.out.println("rm [ args] - Removes a file.");
		System.out.println(
				"cat [ args] - If given one argument which is a file name it prints the contents of this file and if given two arguments it prints the content of each file.");
		System.out.println("clear - Clears terminal.");
		System.out.println("args - Shows the command list.");
		System.out.println("exit - Close terminal.");
	}

	public void clear() {
		for (int i = 0; i < 50; i++)
			System.out.println();
	}

	public void chooseCommandAction() {

		commandName = parser.getCommandName();
		argument = parser.getArgs();

		if (commandName.equals("echo"))
			echo(argument);

		else if (commandName.equals("pwd"))
			pwd();

		else if (commandName.equals("exit"))
			System.exit(0);

		else if (commandName.equals("cd")) {
			if (argument[1] == "~")
				cd("~");
			else if (argument[1] == "..")
				cd("..");
			else
				cd(argument[1]);
		}

		else if (commandName.equals("mkdir")) {
			mkdir(argument[1]);
		}

		else if (commandName.equals("rmdir")) {
			if (argument[1] == "*")
				rmdirAll(curDir);
			else
				rmdir(argument[1]);
		}

		else if (commandName.equals("touch")) {
			touch(argument[1]);
		}

		else if (commandName.equals("rm")) {
			rm(argument[1]);
		}

		else if (commandName.equals("cat")) {
			cat(argument[1]);
		}

		else if (commandName.equals("ls")) {
			ls();
		}

		else if (commandName.equals("ls-r")) {
			lsr();
		}

		else if (commandName.equals("cp")) {
			cp();
		}

		else if (commandName.equals("args"))
			args();

		else if (commandName.equals("help"))
			help();

		else if (commandName.equals("clear"))
			clear();

		else
			System.out.println("Wrong command");
	};

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		String cmd;
		boolean flag = true;

		Terminal t = new Terminal();
		Parser p = new Parser();

		while (flag) {
			System.out.print("Command: ");
			cmd = scanner.nextLine();
			p.parse(cmd);
			t.setParser(p);
			t.chooseCommandAction();

		}

	}

	public static class Parser {

		private String commandName;
		private String[] args;

		public boolean parse(String input) {
			args = input.split(" ");
			boolean flag = true;
			if (args == null)
				commandName = input;
			else {
				String command = args[0];
				for (String i : args) {
					if (i.equals(command)) {
						flag = true;
						break;
					}
				}
				commandName = command;

			}

			return flag;
		};

		public String getCommandName() {
			return commandName;
		};

		public String[] getArgs() {
			return args;
		};
	}

}
