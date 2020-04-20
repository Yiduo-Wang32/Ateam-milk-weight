package application;

import java.util.List;
import data.*;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	private List<String> args;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FarmDataWithYearMonth display = new FarmDataWithYearMonth("66");
		display.ShowFarmDataTable();

	}

	/**
	   * @param args
	   */
	  public static void main1(String[] args) {
	    launch(args);
	  }
}
