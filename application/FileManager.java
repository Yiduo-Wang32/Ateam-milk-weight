/**
 * FileManager.java created by Yiduo Wang on HP Envy x360 in Ateam-milk-weight
 *
 * Author:   Yiduo Wang (ywang2292@wisc.edu)
 * Date:     Apr 27, 2020
 * 
 * Course:   CS 400
 * Semester: Spring 2020
 * Lecture:  001
 * 
 * IDE:      Eclipse IDE for Java Developers
 * Version:  2019-12 (4.14.0)
 * Build id: 20191212-1212
 * 
 * Device:   Ethan-hp
 * OS:       Windows 10 Pro
 * Version:  1809
 * OS Build: 17763.973
 * 
 * 
 * List Collaborators:
 * 
 * Other Credits: 
 * 
 * Known Bugs:
 */
package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.DataFormatException;

/**
 * FileManager - TODO Describe purpose of this user-defined type
 * 
 * @author Yiduo Wang (2020)
 *
 */
public class FileManager {
	public String inputFile;
	public String outputFile;

	public FileManager(String inputFile, String outputFile) {
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}

	public FileManager(String inputFile) {
		this(inputFile, null);
	}

	public FileManager() {
		this(null, null);
	}

	public boolean readFile(TreeMap<String, Farm> farms) {
		try (Stream<String> lineStream = Files.lines(Paths.get(inputFile))) {
			lineStream.map(line -> line.split(",")).filter(arr -> arr.length == 3).forEach(arr -> {
				Farm curFarm = farms.get(arr[1]);
				if (curFarm == null) {
					curFarm = new Farm();
					curFarm.setId(arr[1]);
					if (curFarm.insertMilkForDate(arr[0], arr[2])) farms.put(arr[1], curFarm);
				} else curFarm.insertMilkForDate(arr[0], arr[2]);
			});
			return true;
		} catch (IOException e) {
			System.out.println("Invalid input file name");
			e.printStackTrace();
			return false;
		}
	}

	private void writeOneFarm(PrintWriter writer, Farm farm) {
		String name = farm.getId();
		farm.getData().entrySet().stream().map(entry -> String.format("%s,%s,%d",
				entry.getKey().toString(), name, entry.getValue()))
				.forEach(str -> writer.println(str));
	}

	public boolean writeToFile(TreeMap<String, Farm> farms) {
		try (PrintWriter writer = new PrintWriter(new File(outputFile))) {

			farms.entrySet().stream().map(entry -> entry.getValue())
					.forEach(farm -> writeOneFarm(writer, farm));
		} catch (IOException e) {
			System.out.println("Invalid output file name");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean writeToFile(Farm[] farms) {
		return false;
	}

	public String getFileContents() {
		return null;
	}
}
