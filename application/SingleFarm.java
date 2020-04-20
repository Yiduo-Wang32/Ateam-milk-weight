/**
 * SingleFarm.java created by Yiduo Wang on HP Envy x360 in Ateam-milk-weight
 *
 * Author:   Yiduo Wang (ywang2292@wisc.edu)
 * Date:     Apr 19, 2020
 * 
 * Course:   CS 400
 * Semester: Spring 2020
 * Lecture:  001
 * 
 * IDE:      Eclipse IDE for Java Developers
 * Version:  2019-12 (4.14.0)
 * Build id: 20191212-1212
 * 
 * Device:   Ethan-hp
 * OS:       Windows 10 Pro
 * Version:  1809
 * OS Build: 17763.973
 * 
 * 
 * List Collaborators:
 * 
 * Other Credits: 
 * 
 * Known Bugs:
 */
package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * SingleFarm - TODO Describe purpose of this user-defined type
 * 
 * @author Yiduo Wang (2020)
 *
 */
public class SingleFarm {
	private final String FARM_ID;
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 450;
	private static final String TOP_BOTTOM_IMAGE_PATH = "TopBottomImage.jpg";
	private static final String VERTICAL_IMAGE_PATH = "LeftRightImage.jpg";
	private static final String ICON1_PATH = "OverviewByMonthIcon.jpg";
	private static final String ICON2_PATH = "SeePercentageShareIcon.jpg";
	private static final String ICON3_PATH = "AccessDataIcon.jpg";

	public SingleFarm(String id) {
		FARM_ID = id;
	}

	public void showOverviewPanel() throws Exception {
		Stage overview = new Stage();
		BorderPane root = new BorderPane();

		Image topBottomImg = new Image(new FileInputStream(TOP_BOTTOM_IMAGE_PATH));
		Image verticalImg = new Image(new FileInputStream(VERTICAL_IMAGE_PATH));
		Image overviewByMonthImg = new Image(new FileInputStream(ICON1_PATH));
		Image seePercentageShareImg = new Image(new FileInputStream(ICON2_PATH));
		Image accessDataImg = new Image(new FileInputStream(ICON3_PATH));

		Text text = new Text("Data for Farm " + FARM_ID + ":");
		text.setFont(Font.font("Calibri Light", FontWeight.NORMAL, FontPosture.REGULAR, 30));
		root.setTop(text);
		
		HBox buttons = new HBox();

		VBox buttonBox1 = new VBox();
		VBox buttonBox2 = new VBox();
		VBox buttonBox3 = new VBox();

		Button bt1 = new Button();
		Button bt2 = new Button();
		Button bt3 = new Button();

		bt1.setGraphic(new ImageView(overviewByMonthImg));
		bt2.setGraphic(new ImageView(seePercentageShareImg));
		bt3.setGraphic(new ImageView(accessDataImg));

		buttonBox1.getChildren().add(bt1);
		buttonBox1.getChildren().add(new Label("Overview by Month"));
		buttonBox2.getChildren().add(bt2);
		buttonBox2.getChildren().add(new Label("See Pencentage Share"));
		buttonBox3.getChildren().add(bt3);
		buttonBox3.getChildren().add(new Label("Access Data"));

		buttons.getChildren().addAll(new VBox[] { buttonBox1, buttonBox2, buttonBox3 });
		root.setCenter(buttons);

		Scene overviewScene = new Scene(root);
		overview.setScene(overviewScene);
		overview.setTitle(FARM_ID);
		overview.show();

	}
}
