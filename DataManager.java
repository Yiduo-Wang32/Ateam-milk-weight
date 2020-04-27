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
		HashMap<String, Data> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<String, Data> milkDataOfTheMonth = new HashMap<String, Data>();
		milkDataFromFarms.forEach((d, s) -> {
			if (s.getDate().getMonth() == date.getMonth())
				milkDataOfTheMonth.put(d, s);
		});
		return new Data(date, getAvg(milkDataOfTheMonth));
	}

	public Data getMonthlyMin(Date date) {
		HashMap<String, Data> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<String, Data> milkDataOfTheMonth = new HashMap<String, Data>();
		milkDataFromFarms.forEach((d, s) -> {
			if (s.getDate().getMonth() == date.getMonth())
				milkDataOfTheMonth.put(d, s);
		});
		return getMin(milkDataOfTheMonth);

	}

	public Data getMonthlyMax(Date date) {
		HashMap<String, Data> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<String, Data> milkDataOfTheMonth = new HashMap<String, Data>();
		milkDataFromFarms.forEach((d, s) -> {
			if (s.getDate().getMonth() == date.getMonth())
				milkDataOfTheMonth.put(d, s);
		});
		return getMin(milkDataOfTheMonth);

	}

	public Data getMonthlyAverageForFarm(Date date, String farmID) {
		HashMap<String, Data> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<String, Data> milkDataOfTheFarm = new HashMap<String, Data>();
		milkDataFromFarms.forEach((d, s) -> {
			if (s.getDate().getMonth() == date.getMonth() && d.equals(farmID))
				milkDataOfTheFarm.put(d, s);
		});
		return new Data(date, getAvg(milkDataOfTheFarm));
	}

	public Data getMonthlyMinForFarm(Date date, String farmID) {
		HashMap<String, Data> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<String, Data> milkDataOfTheFarm = new HashMap<String, Data>();
		milkDataFromFarms.forEach((d, s) -> {
			if (s.getDate().getMonth() == date.getMonth() && d.equals(farmID))
				milkDataOfTheFarm.put(d, s);
		});
		return getMin(milkDataOfTheFarm);

	}

	public Data getMonthlyMaxForFarm(Date date, String farmID) {
		HashMap<String, Data> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<String, Data> milkDataOfTheFarm = new HashMap<String, Data>();
		milkDataFromFarms.forEach((d, s) -> {
			if (s.getDate().getMonth() == date.getMonth() && d.equals(farmID))
				milkDataOfTheFarm.put(d, s);
		});
		return getMax(milkDataOfTheFarm);
	}

	public Data getDataSortedByField() {
		return null;
	}

	public Data getAverageInDateRange(Date d1, Date d2) {
		HashMap<String, Data> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<String, Data> milkDataInRange = new HashMap<String, Data>();
		milkDataFromFarms.forEach((d, s) -> {
			if (inDateRange(s.getDate(), d1, d2)) {
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
		HashMap<String, Data> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<String, Data> milkDataInRange = new HashMap<String, Data>();
		milkDataFromFarms.forEach((d, s) -> {
			if (inDateRange(s.getDate(), d1, d2)) {
				milkDataInRange.put(d, s);
			}
		});
		return getMin(milkDataInRange);
	}

	public Data getMaxInDateRange(Date d1, Date d2) {

		HashMap<String, Data> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<String, Data> milkDataInRange = new HashMap<String, Data>();
		milkDataFromFarms.forEach((d, s) -> {
			if (inDateRange(s.getDate(), d1, d2)) {
				milkDataInRange.put(d, s);
			}
		});
		return getMax(milkDataInRange);
	}

	private String getAvg(HashMap<String, Data> dataMap) {
		Set<Entry<String, Data>> dataSet = dataMap.entrySet();
		double tally = 0.0;
		long sum = 0;
		Iterator<Entry<String, Data>> itr = dataSet.iterator();
		while (itr.hasNext()) {
			Entry<String, Data> curr = itr.next();
			tally++;
			sum += Integer.parseInt(curr.getValue().getWeight());
		}
		double avg = sum / tally;
		return Double.toString(avg);
	}

	private Data getMin(HashMap<String, Data> dataMap) {
		Set<Entry<String, Data>> dataSet = dataMap.entrySet();
		Data minData = new Data(null, "");
		int minWeight = 0;
		int tally = 0;
		Iterator<Entry<String, Data>> itr = dataSet.iterator();
		while (itr.hasNext()) {
			Entry<String, Data> curr = itr.next();
			if (tally == 0) {
				minData = curr.getValue();
				minWeight = Integer.parseInt(curr.getValue().getWeight());
				tally++;
			}
			int thisWeight = Integer.parseInt(curr.getValue().getWeight());
			if (thisWeight < minWeight) {
				minWeight = thisWeight;
				minData = curr.getValue();
			}
		}
		return minData;
	}

	private Data getMax(HashMap<String, Data> dataMap) {
		Set<Entry<String, Data>> dataSet = dataMap.entrySet();
		Data maxData = new Data(null, "");
		int maxWeight = 0;
		Iterator<Entry<String, Data>> itr = dataSet.iterator();
		while (itr.hasNext()) {
			Entry<String, Data> curr = itr.next();
			int thisWeight = Integer.parseInt(curr.getValue().getWeight());
			if (thisWeight > maxWeight) {
				maxWeight = thisWeight;
				maxData = curr.getValue();
			}
		}
		return maxData;
	}
}
