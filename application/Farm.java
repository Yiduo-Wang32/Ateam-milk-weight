package application;

import java.util.Map;
import java.util.TreeMap;


/**
 * This class defines a farm and its constituent milk supplies
 * 
 * @author Kexin Chen
 *
 */
public class Farm implements Comparable<Farm> {
  private String id; // id of this farm
  private TreeMap<Date, Integer> data; // milk data in date-weight pair

  
  /**
   * This constructor creates an empty farm
   */
  public Farm() {
    this.id = "";
    this.data = new TreeMap<Date, Integer>();
  }

  
  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  
  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  
  /**
   * @return the data
   */
  public TreeMap<Date, Integer> getData() {
    return data;
  }

  
  /**
   * @param data the data to set
   */
  public void setData(TreeMap<Date, Integer> data) {
    this.data = data;
  }


  /**
   * Add the milk information for a given date
   * 
   * @param inputDate - a date in string format: "year,month,day"
   * @return true if data is stored successfully, false otherwise
   */
  public boolean insertMilkForDate(String inputDate, String inputWeight) {
    if (inputDate != null && inputWeight != null) {
      try {
        // split input date into year, month, day
        String[] dateArray = inputDate.split(",");
        Integer year = Integer.parseInt(dateArray[0]);
        Integer month = Integer.parseInt(dateArray[1]);
        Integer day = Integer.parseInt(dateArray[2]);
        Integer weightInt = Integer.parseInt(inputWeight);

        Date insertDate = new Date(year, month, day);
        if (this.data != null) {
          this.data.put(insertDate, weightInt);
          return true;
        }
        return false;


      } catch (NumberFormatException e) {
        e.printStackTrace();
        System.out.println("Mis-input string cannot convert to int");
        return false;

      } catch (Exception e) {
        e.printStackTrace();
        return false;

      }

    }
    return false;

  }

  
  /**
   * Edit the milk information for a given date
   * 
   * @param inputDate - a date in string format: "year,month,day"
   * @return true if data is stored successfully, false otherwise
   */
  public boolean editMilkForDate(String inputDate, String inputWeight) {
    if (inputDate != null && inputWeight != null) {

      try {
        // split input date into year, month, day
        String[] dateArray = inputDate.split(",");
        Integer year = Integer.parseInt(dateArray[0]);
        Integer month = Integer.parseInt(dateArray[1]);
        Integer day = Integer.parseInt(dateArray[2]);
        Integer weightInt = Integer.parseInt(inputWeight);

        Date editDate = new Date(year, month, day);

        if (this.data != null) {
          this.data.put(editDate, weightInt);
          return true;
        }
        return false;

      } catch (NumberFormatException e) {
        e.printStackTrace();
        System.out.println("Mis-input string cannot convert to int");
        return false;

      } catch (Exception e) {
        e.printStackTrace();
        return false;

      }
    }
    return false;

  }

  
  /**
   * Remove milk information for a given date. If the date exists in data treemap, it will be
   * removed. If the date doesn't exist, exception won't be thrown.
   * 
   * @param inputDate - a date in string format: "year,month,day"
   * @return true if data is removed successfully, false otherwise
   */
  public boolean removeMilkForDate(String inputDate) {
    if (inputDate != null) {

      try {
        // split input date into year, month, day
        String[] dateArray = inputDate.split(",");
        Integer year = Integer.parseInt(dateArray[0]);
        Integer month = Integer.parseInt(dateArray[1]);
        Integer day = Integer.parseInt(dateArray[2]);

        Date removeDate = new Date(year, month, day);
        if (this.data != null) {
          this.data.remove(removeDate);
          return true;
        }
        return false;

      } catch (NumberFormatException e) {
        e.printStackTrace();
        System.out.println("Mis-input string cannot convert to int");
        return false;

      } catch (Exception e) {
        e.printStackTrace();
        return false;

      }
    }
    return false;
  }


  /**
   * Clear all the data for this farm
   */
  public void clearData() {
    this.data.clear();

  }

  
  /**
   * Get the total milk weight in a month for this farm given a year and month
   * 
   * @param year  - a specified year to get milk weight
   * @param month - a specified month in that year to get milk weight
   * @return total milk weight
   */
  public int getTotalWeightOfMonth(int year, int month) {
    int totalWeight = 0;
    for (Map.Entry<Date, Integer> data : this.data.entrySet()) {
      Date keyDate = data.getKey();

      // if the current year is larger than given year, break the loop
      if (keyDate.getYear() > year) {
        break;
      }

      if (keyDate.getYear() == year) {
        if (keyDate.getMonth() == month) {
          totalWeight += data.getValue();
        }
      }
    }
    return totalWeight;
  }


  /**
   * Get the total weight in a given year for this farm
   * 
   * @param year - a specified year to get milk weight
   * @return total milk weight
   */
  public int getTotalWeightOfYear(int year) {
    int totalWeight = 0;
    for (Map.Entry<Date, Integer> data : this.data.entrySet()) {
      Date keyDate = data.getKey();
      // if the current year is larger than given year, break the loop
      if (keyDate.getYear() > year) {
        break;
      }
      if (keyDate.getYear() == year) {
        totalWeight += data.getValue();

      }
    }

    return totalWeight;
  }


  /**
   * Compare the farm based on id
   * 
   * @param f - farm to compare
   * @return positive if this farm id is larger
   */
  public int compareTo(Farm f) {
    return this.id.compareTo(f.id);
  }
}
