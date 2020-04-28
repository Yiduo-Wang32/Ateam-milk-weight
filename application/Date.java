package application;

/**
 * 
 * Date - TODO Describe purpose of this user-defined type
 * @author Yiduo Wang (2020)
 *
 */
public class Date implements Comparable<Date> {
	private int year;
	private int month;
	private int day;

	public Date(int year, int month, int day) {
		setYear(year);
		setMonth(month);
		setDay(day);
	}

	public Date() {
		this(-1, -1, -1);
	}

	public int getYear() {
		return this.year;
	}

	public boolean setYear(int year) {
		this.year = year;
		return true;
	}

	public int getMonth() {
		return this.month;
	}

	public boolean setMonth(int month) {
		if (month > 0 && month <= 12) {
			this.month = month;
			return true;
		} else return false;
	}

	public int getDay() {
		return this.day;
	}

	public boolean setDay(int day) {
		if (day == -1) {
			this.day = -1;
			return true;
		} else if (day <= 0) {
			return false;
		} else {
			switch (month) {
			case -1:
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				if (day <= 31) {
					this.day = day;
					return true;
				} else return false;

			case 4:
			case 6:
			case 9:
			case 11:
				if (day <= 30) {
					this.day = day;
					return true;
				} else return false;

			case 2:
				if (day <= (isLeapYear() ? 29 : 28)) {
					this.day = day;
					return true;
				} else return false;

			default:
				return false;
			}
		}
	}

	private boolean isLeapYear() {
		// determine if year is leap year and return the corresponding value
		if (year % 4 != 0) return false;
		if (year % 100 == 0 && year % 400 != 0) return false;
		else return true;
	}

	@Override
	public int compareTo(Date o) {
		return this.year != o.getYear() ? this.year - o.getYear()
				: (this.month != o.getMonth() ? this.month - o.getMonth()
						: (this.day != o.getDay() ? this.day - o.getDay() : 0));
	}

	/**
	 * convert data to the string with the format month/day/year
	 */
	@Override
	public String toString() {
		return String.format("%d-%d-%d", year, month, day);
	}
}
