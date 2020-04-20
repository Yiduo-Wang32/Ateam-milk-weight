
package application;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	/**
	 * start the program
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();

		Button farmOverview = new Button();
		farmOverview.setText("Data Overview");
		farmOverview.setOnAction(event -> {
			FarmDataWithYearMonth sf = new FarmDataWithYearMonth("123");
			try {
				sf.ShowFarmDataTable();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//      {
//        SingleFarm sf = new SingleFarm("123");
//        try {
//          sf.showOverviewPanel();
//        } catch (Exception e) {
//        }
//      }
		);
		root.setCenter(farmOverview);

		Scene mainScene = new Scene(root);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args); // launch the program
	}

}
