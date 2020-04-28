import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 
 * DataManager - Defines operations on data manipulations and forming the
 * required data for the visualizer.
 * 
 * @author Yumeng Bai & Ruiwen Wang
 *
 */
public class DataManager {

	// initializing fields
	CheeseFactory factory;

	/**
	 * This constructor initialize the factory with the input factory.
	 * 
	 * @param factory
	 */
	public DataManager(CheeseFactory factory) {
		this.factory = factory;
	}

	/**
	 * find the milk data of a certain month
	 * 
	 * @param date - find data based on the specified date
	 * @return sorted milk data of the month
	 */
	private TreeMap<String, Farm> findDataOfMonth(Date date) {
		TreeMap<String, Farm> milkDataFromFarms = factory.getMilkDataFromFarms();
		TreeMap<String, Farm> milkDataOfTheMonth = new TreeMap<String, Farm>();

		// go through the milk data of each farm
		milkDataFromFarms.forEach((s, f) -> {
			Set<Entry<Date, Integer>> dataSetOfFarm = f.getData().entrySet();
			Iterator<Entry<Date, Integer>> itr = dataSetOfFarm.iterator();
			while (itr.hasNext()) {
				Entry<Date, Integer> curr = itr.next();
				if (curr.getKey().getMonth() == date.getMonth()) {
					milkDataOfTheMonth.put(s, f);
				}
			}
		});

		return milkDataOfTheMonth;
	}

	/**
	 * get the average milk data of a certain month
	 * 
	 * @param date - find data based on the specified date
	 * @return Data - average milk data of the month
	 */
	public Data getMonthlyAverage(Date date) {
		TreeMap<String, Farm> milkDataOfTheMonth = findDataOfMonth(date);
		String avg = getAvg(milkDataOfTheMonth);
		return new Data(date, avg);
	}

	/**
	 * get the minimum milk data of this month
	 * 
	 * @param date - find data based on the specified date
	 * @return the minimum milk data (milk weight / contributions)
	 */
	public Data getMonthlyMin(Date date) {
		TreeMap<String, Farm> milkDataOfTheMonth = findDataOfMonth(date);
		return getMin(milkDataOfTheMonth);

	}

	/**
	 * get the maximum milk data of this month
	 * 
	 * @param date - find data based on the specified date
	 * @return the maximum milk data (milk weight / contributions)
	 */
	public Data getMonthlyMax(Date date) {
		TreeMap<String, Farm> milkDataOfTheMonth = findDataOfMonth(date);
		return getMin(milkDataOfTheMonth);
	}

	/**
	 * find the milk data of a certain farm and date
	 * 
	 * @param date   - find data based on the specified date
	 * @param farmID - find data based on the input farmID
	 * @return the milk data of each farm at a certain date
	 */
	private TreeMap<String, Farm> findDataOfFarmOfMont(Date date, String farmID) {
		TreeMap<String, Farm> milkDataFromFarms = factory.getMilkDataFromFarms();
		TreeMap<String, Farm> milkDataOfTheMonth = new TreeMap<String, Farm>();
		Farm farm = milkDataFromFarms.get(farmID);
		Set<Entry<Date, Integer>> dataSetOfFarm = farm.getData().entrySet();

		// go through the milk data of each farm
		Iterator<Entry<Date, Integer>> itr = dataSetOfFarm.iterator();
		TreeMap<Date, Integer> dataOfTheMonth = new TreeMap<Date, Integer>();
		while (itr.hasNext()) {
			Entry<Date, Integer> curr = itr.next();
			if (curr.getKey().getMonth() == date.getMonth()) {
				dataOfTheMonth.put(curr.getKey(), curr.getValue());
			}
		}
		farm.setData(dataOfTheMonth);
		milkDataOfTheMonth.put(farmID, farm);
		return milkDataOfTheMonth;
	}

	/**
	 * find the average milk data of a certain farm and date
	 * 
	 * @param date   - find data based on the specified date
	 * @param farmID - find data based on the input farmID
	 * @return the average milk data of each farm at a certain date
	 */
	public Data getMonthlyAverageForFarm(Date date, String farmID) {
		TreeMap<String, Farm> milkDataOfTheMonth = findDataOfFarmOfMont(date, farmID);
		return new Data(date, getAvg(milkDataOfTheMonth));
	}

