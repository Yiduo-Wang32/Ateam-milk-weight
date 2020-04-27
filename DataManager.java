import java.util.HashMap;

public class DataManager {

	CheeseFactory factory;

	public DataManager(CheeseFactory factory) {
		this.factory = factory;
	}

	public Data getMonthlyAverage(Date date) {
		HashMap<Data, String> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<Data, String> milkDataOfTheMonth = factory.milkDataFromFarms;
		milkDataFromFarms.forEach((d, s) -> {
			if (d.getDate().getMonth() == date.getMonth())
				milkDataOfTheMonth.put(d, s);
		});
		long sum = 0;
		double tally = 0;
		milkDataOfTheMonth.forEach((d, s) -> {
			tally++;
			sum += Integer.parseInt(d.getWeight());
		});
		double avg = sum / tally;
		return new Data(date, Double.toString(avg));
	}

	public Data getMonthlyMin(Date date) {
		HashMap<Data, String> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<Data, String> milkDataOfTheMonth = factory.milkDataFromFarms;
		milkDataFromFarms.forEach((d, s) -> {
			if (d.getDate().getMonth() == date.getMonth())
				milkDataOfTheMonth.put(d, s);
		});
		Data minData = new Data(date, "");
		int minWeight = 0;
		int tally = 0;
		milkDataOfTheMonth.forEach((d, s) -> {
			if (tally == 0) {
				minData = d;
				minWeight = Integer.parseInt(d.getWeight());
				tally++;
			}
			int thisWeight = Integer.parseInt(d.getWeight());
			if (thisWeight < minWeight) {
				minWeight = thisWeight;
				minData = d;
			}

		});
		return minData;

	}

	public Data getMonthlyMax(Date date) {
		HashMap<Data, String> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<Data, String> milkDataOfTheMonth = factory.milkDataFromFarms;
		milkDataFromFarms.forEach((d, s) -> {
			if (d.getDate().getMonth() == date.getMonth())
				milkDataOfTheMonth.put(d, s);
		});
		Data maxData = new Data(date, "");
		int maxWeight = 0;
		milkDataOfTheMonth.forEach((d, s) -> {
			int thisWeight = Integer.parseInt(d.getWeight());
			if (thisWeight > maxWeight) {
				maxWeight = thisWeight;
				maxData = d;
			}
		});
		return maxData;

	}

	public Data getMonthlyAverageForFarm(Date date, String farmID) {
		HashMap<Data, String> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<Data, String> milkDataOfTheFarm = factory.milkDataFromFarms;
		milkDataFromFarms.forEach((d, s) -> {
			if (d.getDate().getMonth() == date.getMonth() && s.equals(farmID))
				milkDataOfTheFarm.put(d, s);
		});
		long sum = 0;
		double tally = 0;
		milkDataOfTheFarm.forEach((d, s) -> {
			tally++;
			sum += Integer.parseInt(d.getWeight());
		});
		double avg = sum / tally;
		return new Data(date, Double.toString(avg));
	}

	public Data getMonthlyMinForFarm(Date date, String farmID) {
		HashMap<Data, String> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<Data, String> milkDataOfTheFarm = factory.milkDataFromFarms;
		milkDataFromFarms.forEach((d, s) -> {
			if (d.getDate().getMonth() == date.getMonth() && s.equals(farmID))
				milkDataOfTheFarm.put(d, s);
		});
		Data minData = new Data(date, "");
		int minWeight = 0;
		int tally = 0;
		milkDataOfTheFarm.forEach((d, s) -> {
			if (tally == 0) {
				minData = d;
				minWeight = Integer.parseInt(d.getWeight());
				tally++;
			}
			int thisWeight = Integer.parseInt(d.getWeight());
			if (thisWeight < minWeight) {
				minWeight = thisWeight;
				minData = d;
			}

		});
		return minData;
	}

	public Data getMonthlyMaxForFarm(Date date, String farmID) {
		HashMap<Data, String> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<Data, String> milkDataOfTheFarm = factory.milkDataFromFarms;
		milkDataFromFarms.forEach((d, s) -> {
			if (d.getDate().getMonth() == date.getMonth() && s.equals(farmID))
				milkDataOfTheFarm.put(d, s);
		});
		Data maxData = new Data(date, "");
		int maxWeight = 0;
		milkDataOfTheFarm.forEach((d, s) -> {
			int thisWeight = Integer.parseInt(d.getWeight());
			if (thisWeight > maxWeight) {
				maxWeight = thisWeight;
				maxData = d;
			}

		});
		return maxData;
	}

	public Data getDataSortedByField() {

	}

	public Data getAverageInDateRange(Date d1, Date d2) {
		HashMap<Data, String> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<Data, String> milkDataInRange = factory.milkDataFromFarms;
		milkDataFromFarms.forEach((d, s) -> {
			if (inDateRange(d.getDate(), d1, d2)) {
				milkDataInRange.put(d, s);
			}
		});
		long sum = 0;
		double tally = 0;
		milkDataInRange.forEach((d, s) -> {
			tally++;
			sum += Integer.parseInt(d.getWeight());
		});
		double avg = sum / tally;
		return new Data(d1, Double.toString(avg));
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
		HashMap<Data, String> milkDataInRange = factory.milkDataFromFarms;
		milkDataFromFarms.forEach((d, s) -> {
			if (inDateRange(d.getDate(), d1, d2)) {
				milkDataInRange.put(d, s);
			}
		});
		Data minData = new Data(d1, "");
		int minWeight = 0;
		int tally = 0;
		milkDataInRange.forEach((d, s) -> {
			if (tally == 0) {
				minData = d;
				minWeight = Integer.parseInt(d.getWeight());
				tally++;
			}
			int thisWeight = Integer.parseInt(d.getWeight());
			if (thisWeight < minWeight) {
				minWeight = thisWeight;
				minData = d;
			}

		});
		return minData;
	}

	public Data getMaxInDateRange(Date d1, Date d2) {

		HashMap<Data, String> milkDataFromFarms = factory.milkDataFromFarms;
		HashMap<Data, String> milkDataInRange = factory.milkDataFromFarms;
		milkDataFromFarms.forEach((d, s) -> {
			if (inDateRange(d.getDate(), d1, d2)) {
				milkDataInRange.put(d, s);
			}
		});
		Data maxData = new Data(d1, "");
		int maxWeight = 0;
		milkDataInRange.forEach((d, s) -> {
			int thisWeight = Integer.parseInt(d.getWeight());
			if (thisWeight > maxWeight) {
				maxWeight = thisWeight;
				maxData = d;
			}

		});
		return maxData;
	}
}
