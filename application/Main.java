/**
 * Main.java created by ateam66 - Milk Weight project GUI This displays a user interface of the milk
 * project that could analyze milk data based on user input file. User can display relevant
 * statistics, add/edit/remove existing data, and save the report to an output file. NOTE: all data
 * displayed in this GUI as of 4/21/20 are DUMMY (fake) data. We are currently working on backend
 * data structure so it can analyze and display real statistics soon.
 *
 * Authors: 1. Yumeng Bai LEC001 bai54@wisc.edu 2. Xi (Chelsea) Chen LEC002 xchen783@wisc.edu 3.
 * Ruiwen Wang LEC002 rwang436@wisc.edu 4. Yiduo Wang LEC001 ywang2292@wisc.edu 5. Kexin Chen LEC002
 * kchen264@wisc.edu
 * 
 * Date: 4/21/2020
 * 
 * Course: CS400 Semester: Spring 2020
 *
 * Other Credits: N/A
 *
 * Known Bugs: N/A
 */
package application;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


/**
 * Main - Main GUI that creates and displays a user interactive screen so users can use Milk
 * Analyzer program to analyze the data.
 * 
 * @authors Yumeng Bai, Chelsea Chen, Ruiwen Wang, Yiduo Wang, Kexin Chen
 *
 */
public class Main extends Application {

  private static final int WINDOW_WIDTH = 1000; // width of the window that users see
  private static final int WINDOW_HEIGHT = 800; // height of the window that users see
  private static final String APP_TITLE = "Milk Weight Analyzer"; // title of the program
  private CheeseFactory factory = new CheeseFactory();
  private FileManager file = null;

  /**
   * Start method that displays the primary (main) stage user will see
   * 
   * @author Chelsea Chen
   * @param primary stage - first stage to be displayed
   * @throws Exception
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    BorderPane root = new BorderPane();
    HBox h1 = new HBox();
    Label welcome = new Label("Welcome to Milk Weight Analyzer!");
    welcome.setFont(new Font("Arial", 36));
    Image milk = new Image("milk1.jpg", 100, 100, false, false);
    ImageView milkImage = new ImageView(milk);
    // add welcome message and milk image in a horizontal box
    // and set to the top of the window
    h1.getChildren().addAll(welcome, milkImage);
    root.setTop(h1);

    HBox h2 = new HBox();
    VBox v1 = new VBox();
    Label description = new Label("This program aims to help localize and report on milk "
        + "that has been sold to them by their farmers, based on the input file you provide."
        + "\n\n\n");
    description.setFont(new Font("Arial", 15));
    Label fileMess = new Label("Please provide the name of the milk data file（.csv）you would"
        + " like this program to analyze: ");

    TextField fileInput = new TextField();
    fileInput.setPromptText("Press enter to load file");

    // after user enter the file name and press enter, the file data will be stored in cheese
    // factory
    fileInput.setOnAction(e -> {
      String fileName = fileInput.getText();
      file = new FileManager(fileName);
      factory.insertData(file);

    });

    Button submit = new Button("Submit");
    submit.setOnAction(e -> chooseOptions(primaryStage));
    // add message label and textfield to a HBox and then to a VBox with button.
    h2.getChildren().addAll(fileMess, fileInput);
    v1.getChildren().addAll(description, h2, submit);
    root.setCenter(v1);

    HBox h3 = new HBox();
    Image exitImage = new Image("exit.png", 50, 50, false, false);
    Button exit = new Button("Exit", new ImageView(exitImage));
    exit.setOnAction(e -> Platform.exit());

    Image helpImage = new Image("help.png", 50, 50, false, false);
    Button help = new Button("Help Me!", new ImageView(helpImage));
    help.setOnAction(e -> help(primaryStage));
    h3.getChildren().addAll(exit, help);

    root.setBottom(h3);

    Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    primaryStage.setTitle(APP_TITLE);
    primaryStage.setScene(mainScene);
    primaryStage.show();

  }

  
  /**
   * Launch the program.
   * 
   * @param args - input arguments.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
