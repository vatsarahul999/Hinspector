package com.datametica.hinspector.loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.datametica.hinspector.checker.Checker;
import com.datametica.hinspector.checker.CompleteChecker;

public class GrammerLoader implements HinspectionLoader {

	static String dir = System.getProperty("user.dir");
	private static final GrammerLoader INSTANCE = new GrammerLoader(dir + "/resources/Parser_Rules.Hin");
	private List<String> rules;
	private List<Checker> checkers;
	private List<String> nameOfRules;
	private List<String> lineReader;

	public List<String> getNameOfRules() {
		return nameOfRules;
	}

	private GrammerLoader(String hinFileLocation) {
		this.lineReader = new ArrayList<String>();
		this.rules = new ArrayList<String>();
		this.nameOfRules = new ArrayList<String>();
		try {
			lineReader = Files.readAllLines(Paths.get(hinFileLocation));

			for (String str : lineReader) {
				if (str.contains("#")) {
					String[] g = str.split("#");

					this.rules.add(g[0]);
					this.nameOfRules.add(g[1]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File Not Found");
		}
		this.checkers = buildCheckers();
	}

	public static GrammerLoader getInstance() {
		return INSTANCE;
	}

	private List<Checker> buildCheckers() {
		List<Checker> list = new ArrayList<Checker>();
		for (String str : lineReader) {
			if (str.contains("#")) {
				String[] g = str.split("#");
				Checker chk = new CompleteChecker(g[0], g[1]);
				list.add(chk);
			}
		}

		return list;
	}

	public List<Checker> getAllCheckers() {
		return checkers;
	}

}
