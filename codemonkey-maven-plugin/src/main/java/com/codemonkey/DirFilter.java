package com.codemonkey;

import java.io.File;
import java.io.FilenameFilter;

public class DirFilter implements FilenameFilter {

	@Override
	public boolean accept(File file , String name) {
		return file.isDirectory();
	}
}
