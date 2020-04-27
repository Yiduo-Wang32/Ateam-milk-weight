import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class DataManager {

	CheeseFactory factory;

	public DataManager(CheeseFactory factory) {
		this.factory = factory;
	}

	private HashMap<String, Farm> findDataOfMonth(Date date) {
		HashMap<String, Farm> milkDataFromFarms = factory.getMilkDataFromFarms();
		HashMap<String, Farm> milkDataOfTheMonth = new HashMap<String, Farm>();
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

	public Data getMonthlyAverage(Date date) {
		HashMap<String, Farm> milkDataOfTheMonth = findDataOfMonth(date);
		String avg = getAvg(milkDataOfTheMonth);
		return new Data(date, avg);
	}

	public Data getMonthlyMin(Date date) {
		HashMap<String, Farm> milkDataOfTheMonth = findDataOfMonth(date);
		return getMin(milkDataOfTheMonth);

	}

	public Data getMonthlyMax(Date date) {
		HashMap<String, Farm> milkDataOfTheMonth = findDataOfMonth(date);
		return getMin(milkDataOfTheMonth);

	}

	private HashMap<String, Farm> findDataOfFarmOfMont(Date date, String farmID) {
		HashMap<String, Farm> milkDataFromFarms = factory.getMilkDataFromFarms();
		HashMap<String, Farm> milkDataOfTheMonth = new HashMap<String, Farm>();
		Farm farm = milkDataFromFarms.get(farmID);
		Set<Entry<Date, Integer>> dataSetOfFarm = farm.getData().entrySet();
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

	public Data getMonthlyAverageForFarm(Date date, String farmID) {
		HashMap<String, Farm> milkDataOfTheMonth = findDataOfFarmOfMont(date, farmID);
		return new Data(date, getAvg(milkDataOfTheMonth));
	}

	public Data getMonthlyMinForFarm(Date date, String farmID) {
		HashMap<String, Farm> milkDataOfTheMonth = findDataOfFarmOfMont(date, farmID);
		return getMin(milkDataOfTheMonth);

	}

	public Data getMonthlyMaxForFarm(Date date, String farmID) {
		HashMap<String, Farm> milkDataOfTheMonth = findDataOfFarmOfMont(date, farmID);
		return getMax(milkDataOfTheMonth);
	}

	public Data getDataSortedByField() {
		return null;
	}

	private HashMap<String, Farm> getDataInDateRange(Date d1, Date d2) {
		HashMap<String, Farm> milkDataFromFarms = factory.getMilkDataFromFarms();
		HashMap<String, Farm> milkDataInRange = new HashMap<String, Farm>();
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

	public Data getAverageInDateRange(Date d1, Date d2) {
		HashMap<String, Farm> milkDataInRange = getDataInDateRange(d1, d2);
		return new Data(d1, getAvg(milkDataInRange));
	}

	private boolean inDateRange(Date day, Date dayMin, Date dayMax) {
		int dayYear = day.getYear();
		int dayMonth = day.getMonth();
		int dayDay = day.getDay();
		int minYear = dayMin.getYear();
		int minMonth = dayMin.getMonth();
		int minDay = dayMin.getDay();
		int maxYear = dayMax.getYear();
		int maxMonth = dayMax.getMonth();
		int maxDay = dayMax.getDay();

		if (dayYear > minYear && dayYear < maxYear) {
			return true;
		} else if (dayYear < minYear || dayYear > maxYear) {
			return false;
		} else if (dayYear == minYear) {
			if (dayMonth > minMonth) {
				return true;
			} else if (dayMonth < minMonth) {
				return false;
			} else {
				// dayMonth = minMonth
				if (dayDay >= minDay) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			// dayYear = maxYear
			if (dayMonth < maxMonth) {
				return true;
			} else if (dayMonth > maxMonth) {
				return false;
			} else {
				// dayMonth = maxMonth
				if (dayDay <= maxDay) {
					return true;
				} else {
					return false;
				}
			}

		}

	}

	public Data getMinInDateRange(Date d1, Date d2) {
		HashMap<String, Farm> milkDataInRange = getDataInDateRange(d1, d2);
		return getMin(milkDataInRange);
	}

	public Data getMaxInDateRange(Date d1, Date d2) {
		HashMap<String, Farm> milkDataInRange = getDataInDateRange(d1, d2);
		return getMax(milkDataInRange);
	}

	private String getAvg(HashMap<String, Farm> dataMap) {
		Set<Entry<String, Farm>> dataSet = dataMap.entrySet();
		double tally = 0.0;
		long sum = 0;
		Iterator<Entry<String, Farm>> itr = dataSet.iterator();
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

	private Data getMin(HashMap<String, Farm> dataMap) {
		Set<Entry<String, Farm>> dataSet = dataMap.entrySet();
		Data minData = new Data(null, "");
		int minWeight = 0;
		int tally = 0;
		Iterator<Entry<String, Farm>> itr = dataSet.iterator();
		while (itr.hasNext()) {
			Entry<String, Farm> curr = itr.next();
			TreeMap<Date, Integer> data = curr.getValue().getData();
			Iterator<Entry<Date, Integer>> dataItr = data.entrySet().iterator();
			while (dataItr.hasNext()) {
				Entry<Date, Integer> currData = dataItr.next();
				if (tally == 0) {
					minData = new Data(currData.getKey(), currData.getValue().toString());
					minWeight = currData.getValue();
				}

				if (currData.getValue() < minWeight) {
					minData = new Data(currData.getKey(), currData.getValue().toString());
					minWeight = currData.getValue();
				}
			}
		}
		return minData;
	}

	private Data getMax(HashMap<String, Farm> dataMap) {
		Set<Entry<String, Farm>> dataSet = dataMap.entrySet();
		Data maxData = new Data(null, "");
		int maxWeight = 0;
		Iterator<Entry<String, Farm>> itr = dataSet.iterator();
		while (itr.hasNext()) {
			Entry<String, Farm> curr = itr.next();
			TreeMap<Date, Integer> data = curr.getValue().getData();
			Iterator<Entry<Date, Integer>> dataItr = data.entrySet().iterator();
			while (dataItr.hasNext()) {
				Entry<Date, Integer> currData = dataItr.next();
				if (currData.getValue() > maxWeight) {
					maxData = new Data(currData.getKey(), currData.getValue().toString());
					maxWeight = currData.getValue();
				}
			}
		}
		return maxData;
	}
}
