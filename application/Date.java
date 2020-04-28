package application;

/**
 * 
 * Date - A class to store a date value. -1 in any of the field represents the
 * information that is not given
 * 
 * @author Yiduo Wang (2020)
 *
 */
public class Date implements Comparable<Date> {
	private int year;
	private int month;
	private int day;

	/**
	 * create Date class with given year, month, day
	 * 
	 * @param year
	 * @param month
	 * @param day
	 */
	public Date(int year, int month, int day) {
		setYear(year);
		setMonth(month);
		setDay(day);
	}

	/**
	 * No argument constructor
	 */
	public Date() {
		this(-1, -1, -1);
	}

	/**
	 * get the year
	 * 
	 * @return the year
	 */
	public int getYear() {
		return this.year;
	}

	/**
	 * set the year to given value
	 * 
	 * @param year the year to set
	 * @return true if value is valid and set to year, false otherwise
	 */
	public boolean setYear(int year) {
		this.year = year;
		return true;
	}

	/**
	 * get the month
	 * 
	 * @return the month
	 */
	public int getMonth() {
		return this.month;
	}

	/**
	 * set the month with the value given
	 * 
	 * @param month the month to set, must be an int between 1 to 12, or -1
	 * @return true if the value given is valid and set to month, false otherwise
	 */
	public boolean setMonth(int month) {
		if (month > 0 && month <= 12) {
			this.month = month;
			return true;
		} else
			return false;
	}

	/**
	 * get the day
	 * 
	 * @return the day
	 */
	public int getDay() {
		return this.day;
	}

	/**
	 * set the day with given value
	 * 
	 * @param day the given day. Must be a integer with a range that is valid for
	 *            putting into the calendar, or -1
	 * @return true if the value is valid and set, false otherwise
	 */
	public boolean setDay(int day) {
		if (day == -1) { // set and return true if day is -1
			this.day = -1;
			return true;
		} else if (day <= 0) { // return false if day <= 0 (except -1)
			return false;
		} else { // determine the upper limit according to the month
			switch (month) {
				// for months with 31 days
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
					} else
						return false;

					// for months with 30 days
				case 4:
				case 6:
				case 9:
				case 11:
					if (day <= 30) {
						this.day = day;
						return true;
					} else
						return false;
					// for February, determine whether the year is leap year
				case 2:
					if (day <= (isLeapYear() ? 29 : 28)) {
						this.day = day;
						return true;
					} else
						return false;

				default:
					return false;
			}
		}
	}

	/**
	 * private helper method for determining whether the year is a leap year
	 */
	private boolean isLeapYear() {
		// determine if year is leap year and return the corresponding value
		if (year % 4 != 0)
			return false;
		if (year % 100 == 0 && year % 400 != 0)
			return false;
		else
			return true;
	}

	/**
	 * compare with another Date object
	 */
	@Override
	public int compareTo(Date o) {
		return this.year != o.getYear() ? this.year - o.getYear()
				: (this.month != o.getMonth() ? this.month - o.getMonth()
						: (this.day != o.getDay() ? this.day - o.getDay() : 0));
	}

	/**
	 * convert data to the string with the format year-month-day
	 */
	@Override
	public String toString() {
		return String.format("%d-%d-%d", year, month, day);
	}
}