	/**
	 * find the minimum milk data of a certain farm and date
	 * 
	 * @param date   - find data based on the specified date
	 * @param farmID - find data based on the input farmID
	 * @return the minimum milk data of each farm at a certain date
	 */
	public Data getMonthlyMinForFarm(Date date, String farmID) {
		TreeMap<String, Farm> milkDataOfTheMonth = findDataOfFarmOfMont(date, farmID);
		return getMin(milkDataOfTheMonth);

	}

	/**
	 * find the maximum milk data of a certain farm and date
	 * 
	 * @param date   - find data based on the specified date
	 * @param farmID - find data based on the input farmID
	 * @return the maximum milk data of each farm at a certain date
	 */
	public Data getMonthlyMaxForFarm(Date date, String farmID) {
		TreeMap<String, Farm> milkDataOfTheMonth = findDataOfFarmOfMont(date, farmID);
		return getMax(milkDataOfTheMonth);
	}

	/**
	 * get data sorted by month from each farm
	 * 
	 * @return sorted data
	 */
	public ArrayList<TreeMap<String, Data>> getDataSortedByField() {
		ArrayList<TreeMap<String, Data>> sortedByMonth = new ArrayList<TreeMap<String, Data>>();
		TreeMap<String, Farm> milkDataFromFarms = factory.getMilkDataFromFarms();
		Set<Entry<String, Farm>> dataSet = milkDataFromFarms.entrySet();
		Iterator<Entry<String, Farm>> itr = dataSet.iterator();

		// go through the milk data of each farm
		while (itr.hasNext()) {
			Entry<String, Farm> curr = itr.next();
			TreeMap<Date, Integer> data = curr.getValue().getData();
			Iterator<Entry<Date, Integer>> dataItr = data.entrySet().iterator();
			while (dataItr.hasNext()) {
				Entry<Date, Integer> currData = dataItr.next();
				int index = currData.getKey().getMonth() - 1;
				sortedByMonth.get(index).put(curr.getKey(),
						new Data(currData.getKey(), currData.getValue().toString()));
			}
		}
		return sortedByMonth;
	}

	/**
	 * get the data in a certain date range
	 * 
	 * @param d1 - start date of the date range
	 * @param d2 - end date of the date range
	 * @return milk data in the certain range
	 */
	private TreeMap<String, Farm> getDataInDateRange(Date d1, Date d2) {
		TreeMap<String, Farm> milkDataFromFarms = factory.getMilkDataFromFarms();
		TreeMap<String, Farm> milkDataInRange = new TreeMap<String, Farm>();

		// go through the milk data of each farm
		milkDataFromFarms.forEach((s, f) -> {
			Set<Entry<Date, Integer>> dataSetOfFarm = f.getData().entrySet();
			Iterator<Entry<Date, Integer>> itr = dataSetOfFarm.iterator();
			TreeMap<Date, Integer> validDataForFarm = new TreeMap<Date, Integer>();
			while (itr.hasNext()) {
				Entry<Date, Integer> curr = itr.next();
				if (inDateRange(curr.getKey(), d1, d2)) {
					validDataForFarm.put(curr.getKey(), curr.getValue());
				}
			}
			f.setData(validDataForFarm);
			milkDataInRange.put(s, f);
		});
		return milkDataInRange;
	}

	/**
	 * get average milk data in a certain date range
	 * 
	 * @param d1 - start date of the date range
	 * @param d2 - end date of the date range
	 * @return average milk data in the certain range
	 */
	public Data getAverageInDateRange(Date d1, Date d2) {
		TreeMap<String, Farm> milkDataInRange = getDataInDateRange(d1, d2);
		return new Data(d1, getAvg(milkDataInRange));
	}

