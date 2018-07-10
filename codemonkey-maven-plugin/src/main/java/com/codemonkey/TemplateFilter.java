package com.codemonkey;

import java.io.File;
import java.io.FilenameFilter;

public class TemplateFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		return name.toLowerCase().endsWith(Constant.TEMPLATE_SUBFIX);
	}
}
