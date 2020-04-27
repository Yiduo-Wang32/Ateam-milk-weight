import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class DataManager {

	CheeseFactory factory;

	public DataManager(CheeseFactory factory) {
		this.factory = factory;
	}

	public Data getMonthlyAverage(Date date) {
		HashMap<Data, String> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<Data, String> milkDataOfTheMonth = new HashMap<Data, String>();
		milkDataFromFarms.forEach((d, s) -> {
			if (d.getDate().getMonth() == date.getMonth())
				milkDataOfTheMonth.put(d, s);
		});
		return new Data(date, getAvg(milkDataOfTheMonth));
	}

	public Data getMonthlyMin(Date date) {
		HashMap<Data, String> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<Data, String> milkDataOfTheMonth = new HashMap<Data, String>();
		milkDataFromFarms.forEach((d, s) -> {
			if (d.getDate().getMonth() == date.getMonth())
				milkDataOfTheMonth.put(d, s);
		});
		return getMin(milkDataOfTheMonth);

	}

	public Data getMonthlyMax(Date date) {
		HashMap<Data, String> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<Data, String> milkDataOfTheMonth = new HashMap<Data, String>();
		milkDataFromFarms.forEach((d, s) -> {
			if (d.getDate().getMonth() == date.getMonth())
				milkDataOfTheMonth.put(d, s);
		});
		return getMin(milkDataOfTheMonth);

	}

	public Data getMonthlyAverageForFarm(Date date, String farmID) {
		HashMap<Data, String> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<Data, String> milkDataOfTheFarm = new HashMap<Data, String>();
		milkDataFromFarms.forEach((d, s) -> {
			if (d.getDate().getMonth() == date.getMonth() && s.equals(farmID))
				milkDataOfTheFarm.put(d, s);
		});
		return new Data(date, getAvg(milkDataOfTheFarm));
	}

	public Data getMonthlyMinForFarm(Date date, String farmID) {
		HashMap<Data, String> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<Data, String> milkDataOfTheFarm = new HashMap<Data, String>();
		milkDataFromFarms.forEach((d, s) -> {
			if (d.getDate().getMonth() == date.getMonth() && s.equals(farmID))
				milkDataOfTheFarm.put(d, s);
		});
		return getMin(milkDataOfTheFarm);

	}

	public Data getMonthlyMaxForFarm(Date date, String farmID) {
		HashMap<Data, String> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<Data, String> milkDataOfTheFarm = new HashMap<Data, String>();
		milkDataFromFarms.forEach((d, s) -> {
			if (d.getDate().getMonth() == date.getMonth() && s.equals(farmID))
				milkDataOfTheFarm.put(d, s);
		});
		return getMax(milkDataOfTheFarm);
	}

	public Data getDataSortedByField() {
		return null;
	}

	public Data getAverageInDateRange(Date d1, Date d2) {
		HashMap<Data, String> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<Data, String> milkDataInRange = new HashMap<Data, String>();
		milkDataFromFarms.forEach((d, s) -> {
			if (inDateRange(d.getDate(), d1, d2)) {
				milkDataInRange.put(d, s);
			}
		});
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
		HashMap<Data, String> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<Data, String> milkDataInRange = new HashMap<Data, String>();
		milkDataFromFarms.forEach((d, s) -> {
			if (inDateRange(d.getDate(), d1, d2)) {
				milkDataInRange.put(d, s);
			}
		});
		return getMin(milkDataInRange);
	}

	public Data getMaxInDateRange(Date d1, Date d2) {

		HashMap<Data, String> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<Data, String> milkDataInRange = new HashMap<Data, String>();
		milkDataFromFarms.forEach((d, s) -> {
			if (inDateRange(d.getDate(), d1, d2)) {
				milkDataInRange.put(d, s);
			}
		});
		return getMax(milkDataInRange);
	}

	private String getAvg(HashMap<Data, String> dataMap) {
		Set<Entry<Data, String>> dataSet = dataMap.entrySet();
		double tally = 0.0;
		long sum = 0;
		Iterator<Entry<Data, String>> itr = dataSet.iterator();
		while (itr.hasNext()) {
			Entry<Data, String> curr = itr.next();
			tally++;
			sum += Integer.parseInt(curr.getKey().getWeight());
		}
		double avg = sum / tally;
		return Double.toString(avg);
	}

	private Data getMin(HashMap<Data, String> dataMap) {
		Set<Entry<Data, String>> dataSet = dataMap.entrySet();
		Data minData = new Data(null, "");
		int minWeight = 0;
		int tally = 0;
		Iterator<Entry<Data, String>> itr = dataSet.iterator();
		while (itr.hasNext()) {
			Entry<Data, String> curr = itr.next();
			if (tally == 0) {
				minData = curr.getKey();
				minWeight = Integer.parseInt(curr.getKey().getWeight());
				tally++;
			}
			int thisWeight = Integer.parseInt(curr.getKey().getWeight());
			if (thisWeight < minWeight) {
				minWeight = thisWeight;
				minData = curr.getKey();
			}
		}
		return minData;
	}

	private Data getMax(HashMap<Data, String> dataMap) {
		Set<Entry<Data, String>> dataSet = dataMap.entrySet();
		Data maxData = new Data(null, "");
		int maxWeight = 0;
		Iterator<Entry<Data, String>> itr = dataSet.iterator();
		while (itr.hasNext()) {
			Entry<Data, String> curr = itr.next();
			int thisWeight = Integer.parseInt(curr.getKey().getWeight());
			if (thisWeight > maxWeight) {
				maxWeight = thisWeight;
				maxData = curr.getKey();
			}
		}
		return maxData;
	}
}