	/**
	 * detect whether the milk data of a certain day is within the data range
	 * 
	 * @param day    - the day that provides the
	 * @param dayMin - the day that provides the
	 * @param dayMax - the day that provides the
	 * @return
	 */
	private boolean inDateRange(Date day, Date dayMin, Date dayMax) {
		if (day.compareTo(dayMin) >= 0 && day.compareTo(dayMax) <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * get minimum milk data in a certain date range
	 * 
	 * @param d1 - start date of the date range
	 * @param d2 - end date of the date range
	 * @return minimum milk data in the certain range
	 */
	public Data getMinInDateRange(Date d1, Date d2) {
		TreeMap<String, Farm> milkDataInRange = getDataInDateRange(d1, d2);
		return getMin(milkDataInRange);
	}

	/**
	 * get maximum milk data in a certain date range
	 * 
	 * @param d1 start date of the date range
	 * @param d2 end date of the date range
	 * @return maximum milk data in the certain range
	 */
	public Data getMaxInDateRange(Date d1, Date d2) {
		TreeMap<String, Farm> milkDataInRange = getDataInDateRange(d1, d2);
		return getMax(milkDataInRange);
	}

	/**
	 * calculate average milk data of a certain set of data
	 * 
	 * @param dataMap - certain set of data that is going to be analyzed
	 * @return average milk data of a certain set of data
	 */
	private String getAvg(TreeMap<String, Farm> dataMap) {
		Set<Entry<String, Farm>> dataSet = dataMap.entrySet();
		double tally = 0.0;
		long sum = 0;
		Iterator<Entry<String, Farm>> itr = dataSet.iterator();

		// go through the milk data of the input data set
		while (itr.hasNext()) {
			Entry<String, Farm> curr = itr.next();
			TreeMap<Date, Integer> data = curr.getValue().getData();
			Iterator<Entry<Date, Integer>> dataItr = data.entrySet().iterator();
			while (dataItr.hasNext()) {
				Entry<Date, Integer> currData = dataItr.next();
				tally++;
				sum += currData.getValue();
			}
		}
		double avg = sum / tally;
		return Double.toString(avg);
	}

	/**
	 * calculate minimum milk data of a certain set of data
	 * 
	 * @param dataMap - certain set of data that is going to be analyzed
	 * @return minimum milk data of a certain set of data
	 */
	private Data getMin(TreeMap<String, Farm> dataMap) {
		Set<Entry<String, Farm>> dataSet = dataMap.entrySet();
		Data minData = new Data(null, "");
		int minWeight = 0;
		int tally = 0;
		Iterator<Entry<String, Farm>> itr = dataSet.iterator();

		// go through the milk data of the input data set
		while (itr.hasNext()) {
			Entry<String, Farm> curr = itr.next();
			TreeMap<Date, Integer> data = curr.getValue().getData();
			Iterator<Entry<Date, Integer>> dataItr = data.entrySet().iterator();
			while (dataItr.hasNext()) {
				Entry<Date, Integer> currData = dataItr.next();

				// when the first data is analyzed
				if (tally == 0) {
					minData = new Data(currData.getKey(), currData.getValue().toString());
					minWeight = currData.getValue();
				}

				// when the second and later data is analyzed
				if (currData.getValue() < minWeight) {
					minData = new Data(currData.getKey(), currData.getValue().toString());
					minWeight = currData.getValue();
				}
			}
		}
		return minData;
	}

	/**
	 * calculate maximum milk data of a certain set of data
	 * 
	 * @param dataMap - certain set of data that is going to be analyzed
	 * @return maximum milk data of a certain set of data
	 */
	private Data getMax(TreeMap<String, Farm> dataMap) {
		Set<Entry<String, Farm>> dataSet = dataMap.entrySet();
		Data maxData = new Data(null, "");
		int maxWeight = 0;
		Iterator<Entry<String, Farm>> itr = dataSet.iterator();

		// go through the milk data of the input data set
		while (itr.hasNext()) {
			Entry<String, Farm> curr = itr.next();
			TreeMap<Date, Integer> data = curr.getValue().getData();
			Iterator<Entry<Date, Integer>> dataItr = data.entrySet().iterator();

			while (dataItr.hasNext()) {
				Entry<Date, Integer> currData = dataItr.next();
				// compare the data with the current max milk data
				if (currData.getValue() > maxWeight) {
					maxData = new Data(currData.getKey(), currData.getValue().toString());
					maxWeight = currData.getValue();
				}
			}
		}
		return maxData;
	}
}