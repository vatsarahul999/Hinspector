package com.datametica.hinspector.checker;

import com.datametica.hinspector.report.InspectionReport;

public interface Checker {

	InspectionReport apply(String fileConents);

}
