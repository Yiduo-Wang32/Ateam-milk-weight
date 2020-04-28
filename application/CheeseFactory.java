package application;

import java.util.HashMap;
import java.util.TreeMap;


/**
 * This class defines a factory that gets its supplies from multiple farms each year
 * 
 * @author Kexin Chen
 *
 */
public class CheeseFactory {
  private String name; // name of the cheese factory
  private TreeMap<String, Farm> milkDataFromFarms; // store farm id as key, Farm instance as value


  /**
   * This constructor creates an empty cheese factory
   */
  public CheeseFactory() {
    name = "";
    milkDataFromFarms = new TreeMap<String, Farm>();
  }


  /**
   * Return a Farm instance given the farm id. Return null if cheese factory has no data or does not
   * contain this farm
   * 
   */
  public Farm getFarmFromID(String id) {
    if (milkDataFromFarms != null) {
      return milkDataFromFarms.get(id);
    }
    // return null if cheese factory has no data
    return null;
  }


  /**
   * Add the milk information for a given farm and given date
   * 
   * @param id          - id of the farm to insert data
   * @param inputDate   - date to insert
   * @param inputWeight - weight to insert
   * @return true if data is stored successfully, false otherwise
   */
  public boolean insertSingleData(String id, String inputDate, String inputWeight) {
    boolean insert = false; // if insert is successful

    if (id != null && inputDate != null && inputWeight != null) {
      Farm farmToInsert = getFarmFromID(id);

      // if cheese factory contains this farm
      if (farmToInsert != null) {
        insert = farmToInsert.insertMilkForDate(inputDate, inputWeight);
      }
    }


    return insert;
  }


  /**
   * Edit the milk information for a given farm and given date
   * 
   * @param id          - id of the farm to edit data
   * @param inputDate   - date to edit
   * @param inputWeight - weight to edit
   * @return true if data is stored successfully, false otherwise
   */
  public boolean editSingleData(String id, String inputDate, String inputWeight) {
    boolean edit = false; // if edit if successful

    if (id != null && inputDate != null && inputWeight != null) {
      Farm farmToEdit = getFarmFromID(id);

      // if cheese factory contains this farm
      if (farmToEdit != null) {
        edit = farmToEdit.editMilkForDate(inputDate, inputWeight);
      }
    }

    return edit;
  }


  /**
   * Remove the milk information for a given farm and given date
   * 
   * @param id        - id of the farm to remove data
   * @param inputDate - date to remove
   * @return true if data is removed successfully, false otherwise
   */
  public boolean removeSingleData(String id, String inputDate) {
    boolean remove = false; // if remove if successful

    if (id != null && inputDate != null) {
      Farm farmToRemove = getFarmFromID(id);

      // if cheese factory contains this farm
      if (farmToRemove != null) {
        remove = farmToRemove.removeMilkForDate(inputDate);
      }
    }

    return remove;
  }


  /**
   * Get the total weight in a given year for all farms
   * 
   * @param year - year to get the weight from
   * @return total weight for all farm in this year
   */
  public int getTotalWeightOfYear(int year) {
    int totalWeight = 0;
    for (Farm f : this.milkDataFromFarms.values()) {
      totalWeight += f.getTotalWeightOfYear(year);
    }

    return totalWeight;

  }

  
  /**
   * Get the total weight in a given month and year for all farms
   * 
   * @param year  - year to get the weight from
   * @param month - month to get the weight from
   * @return total weight for all farm in this month
   */
  public int getTotalWeightOfMonth(int year, int month) {
    int totalWeight = 0;
    for (Farm f : this.milkDataFromFarms.values()) {
      totalWeight += f.getTotalWeightOfMonth(year, month);
    }

    return totalWeight;

  }


  /**
   * Insert all the date from file to cheese factory
   * 
   * @param file - a instance of FileManager that reads the file
   * @return true if data is stored successfully
   */
  public boolean insertData(FileManager file) {
    return file.readFile(this.milkDataFromFarms);
  }
}
