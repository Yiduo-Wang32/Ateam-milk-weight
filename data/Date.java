package data;

public class Date implements Comparable<Date> {
	private int year;
	private int month;
	private int day;

	public Date(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public Date() {
		this(-1, -1, -1);
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return this.month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return this.day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	@Override
	public int compareTo(Date o) {
		return this.year != o.getYear() ? this.year - o.getYear()
				: (this.month != o.getMonth() ? this.month - o.getMonth()
						: (this.day != o.getDay() ? this.day - o.getDay() : 0));
	}

	@Override
	public String toString() {
		return String.format("%04d %02d %02d", year, month, day);
	}
}
