package com.datametica.hinspector.report;

public class IncidentAnalyser implements InspectionReport {
	String report;
	boolean valid;
	String rule;
	String file;
	String ruleName;
	int lineNumber;
	String locationOfError;

	public IncidentAnalyser(boolean b, String rule, String fileConents, String ruleName) {

		this.valid = b;
		this.rule = rule;
		this.file = fileConents;
		this.ruleName = ruleName;
	}

	public IncidentAnalyser(boolean find, String rule2, String fileConents, String ruleName2, int lineNumber,
			String string) {

		this.valid = find;
		this.rule = rule2;
		this.file = fileConents;
		this.ruleName = ruleName2;
		this.lineNumber = lineNumber;
		this.locationOfError = string;
	}

	@Override
	public String getReport() {

		StringBuffer sb = new StringBuffer("");
		if (valid) {
			if (!locationOfError.contains("/*") && !locationOfError.contains("*/"))
				sb.append("\nThere is a failure to this rule : ").append(this.ruleName).append(" at Line Number ")
						.append(this.lineNumber).append(".\n The rule broken in file at .... \n")
						.append(locationOfError + "\n");
			this.report = sb.toString();
		} else {
			this.report = "";
		}
		return this.report;
	}

}
