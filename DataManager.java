import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
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
		TreeMap<String, Farm> milkDataFromFarms = factory.getDataFromFarms();
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
		TreeMap<String, Farm> milkDataFromFarms = factory.getDataFromFarms();
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
		TreeMap<String, Farm> milkDataFromFarms = factory.getDataFromFarms();
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
		TreeMap<String, Farm> milkDataFromFarms = factory.getDataFromFarms();
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

//	FARM REPORT [required, get at least this one working]
//			Prompt user for a farm id and year (or use all available data)
//
//			Then, display the total milk weight and percent of the total of all farm for each month.
//
//			Sort, the list by month number 1-12, show total weight, then that farm's percent of the total milk received for each month.
//	
	public String[][] farmReport(String farmId, int year) {
		Farm farm = factory.getFarmFromID(farmId);
		String[][] returnArr = new String[14][2];
		int farmTotalWeightOfYear = farm.getTotalWeightOfYear(year);
		int allFarmTotalWeightOfYear = factory.getTotalWeightOfYear(year);
		returnArr[0][0] = Double.toString(farmTotalWeightOfYear);
		returnArr[1][0] = Double.toString(100 * farmTotalWeightOfYear / (allFarmTotalWeightOfYear + 0.0));
		for (int i = 1; i <= 12; i++) {
			returnArr[i + 1][0] = "MONTH: " + i;
			returnArr[i + 1][1] = Double
					.toString(100 * farm.getTotalWeightOfMonth(year, i) / (allFarmTotalWeightOfYear + 0.0));
		}
		return returnArr;
	}

//	ANNUAL REPORT [next most important to get working]
//			Ask for year.
//
//			Then display list of total weight and percent of total weight of all farms by farm for the year.
//
//			Sort by Farm ID, or you can allow the user to select display ascending or descending by weight.
	public String[][] annualReport(int year) {
		int numFarm = factory.getDataFromFarms().size();
		String[][] returnArr = new String[numFarm + 1][3];
		int i = 1;
		double totalWeight = factory.getTotalWeightOfYear(year) + 0.0;
		returnArr[0][0] = Double.toString(totalWeight);
		returnArr[0][1] = "";
		returnArr[0][2] = "";
		for (Farm f : factory.getDataFromFarms().values()) {
			returnArr[i][0] = f.getId();
			int farmWeight = f.getTotalWeightOfYear(year);
			returnArr[i][1] = Integer.toString(farmWeight);
			double percentage = farmWeight / totalWeight;
			returnArr[i][2] = Double.toString(percentage);
			i++;
		}
		return returnArr;
	}

//	MONTHLY REPORT [next most important to get working]
//			Ask for year and month.
//
//			Then, display a list of totals and percent of total by farm.
//
//			The list must be sorted by Farm ID, or you can prompt for ascending or descending by weight. 
//	
	public String[][] monthlyReport(int year, int month) {
		int numFarm = factory.getDataFromFarms().size();
		String[][] returnArr = new String[numFarm + 1][3];
		int i = 1;
		double totalWeight = factory.getTotalWeightOfMonth(year, month) + 0.0;
		returnArr[0][0] = Double.toString(totalWeight);
		returnArr[0][1] = "";
		returnArr[0][2] = "";
		for (Farm f : factory.getDataFromFarms().values()) {
			returnArr[i][0] = f.getId();
			int farmWeight = f.getTotalWeightOfMonth(year, month);
			returnArr[i][1] = Integer.toString(farmWeight);
			double percentage = farmWeight / totalWeight;
			returnArr[i][2] = Double.toString(percentage);
			i++;
		}
		return returnArr;
	}

//	DATE RANGE REPORT [least points, but still worth some if you can get this working]
//			Prompt user for start date (year-month-day) and end month-day,
//
//			Then display the total milk weight per farm and the percentage of the total for each farm over that date range.
//
//			The list must be sorted by Farm ID, or you can prompt for ascending or descending order by weight or percentage.
	public String[][] dateRangeReport(Date dayMin, Date dayMax) {
		int numFarm = factory.getDataFromFarms().size();
		String[][] returnArr = new String[numFarm + 1][3];
		int i = 1;
		double totalWeight = 0;
		int[] farmWeights = new int[numFarm + 1];
		for (Farm f : factory.getDataFromFarms().values()) {
			returnArr[i][0] = f.getId();
			int farmWeight = 0;
			for (Map.Entry<Date, Integer> data : f.getData().entrySet()) {
				Date keyDate = data.getKey();
				if (keyDate.compareTo(dayMin) >= 0 && keyDate.compareTo(dayMin) <= 0) {
					farmWeight += data.getValue();
				}
			returnArr[i][1] = Integer.toString(farmWeight);
			}
			farmWeights[i] = farmWeight;
			totalWeight += farmWeight;
		}
		for(int j =0; j < numFarm;j++) {
			returnArr[j+1][2] = Double.toString(100*totalWeight/farmWeights[j+1]);
		}
		returnArr[0][0] = Double.toString(totalWeight);
		returnArr[0][1] = "";
		returnArr[0][2] = "";

		return returnArr;
	}

}
