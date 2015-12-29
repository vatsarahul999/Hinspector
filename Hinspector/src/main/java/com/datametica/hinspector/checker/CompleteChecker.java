package com.datametica.hinspector.checker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.datametica.hinspector.report.IncidentAnalyser;
import com.datametica.hinspector.report.InspectionReport;

public class CompleteChecker implements Checker {
	String rule;
	String ruleName;

	public CompleteChecker(String str, String str1) {
		this.rule = str;
		this.ruleName = str1;
	}

	@Override
	public InspectionReport apply(String fileConents) {

		Pattern p1 = Pattern.compile(rule);
		// System.out.println(rule);
		Matcher m1 = p1.matcher(fileConents);
		InspectionReport report = null;
		int lineNumber = -1;
		String temp = null;
		String temp1 = null;
		if (m1.find()) {
			if (fileConents.charAt(m1.end() + 1) == '\n') {
				temp = fileConents.substring(m1.end() - 2, fileConents.length());
				System.out.println("in if");
			} else
				temp = fileConents.substring(m1.end(), fileConents.length());
			String[] g = temp.split("##\\n");
			lineNumber = Integer.parseInt(g[1]);

			temp1 = fileConents.substring(0, m1.start());
			String[] g1 = temp1.split("##\\n");
			String beg = g1[g1.length - 1];

			String temp2 = fileConents.substring(m1.end(), fileConents.length());
			String[] g2 = temp2.split("##\\n");
			String end = g2[0];

			beg = beg.replaceAll("[\\d][\\d][\\d][\\d][\\d][\\d][\\d][\\d][\\d]##", "");
			end = end.replaceAll("[\\d][\\d][\\d][\\d][\\d][\\d][\\d][\\d][\\d]##", "");
			beg = beg.replaceAll("[\\d][\\d][\\d][\\d][\\d][\\d][\\d][\\d][\\d]", "");
			String temp3 = fileConents.substring(m1.start(), m1.end());
			temp3 = temp3.replaceAll("##\\n[\\d][\\d][\\d][\\d][\\d][\\d][\\d][\\d][\\d]##", "");
			String errorline = beg + temp3 + end;

			// if(beg.contains(sq))
			// System.out.println(beg+" >> beg\n"+temp3+" >>main error\n"+end+"
			// >>end\n");

			report = new IncidentAnalyser(true, rule, fileConents, ruleName, lineNumber, errorline);

		} else {
			report = new IncidentAnalyser(m1.find(), rule, fileConents, ruleName);
		}
		return report;
	}

}
