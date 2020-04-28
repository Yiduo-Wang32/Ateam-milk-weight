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
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * FileManager - TODO Describe purpose of this user-defined type
 * 
 * @author Yiduo Wang (2020)
 *
 */
public class FileManager {
	public String inputFile; // path for input file
	public String outputFile; // path for output file

	/**
	 * Set up a FileManager class with the path for input and output file
	 * 
	 * @param inputFile  - path for input file
	 * @param outputFile - path for output file
	 */
	public FileManager(String inputFile, String outputFile) {
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}

	/**
	 * Constructor for only setting up with input file
	 * 
	 * @param inputFile - path for input file
	 */
	public FileManager(String inputFile) {
		this(inputFile, null);
	}

	/**
	 * No-argument constructor
	 */
	public FileManager() {
		this(null, null);
	}

	/**
	 * Read the input file and store the data into a tree map in the correct format.
	 * Line with wrongly formatted data will be ignored
	 * 
	 * @param farms - the tree map to write in
	 * @return - true if file is successfully read, false otherwise
	 */
	public boolean readFile(TreeMap<String, Farm> farms) {
		// use a stream to read the file line by line
		try (Stream<String> lineStream = Files.lines(Paths.get(inputFile))) {
			lineStream.map(line -> line.split(",")).filter(arr -> arr.length == 3).forEach(arr -> {
				// store the formatted data to the tree
				Farm curFarm = farms.get(arr[1]);
				if (curFarm == null) {
					curFarm = new Farm();
					curFarm.setId(arr[1]);
					if (curFarm.insertMilkForDate(arr[0], arr[2])) farms.put(arr[1], curFarm);
				} else curFarm.insertMilkForDate(arr[0], arr[2]);
			});
			return true;
		} catch (IOException e) {
			// prompt if file name is invalid and return false
			System.out.println("Invalid input file name");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Write the data from a given treeMap to the output file, in csv format
	 * 
	 * @param farms - treemap that stores the data
	 * @return - true if the data is successfully written into the file, false
	 *         otherwise
	 */
	public boolean writeToFile(TreeMap<String, Farm> farms) {
		try (PrintWriter writer = new PrintWriter(new File(outputFile))) {
			writer.println("date,farm_id,weight"); // write header

			// use a stream to get data stored in the tree
			farms.entrySet().stream().map(entry -> entry.getValue()).forEach(farm -> {
				String name = farm.getId();
				farm.getData().entrySet().stream()
						// format them into string for output
						.map(entry -> String.format("%s,%s,%d", entry.getKey().toString(), name,
								entry.getValue()))
						// write the string into output file
						.forEach(str -> writer.println(str));
			});
			return true;
		} catch (IOException e) { // prompt if file name is invalid and return false
			System.out.println("Invalid output file name");
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * write data from a given arrayList to the output file, in csv format
	 * 
	 * @param - farms ArrayList for storing the data
	 * @return - true if data is successfully written into the output file, false
	 *         otherwise
	 */
	public boolean writeToFile(ArrayList<TreeMap<String, Data>> farms) {
		try (PrintWriter writer = new PrintWriter(outputFile)) {
			writer.println("date,farm_id,weight"); // print header

			// setup a stream to process the data stored in array list
			farms.stream().forEach(tree -> {
				// setup another stream for processing the in the treeMap
				tree.entrySet().stream()
						// format data into string for output
						.map(entry -> String.format("%s,%s,%s",
								entry.getValue().getDate().toString(), entry.getKey(),
								entry.getValue().getWeight()))
						// write the string into the file
						.forEach(str -> writer.println(str));
			});
			return true;
		} catch (FileNotFoundException e) { // prompt if file name is invalid and return false
			System.out.println("Invalid output file name");
			e.printStackTrace();
			return false;
		}
	}

}
