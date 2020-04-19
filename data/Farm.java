package data;

import java.util.TreeMap;

public class Farm {
	private String id;
	private TreeMap<Date, Integer> data;

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
}