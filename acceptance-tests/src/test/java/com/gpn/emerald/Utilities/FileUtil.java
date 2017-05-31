/********************************************************************************************************
 * Copyright (c) 2017 Global Payments
 * All rights reserved. 
 ********************************************************************************************************/

package com.grp.sample.Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * The FileUtil method provides method to read various type of file and perform matching operations
 */
public class FileUtil {

	/**
	 * To read files available in the classpath
	 * 
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String getFile(String filePath) throws FileNotFoundException {

		StringBuilder result = new StringBuilder("");

		// Get file from resources folder
		File file = new File(FileUtil.class.getClassLoader().getResource(filePath).getFile());

		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				result.append(scanner.nextLine());
			}
			scanner.close();
		}
		return result.toString();
	}

	/**
	 * To match the content of the file to provided string. Please note the --
	 * All the spaces and newlines will be ignored
	 * 
	 * @param contents
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 */
	public static boolean isMatch(String contents, String filePath) throws FileNotFoundException {
		// Remove all spaces and new line
		String fileContents = getFile(filePath).replaceAll("\\n", "");
		contents = contents.replaceAll("\\n", "");
		return contents.equals(fileContents);
	}

}