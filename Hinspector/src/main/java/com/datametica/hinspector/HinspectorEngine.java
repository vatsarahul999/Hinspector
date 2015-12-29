package com.datametica.hinspector;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.datametica.hinspector.checker.Checker;
import com.datametica.hinspector.loader.GrammerLoader;
import com.datametica.hinspector.report.InspectionReport;

public class HinspectorEngine {

	private List<Checker> checkers;

	public HinspectorEngine() {

		checkers = GrammerLoader.getInstance().getAllCheckers();
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		HinspectorEngine engine = new HinspectorEngine();
		String tem = args[0].substring(args[0].length() - 4, args[0].length());

		if (!tem.equals(".pig")) {
			System.out.println("Not a valid pig file");

			System.exit(0);
		}
		String output = engine.execute(args[0]);
		Date dt = new Date();
		String[] t = args[0].split("/");
		String temp = t[t.length - 1];
		String fname = dt.toString() + temp.substring(0, temp.length() - 5);
		StringBuffer sb = new StringBuffer();

		sb.append("Analysis for file ").append(args[0]).append(" on ").append(dt.toString() + "\n");
		for (int i = 0; i < 100; i++) {
			if (i == 0)
				sb.append("+");
			else if (i == 99)
				sb.append("+");
			else
				sb.append("-");
		}
		sb.append("\n| WARNINGS");
		for (int i = 0; i < 89; i++) {
			sb.append(" ");
		}
		sb.append("|\n");
		for (int i = 0; i < 100; i++) {
			if (i == 0)
				sb.append("+");
			else if (i == 99)
				sb.append("+");
			else
				sb.append("-");
		}
		sb.append("\n" + output + "\n");
		for (int i = 0; i < 100; i++) {
			sb.append("-");
		}
		String fistr = sb.toString();
		BufferedWriter writer = new BufferedWriter(new FileWriter(args[1] + fname + ".txt"));
		try {
			writer.write(fistr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			writer.close();
		}
		/*
		 * for (int i = 0; i < 100; i++) { System.out.print("-"); }
		 * System.out.println(); System.out.print("|  WARNINGS"); for (int i =
		 * 0; i < 89; i++) { System.out.print(" "); } System.out.print("|");
		 * 
		 * System.out.println(); for (int i = 0; i < 100; i++) {
		 * System.out.print("-"); } System.out.println();
		 * System.out.println(output); for (int i = 0; i < 100; i++) {
		 * System.out.print("-"); }
		 */
		System.out.println("Done");

	}

	private String execute(String pigFile) {
		String fileConents = readFile(pigFile);
		StringBuilder sb = new StringBuilder();

		for (Checker checker : checkers) {
			InspectionReport inspectionReport = checker.apply(fileConents);
			if (!inspectionReport.getReport().equals("")) {
				sb.append(inspectionReport.getReport() + "\r\n");

			}
		}
		return sb.toString();

	}

	private static String readFile(String pigFilePath) {
		// TODO Auto-generated method stub
		Path path = FileSystems.getDefault().getPath(pigFilePath);
		StringBuffer contents = new StringBuffer();
		int lineCounter = 1;
		List<String> list = new ArrayList<String>();
		try {
			list = Files.readAllLines(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String l : list) {
			l.replaceAll("\n", " ");
			contents.append(l);
			contents.append("##\n" + String.format("%09d", lineCounter) + "##\n");
			lineCounter++;
		}

		return contents.toString();
	}

}
