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
import java.util.Set;
import java.util.TreeMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
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
public class GUI {

  private static final int WINDOW_WIDTH = 1000; // width of the window that users see
  private static final int WINDOW_HEIGHT = 800; // height of the window that users see
  private static final String APP_TITLE = "Milk Weight Analyzer"; // title of the program
  
  private CheeseFactory factory;
  private FileManager file;
  
  public GUI(CheeseFactory factory, FileManager file) {
    this.factory = factory;
    this.file = file;
  }
  
  /**
   * An inner class to store the a date-weight pair
   * @author Kexin
   *
   */

  public class DataForTable{
    private String date;
    private String weight;
    
    public DataForTable(String date, String weight) {
      this.date = date;
      this.weight = weight;
    }
    
    public String getDate() {
      return this.date;
    }
    
    public String getWeight() {
      return this.weight;
    }
  }

  /**
   * Displays a pop-up help message
   * 
   * @author Chelsea Chen
   * @param primaryStage - the previous stage
   */
  public void help(Stage primaryStage) {
    VBox v1 = new VBox();
    Label helpMessage = new Label("What can this program do? " + "\n\n");
    helpMessage.setFont(new Font("Verdana", 14));
    Label helpMessage2 = new Label("After you input your file, you can choose to "
        + "display min/max/average milk weight by month for" + "\n"
        + "a specific farm and year, min/max/average milk weight "
        + "for all months for a specific month/year, " + "\n"
        + "each farm's percent of the total for all farms, and each farm's share "
        + "for a specific month or year. " + "\n" + "You can also generate farm "
        + "report, annual report, monthly report, and date range report." + "\n\n");
    Label helpMessage3 = new Label("What if I need to edit the data?" + "\n\n");
    helpMessage3.setFont(new Font("Verdana", 14));
    Label helpMessage4 = new Label("You can certainly do that! :) You may add, edit and "
        + "remove existing data for a given farm, year," + "\n" + "month, and day, after "
        + "inputing a file" + "\n\n");

    Button gotIt = new Button("Got it!");

    v1.getChildren().addAll(helpMessage, helpMessage2, helpMessage3, helpMessage4, new Label(""),
        gotIt);

    Scene newScene = new Scene(v1, 600, 250);
    final Stage popUp = new Stage();
    popUp.initModality(Modality.APPLICATION_MODAL);
    gotIt.setOnAction(e -> exitCurrScreen(popUp));
    popUp.initOwner(primaryStage);
    popUp.setScene(newScene);
    popUp.show();
  }

  /**
   * Method that exits the current screen (returns the the previous screen)
   * 
   * @author Chelsea Chen
   * @param currStage - current stage
   */
  public void exitCurrScreen(Stage currStage) {
    currStage.close();
  }

  /**
   * Displays a window to choose option to analyze data.
   * 
   * @author Yumeng Bai
   * @param primaryStage - previous stage
   */
  public void chooseOptions(Stage primaryStage) {
    // Main layout is Border Pane example (top,left,center,right,bottom)
    BorderPane root = new BorderPane();
    Label message = new Label("Choose an option below to start analyzing your" + " milk data:");
    message.setFont(new Font("Arial", 30));
    root.setTop(message);

    VBox v1 = new VBox();

    Image image1 = new Image("chart.png");
    ImageView imageView1 = new ImageView(image1);
    imageView1.setFitWidth(75);
    imageView1.setFitHeight(75);

    VBox vb1 = new VBox();
    vb1.getChildren().addAll(imageView1, new Label("Show statistics"));
    Button b1 = new Button("", vb1);

    Image image2 = new Image("piechart.png");
    ImageView imageView2 = new ImageView(image2);
    imageView2.setFitWidth(75);
    imageView2.setFitHeight(75);

    VBox vb2 = new VBox();
    vb2.getChildren().addAll(imageView2, new Label("Show % share"));
    Button b2 = new Button("", vb2);

    v1.getChildren().addAll(b1, new Label(""), new Label(""), new Label(""), new Label(""),
        new Label(""), new Label(""), b2);

    VBox v2 = new VBox();
    Image image3 = new Image("edit.png");
    ImageView imageView3 = new ImageView(image3);
    imageView3.setFitWidth(75);
    imageView3.setFitHeight(75);

    VBox vb3 = new VBox();
    vb3.getChildren().addAll(imageView3, new Label("Edit data"));
    Button b3 = new Button("", vb3);

    Image image4 = new Image("report.jpg");
    ImageView imageView4 = new ImageView(image4);
    imageView4.setFitWidth(75);
    imageView4.setFitHeight(75);

    VBox vb4 = new VBox();
    vb4.getChildren().addAll(imageView4, new Label("Get reports"));
    Button b4 = new Button("", vb4);

    v2.getChildren().addAll(b3, new Label(""), new Label(""), new Label(""), new Label(""),
        new Label(""), new Label(""), b4);

    AnchorPane ap1 = new AnchorPane();
    AnchorPane.setTopAnchor(v1, 200.0);
    AnchorPane.setLeftAnchor(v1, 200.0);
    root.setLeft(ap1);
    ap1.getChildren().addAll(v1);

    AnchorPane ap2 = new AnchorPane();
    AnchorPane.setTopAnchor(v2, 200.0);
    AnchorPane.setRightAnchor(v2, 200.0);
    root.setRight(ap2);
    ap2.getChildren().addAll(v2);


    Scene secScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    Stage secScreen = new Stage();

    b1.setOnAction(e -> oneOrMoreFarmsStats(secScreen));
    b2.setOnAction(e -> oneOrMoreFarmsPercent(secScreen));
    b3.setOnAction(e -> changeDataOptions(secScreen));
    b4.setOnAction(e -> generateReport(secScreen));

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    returns.setOnAction(e -> exitCurrScreen(secScreen));

    root.setBottom(returns);
    secScreen.setScene(secScene);
    secScreen.show();
  }

  /**
   * Shows a window that let user choose one farm or all farm for display statistics.
   * 
   * @author Chelsea Chen
   * @param primaryStage - previous stage
   */
  public void oneOrMoreFarmsStats(Stage primaryStage) {
    BorderPane root = new BorderPane();
    Label message =
        new Label("Would you like to analyze statistics for a specific farm, " + "or all farms?");
    message.setFont(new Font("Arial", 30));
    VBox mess = new VBox();
    Image chart = new Image("chart.png", 75, 75, false, false);
    ImageView chartImage = new ImageView(chart);
    mess.getChildren().addAll(message, chartImage);
    root.setTop(mess);

    Image farm = new Image("farm_single.png", 75, 75, false, false);
    ImageView farmImage = new ImageView(farm);
    VBox v1 = new VBox();
    v1.getChildren().addAll(farmImage, new Label("A single farm"));
    Button b1 = new Button("", v1);

    Image manyFarms = new Image("many_farms.png", 75, 75, false, false);
    ImageView manyFarmsImage = new ImageView(manyFarms);
    VBox v2 = new VBox();
    v2.getChildren().addAll(manyFarmsImage, new Label("All farms"));
    Button b2 = new Button("", v2);

    AnchorPane ap1 = new AnchorPane();
    AnchorPane.setTopAnchor(b1, 300.0);
    AnchorPane.setLeftAnchor(b1, 200.0);
    root.setLeft(ap1);
    ap1.getChildren().addAll(b1);

    AnchorPane ap2 = new AnchorPane();
    AnchorPane.setTopAnchor(b2, 300.0);
    AnchorPane.setRightAnchor(b2, 150.0);
    root.setRight(ap2);
    ap2.getChildren().addAll(b2);

    Scene farmScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    Stage farmScreen = new Stage();

    b1.setOnAction(e -> showStatsOneFarm(farmScreen));
    b2.setOnAction(e -> chooseDatesAllFarms(farmScreen));

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    root.setBottom(returns);
    returns.setOnAction(e -> exitCurrScreen(farmScreen));

    farmScreen.initModality(Modality.APPLICATION_MODAL);
    farmScreen.initOwner(primaryStage);
    farmScreen.setScene(farmScene);
    farmScreen.show();
  }

  /**
   * Shows a window that let user choose one farm or all farm for display percent share.
   * 
   * @author Chelsea Chen
   * @param primaryStage - previous stage
   */
  public void oneOrMoreFarmsPercent(Stage primaryStage) {
    BorderPane root = new BorderPane();
    Label message =
        new Label("Would you like to see percent share for a specific farm, " + "or all farms?");
    message.setFont(new Font("Arial", 30));
    VBox mess = new VBox();
    Image percent = new Image("piechart.png", 75, 75, false, false);
    ImageView percentImage = new ImageView(percent);
    mess.getChildren().addAll(message, percentImage);
    root.setTop(mess);

    Image farm = new Image("farm_single.png", 75, 75, false, false);
    ImageView farmImage = new ImageView(farm);
    VBox v1 = new VBox();
    v1.getChildren().addAll(farmImage, new Label("A single farm"));
    Button b1 = new Button("", v1);

    Image manyFarms = new Image("many_farms.png", 75, 75, false, false);
    ImageView manyFarmsImage = new ImageView(manyFarms);
    VBox v2 = new VBox();
    v2.getChildren().addAll(manyFarmsImage, new Label("All farms"));
    Button b2 = new Button("", v2);

    AnchorPane ap1 = new AnchorPane();
    AnchorPane.setTopAnchor(b1, 300.0);
    AnchorPane.setLeftAnchor(b1, 200.0);
    root.setLeft(ap1);
    ap1.getChildren().addAll(b1);

    AnchorPane ap2 = new AnchorPane();
    AnchorPane.setTopAnchor(b2, 300.0);
    AnchorPane.setRightAnchor(b2, 150.0);
    root.setRight(ap2);
    ap2.getChildren().addAll(b2);

    Scene farmScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    Stage farmScreen = new Stage();

    b1.setOnAction(e -> showPercentShareOneFarm(farmScreen));
    b2.setOnAction(e -> showPercentShareAllFarms(farmScreen));

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    root.setBottom(returns);
    returns.setOnAction(e -> exitCurrScreen(farmScreen));

    farmScreen.setScene(farmScene);
    farmScreen.show();
  }

  /**
   * Shows a window that let user choose to add, edit or remove data.
   * 
   * @author Chelsea Chen
   * @param primaryStage - prev stage
   */
  public void changeDataOptions(Stage primaryStage) {
    BorderPane root = new BorderPane();
    Label message = new Label("Would you like to add, edit or remove data?");
    message.setFont(new Font("Arial", 30));
    root.setTop(message);

    VBox v1 = new VBox();
    Image add = new Image("add.jpg", 75, 75, false, false);
    v1.getChildren().addAll(new ImageView(add), new Label("Add data"));
    Button b1 = new Button("", v1);

    VBox v2 = new VBox();
    Image edit = new Image("edit1.png", 75, 75, false, false);
    v2.getChildren().addAll(new ImageView(edit), new Label("Edit data"));
    Button b2 = new Button("", v2);

    VBox v3 = new VBox();
    Image remove = new Image("remove.png", 75, 75, false, false);
    v3.getChildren().addAll(new ImageView(remove), new Label("Remove data"));
    Button b3 = new Button("", v3);

    AnchorPane ap1 = new AnchorPane();
    AnchorPane.setTopAnchor(b1, 300.0);
    AnchorPane.setLeftAnchor(b1, 200.0);
    root.setLeft(ap1);
    ap1.getChildren().addAll(b1);

    AnchorPane ap2 = new AnchorPane();
    AnchorPane.setTopAnchor(b2, 300.0);
    AnchorPane.setRightAnchor(b2, 150.0);
    root.setCenter(ap2);
    ap2.getChildren().addAll(b2);

    AnchorPane ap3 = new AnchorPane();
    AnchorPane.setTopAnchor(b3, 300.0);
    AnchorPane.setRightAnchor(b3, 200.0);
    root.setRight(ap3);
    ap3.getChildren().addAll(b3);

    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    final Stage stage = new Stage();

    b1.setOnAction(e -> addOrEditData("add", stage));
    b2.setOnAction(e -> addOrEditData("edit", stage));
    b3.setOnAction(e -> removeData(stage));

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    root.setBottom(returns);
    returns.setOnAction(e -> exitCurrScreen(stage));

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(primaryStage);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Prompts user to enter data they would like to add or edit.
   * 
   * @author Yiduo Wang
   * @param action       - whether add or edit
   * @param primaryStage - previous stage
   */
  public void addOrEditData(String action, Stage primaryStage) {
    BorderPane root = new BorderPane();
    Label prompt =
        new Label("Please enter the milk information you would like to " + action + " below: ");
    prompt.setFont(new Font("Arial", 30));
    root.setTop(prompt);

    VBox v1 = new VBox();
    HBox h1 = new HBox();
    Label farmName = new Label("Please enter the farm id: ");
    TextField nameInput = new TextField();
    h1.getChildren().addAll(farmName, nameInput);

    HBox h2 = new HBox();
    Label date = new Label("Please select the date of the milk data: ");
    List<String> days = new ArrayList<String>();
    for (int i = 1; i <= 31; i++) {
      days.add(Integer.toString(i));
    }
    ComboBox<String> cb1 = new ComboBox<String>(FXCollections.observableArrayList(days));

    ObservableList<String> months =
        FXCollections.observableArrayList("January", "Feburary", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December");
    ComboBox<String> cb2 = new ComboBox<String>(months);

    ObservableList<String> years = FXCollections.observableArrayList("2011", "2012", "2013", "2014",
        "2015", "2016", "2017", "2018", "2019");
    ComboBox<String> cb3 = new ComboBox<String>(years);
    h2.getChildren().addAll(date, cb1, cb2, cb3);

    HBox h3 = new HBox();
    Label milkWeight = new Label("Please enter the new milk weight data: ");
    TextField milkInput = new TextField();
    h3.getChildren().addAll(milkWeight, milkInput);

    Button submit = new Button("Submit");

    v1.getChildren().addAll(h1, h2, h3, submit);
    root.setCenter(v1);

    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    final Stage stage = new Stage();

    submit.setOnAction(e -> success(stage));

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    root.setBottom(returns);
    returns.setOnAction(e -> exitCurrScreen(stage));

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(primaryStage);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Prompts user to enter information to remove a specific milk data
   * 
   * @author Yiduo Wang
   * @param primaryStage - previous stage
   */
  public void removeData(Stage primaryStage) {
    BorderPane root = new BorderPane();
    Label message =
        new Label("Please enter the milk information you would like to " + "remove below: ");
    message.setFont(new Font("Arial", 30));
    root.setTop(message);

    VBox v1 = new VBox();
    HBox h1 = new HBox();
    Label farmName = new Label("Please enter the farm id: ");
    TextField nameInput = new TextField();
    h1.getChildren().addAll(farmName, nameInput);

    HBox h2 = new HBox();
    Label date = new Label("Please select the date of the milk data: ");
    List<String> days = new ArrayList<String>();
    for (int i = 1; i <= 31; i++) {
      days.add(Integer.toString(i));
    }
    ComboBox<String> cb1 = new ComboBox<String>(FXCollections.observableArrayList(days));

    ObservableList<String> months =
        FXCollections.observableArrayList("January", "Feburary", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December");
    ComboBox<String> cb2 = new ComboBox<String>(months);

    ObservableList<String> years = FXCollections.observableArrayList("2011", "2012", "2013", "2014",
        "2015", "2016", "2017", "2018", "2019");
    ComboBox<String> cb3 = new ComboBox<String>(years);
    h2.getChildren().addAll(date, cb1, cb2, cb3);

    Button submit = new Button("Submit");

    v1.getChildren().addAll(h1, h2, submit);
    root.setCenter(v1);

    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    final Stage stage = new Stage();

    submit.setOnAction(e -> success(stage));

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    root.setBottom(returns);
    returns.setOnAction(e -> exitCurrScreen(stage));

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(primaryStage);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Displays a success message for updating milk data information
   * 
   * @author Yiduo Wang
   * @param primaryStage - previous stage
   */
  public void success(Stage primaryStage) {
    BorderPane root = new BorderPane();
    root.setTop(new Label(""));

    VBox v1 = new VBox();
    Label success = new Label("Success!! The milk data information has been updated.");
    success.setFont(new Font("Arial", 30));
    Image smile = new Image("smile.jpg", 100, 100, false, false);
    v1.getChildren().addAll(success, new ImageView(smile));
    root.setCenter(v1);

    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    final Stage stage = new Stage();

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    returns.setOnAction(e -> exitCurrScreen(stage));
    root.setBottom(returns);

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(primaryStage);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Displays statistics in a table for one farm for a specific date.
   * 
   * @author Kexin Chen
   * @param primaryStage - previous stage
   */
  public void showStatsOneFarm(Stage primaryStage) {
    Stage stage = new Stage();
    BorderPane root = new BorderPane();

    Label label1 = new Label("Farm");
    TextField nameInput = new TextField();
    nameInput.setPromptText("Enter a farm id");
    Label label2 = new Label("'s data on");

    // populate the list of days in a month
    List<String> dayList = new ArrayList<String>();
    for (int i = 1; i <= 31; i++) {
      dayList.add(Integer.toString(i));
    }
    ComboBox<String> days = new ComboBox<String>(FXCollections.observableArrayList(dayList));

    ObservableList<String> monthList =
        FXCollections.observableArrayList("January", "Feburary", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December");
    ComboBox<String> months = new ComboBox<String>(monthList);

    TextField year_tf = new TextField();
    year_tf.setPromptText("Enter a year");
    Button submit = new Button("OK");

    // will use the farm id, year, month, day information to
    // obtain data to populate the table
    
    


    HBox hbox_top = new HBox(5);
    hbox_top.getChildren().addAll(label1, nameInput, label2, days, months, year_tf, submit);
    root.setTop(hbox_top);

    TableView table = new TableView<>();
    TableColumn<String, Data> column1 = new TableColumn<>("Date");
    column1.setCellValueFactory(new PropertyValueFactory<>("date"));

    TableColumn<String, Data> column2 = new TableColumn<>("Weight");
    column2.setCellValueFactory(new PropertyValueFactory<>("weight"));

    table.getColumns().add(column1);
    table.getColumns().add(column2);
    table.setPlaceholder(new Label("No rows to display"));
    root.setCenter(table);

    HBox hbox_bottom = new HBox(100);
    // click button and add/edit/remove
    Button button1 = new Button("add");
    button1.setOnAction(e -> addOrEditData("add", stage));
    Button button2 = new Button("edit");
    button2.setOnAction(e -> addOrEditData("edit", stage));
    Button button3 = new Button("remove");
    button3.setOnAction(e -> removeData(stage));

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    returns.setOnAction(e -> exitCurrScreen(stage));

    hbox_bottom.getChildren().addAll(returns, button1, button2, button3);
    root.setBottom(hbox_bottom);

    submit.setOnAction(e -> {
      // get farm id
      String id = nameInput.getText();
      // get user input year
      Integer year = Integer.parseInt(year_tf.getText());
      // get user input month
      Integer month = Integer.parseInt(months.getSelectionModel().getSelectedItem());
      // get user input day
      Integer day = Integer.parseInt(days.getSelectionModel().getSelectedItem());

      // get list of date-weight pair to fill table
      DataForTable tableData = getTableData(e, id, year, month, day);
      // fill the table
        table.getItems().add(tableData);

      
    });

    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(primaryStage);
    stage.setScene(scene);
    stage.show();
  }

/**
 * Helper method to get the row of data for a farm on one day
 * @param e
 * @param id
 * @param year
 * @param month
 * @param day
 * @return
 * @author Kexin Chen
 */
  private DataForTable getTableData(Event e, String id, int year, int month, int day) {

    Farm f = factory.getFarmFromID(id);
    TreeMap farmData = f.getData();
    Set<Date> dateSet = farmData.keySet(); // all the date information this farm has

    for (Date d : dateSet) {
      // get the farm date information given a year and month
      // get the weight information for that date
      // add the date-weight pair into tableData list
      if (d.getYear() == year && d.getMonth() == month && d.getDay() == day) {
        String date = d.toString();
        String weight = farmData.get(d).toString();
        DataForTable tableRowData = new DataForTable(date, weight);
        return tableRowData;
      }
    }

    


  }

  /**
   * Shows a window that let user choose dates to display statistics for all farms.
   * 
   * @author Yumeng Bai
   * @param primaryStage - previous stage
   */
  public void chooseDatesAllFarms(Stage primaryStage) {
    BorderPane root2_2 = new BorderPane();
    Label message =
        new Label("Please select the month and year you would like the program to analyze"
            + " the milk data of: ");
    message.setFont(new Font("Arial", 14));
    root2_2.setTop(message);
    HBox h1 = new HBox();
    ObservableList<String> months =
        FXCollections.observableArrayList("January", "Feburary", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December");
    ComboBox<String> cb2_2_1 = new ComboBox<String>(months);
    Button b1 = new Button("Submit");
    ObservableList<String> years = FXCollections.observableArrayList("2011", "2012", "2013", "2014",
        "2015", "2016", "2017", "2018", "2019");
    ComboBox<String> cb2_2_2 = new ComboBox<String>(years);
    h1.getChildren().addAll(cb2_2_1, cb2_2_2, b1);

    AnchorPane ap2_2_1 = new AnchorPane();
    AnchorPane.setTopAnchor(h1, 50.0);
    AnchorPane.setLeftAnchor(h1, 50.0);
    ap2_2_1.getChildren().addAll(h1);

    root2_2.setCenter(ap2_2_1);

    Scene scene2_2 = new Scene(root2_2, 600, 300);
    final Stage stageB = new Stage();
    stageB.initModality(Modality.APPLICATION_MODAL);
    stageB.initOwner(primaryStage);
    stageB.setScene(scene2_2);
    stageB.show();

    b1.setOnAction(e -> displayStatisticsAllFarms(stageB));

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    root2_2.setBottom(returns);
    returns.setOnAction(e -> exitCurrScreen(stageB));
  }

  /**
   * Displays statistics (Max, Min, Average) for all farms
   * 
   * @author Yumeng Bai
   * @param previousStage
   */
  public void displayStatisticsAllFarms(Stage previousStage) {
    BorderPane root = new BorderPane();
    Label dataMess =
        new Label("Here's the statistic for the given month and year" + " for all farms: ");
    dataMess.setFont(new Font("Arial", 20));
    root.setTop(dataMess);
    // dummy data/statistics showed
    GridPane gPane = new GridPane();
    gPane.add(new Label("           "), 4, 0);
    gPane.add(new Label("           "), 8, 0);
    gPane.add(new Label("       MAX:"), 4, 1);
    gPane.add(new Label("       9999"), 8, 1);
    gPane.add(new Label("           "), 4, 2);
    gPane.add(new Label("           "), 8, 1);
    gPane.add(new Label("           "), 4, 3);
    gPane.add(new Label("           "), 8, 3);
    gPane.add(new Label("           "), 4, 4);
    gPane.add(new Label("       MIN:"), 4, 5);
    gPane.add(new Label("       1111"), 8, 5);
    gPane.add(new Label("           "), 4, 6);
    gPane.add(new Label("           "), 4, 7);
    gPane.add(new Label("           "), 8, 7);
    gPane.add(new Label("           "), 4, 8);
    gPane.add(new Label("       AVG:"), 4, 9);
    gPane.add(new Label("       4444"), 8, 9);
    gPane.add(new Label("           "), 4, 10);

    root.setCenter(gPane);

    Scene scene2_2 = new Scene(root, 600, 300);
    final Stage stageB_A = new Stage();
    stageB_A.initModality(Modality.APPLICATION_MODAL);
    stageB_A.initOwner(previousStage);
    stageB_A.setScene(scene2_2);
    stageB_A.show();

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    root.setBottom(returns);
    returns.setOnAction(e -> exitCurrScreen(stageB_A));

  }

  /**
   * Shows a window that let user input farm id to display percent share for one farm.
   * 
   * @author Chelsea Chen
   * @param primaryStage - previous stage
   */
  public void showPercentShareOneFarm(Stage primaryStage) {
    BorderPane root = new BorderPane();
    HBox h1 = new HBox();
    Label message = new Label("Please enter the farm id: ");
    TextField nameInput = new TextField();
    Button submit = new Button("submit");
    h1.getChildren().addAll(message, nameInput, submit);
    root.setTop(h1);

    Scene scene = new Scene(root, 400, 200);
    final Stage stage = new Stage();

    submit.setOnAction(e -> showPercentOneFarmHelper(stage));

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    returns.setOnAction(e -> exitCurrScreen(stage));
    root.setBottom(returns);

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(primaryStage);
    stage.setScene(scene);
    stage.show();

  }

  /**
   * Displays percent share for one farm
   * 
   * @author Chelsea Chen
   * @param primaryStage - prev stage
   */
  public void showPercentOneFarmHelper(Stage primaryStage) {
    BorderPane root = new BorderPane();
    Label percent = new Label("Here's the percent share by farm 23: 33%");
    root.setCenter(percent);
    Scene scene = new Scene(root, 400, 200);
    final Stage stage = new Stage();

    Button gotIt = new Button("Got it!");
    gotIt.setOnAction(e -> exitCurrScreen(stage));
    root.setBottom(gotIt);

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(primaryStage);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Displays percent share for all farms with a pie chart
   * 
   * @author Yumeng Bai
   * @param primaryStage - prev stage
   */
  public void showPercentShareAllFarms(Stage primaryStage) {
    BorderPane root2_3 = new BorderPane();
    VBox v1 = new VBox();
    // dummy data/statistics displayed
    GridPane gPane = new GridPane();
    gPane.add(new Label("      Farm Name: "), 4, 0);
    gPane.add(new Label("    Percentage "), 8, 0);
    gPane.add(new Label("      Farm 1: "), 4, 1);
    gPane.add(new Label("      13% "), 8, 1);
    gPane.add(new Label("        "), 4, 2);
    gPane.add(new Label("      Farm 2: "), 4, 3);
    gPane.add(new Label("      25% "), 8, 3);
    gPane.add(new Label("        "), 4, 4);
    gPane.add(new Label("      Farm 3: "), 4, 5);
    gPane.add(new Label("      10% "), 8, 5);
    gPane.add(new Label("        "), 4, 6);
    gPane.add(new Label("      Farm 4: "), 4, 7);
    gPane.add(new Label("      22% "), 8, 7);
    gPane.add(new Label("        "), 4, 8);
    gPane.add(new Label("      Farm 5: "), 4, 9);
    gPane.add(new Label("      30% "), 8, 9);
    gPane.add(new Label("        "), 4, 10);

    ObservableList<PieChart.Data> pieChartData =
        FXCollections.observableArrayList(new PieChart.Data("Farm 1", 13),
            new PieChart.Data("Farm 2", 25), new PieChart.Data("Farm 3", 10),
            new PieChart.Data("Farm 4", 22), new PieChart.Data("Farm 5", 30));
    final PieChart chart = new PieChart(pieChartData);
    chart.setTitle("Percent Share for All Farms");

    v1.getChildren().addAll(gPane, chart);
    root2_3.setCenter(v1);
    root2_3.setTop(new Label("Percent share of each farm"));

    Scene scene2_3 = new Scene(root2_3, WINDOW_WIDTH, WINDOW_HEIGHT);
    final Stage stageC = new Stage();
    stageC.initModality(Modality.APPLICATION_MODAL);
    stageC.initOwner(primaryStage);
    stageC.setScene(scene2_3);
    stageC.show();

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    root2_3.setBottom(returns);
    returns.setOnAction(e -> exitCurrScreen(stageC));
  }

  /**
   * Generate reports. Shows a window that let user choose which report (farm report, annual report,
   * monthly report, date_range report) to generate.
   * 
   * @author Chelsea Chen
   * @param primaryStage - prev stage
   */
  public void generateReport(Stage primaryStage) {
    BorderPane root = new BorderPane();
    Label message = new Label("Please select a type of report to generate: ");
    message.setFont(new Font("Arial", 30));
    root.setTop(message);

    VBox v1 = new VBox();

    Image farm = new Image("farm_single.png", 75, 75, false, false);
    VBox vb1 = new VBox();
    vb1.getChildren().addAll(new ImageView(farm), new Label("Farm report"));
    Button farmReport = new Button("", vb1);

    Image annual = new Image("annual.png", 75, 75, false, false);
    VBox vb2 = new VBox();
    vb2.getChildren().addAll(new ImageView(annual), new Label("Annual report"));
    Button annualReport = new Button("", vb2);

    v1.getChildren().addAll(farmReport, new Label(""), new Label(""), new Label(""), new Label(""),
        new Label(""), new Label(""), annualReport);

    VBox v2 = new VBox();

    Image month = new Image("month.png", 75, 75, false, false);
    VBox vb3 = new VBox();
    vb3.getChildren().addAll(new ImageView(month), new Label("Monthly report"));
    Button monthlyReport = new Button("", vb3);

    Image dateRange = new Image("date_range.png", 75, 75, false, false);
    VBox vb4 = new VBox();
    vb4.getChildren().addAll(new ImageView(dateRange), new Label("Date-Range report"));
    Button dateRangeReport = new Button("", vb4);

    v2.getChildren().addAll(monthlyReport, new Label(""), new Label(""), new Label(""),
        new Label(""), new Label(""), new Label(""), dateRangeReport);

    AnchorPane ap1 = new AnchorPane();
    AnchorPane.setTopAnchor(v1, 200.0);
    AnchorPane.setLeftAnchor(v1, 200.0);
    root.setLeft(ap1);
    ap1.getChildren().addAll(v1);

    AnchorPane ap2 = new AnchorPane();
    AnchorPane.setTopAnchor(v2, 200.0);
    AnchorPane.setRightAnchor(v2, 200.0);
    root.setRight(ap2);
    ap2.getChildren().addAll(v2);

    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    final Stage stage = new Stage();

    farmReport.setOnAction(e -> farmReportHelper(stage));
    annualReport.setOnAction(e -> annualReportHelper(stage));
    monthlyReport.setOnAction(e -> monthlyReportHelper(stage));
    dateRangeReport.setOnAction(e -> dateRangeReportHelper(stage));

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    returns.setOnAction(e -> exitCurrScreen(stage));
    root.setBottom(returns);

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(primaryStage);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Shows a window that let user enter farm id and year to generate farm report.
   * 
   * @author Chelsea Chen
   * @param primaryStage - prev stage
   */
  public void farmReportHelper(Stage primaryStage) {
    BorderPane root = new BorderPane();
    HBox h1 = new HBox();
    HBox h2 = new HBox();
    VBox v1 = new VBox();
    Label name = new Label("Please enter a valid farm id: ");
    TextField nameInput = new TextField();
    h1.getChildren().addAll(name, nameInput);
    Label year = new Label("Please enter a valid year: ");
    TextField yearInput = new TextField();
    h2.getChildren().addAll(year, yearInput);
    Button submit = new Button("submit");
    v1.getChildren().addAll(h1, h2, submit);
    root.setTop(v1);

    Scene scene = new Scene(root, 600, 400);
    final Stage stage = new Stage();

    submit.setOnAction(e -> generateFarmReport(stage));

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    returns.setOnAction(e -> exitCurrScreen(stage));
    root.setBottom(returns);

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(primaryStage);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Generates farm report that shows total milk weight and percent share in a table.
   * 
   * @author Chelsea Chen
   * @param primaryStage - prev stage
   */
  public void generateFarmReport(Stage primaryStage) {
    BorderPane root = new BorderPane();
    Label message = new Label("Farm 66's statistics in year 2011");
    root.setTop(message);
    TableView<String> tableView = new TableView<String>();

    TableColumn<String, String> column1 = new TableColumn<>("Month");
    column1.setCellValueFactory(new PropertyValueFactory<>("month"));
    column1.setMinWidth(200);

    TableColumn<String, String> column2 = new TableColumn<>("Total Milk Weight");
    column2.setCellValueFactory(new PropertyValueFactory<>("weight"));
    column2.setMinWidth(200);

    TableColumn<String, String> column3 = new TableColumn<>("Percent total of All Farms");
    column3.setCellValueFactory(new PropertyValueFactory<>("percent"));
    column3.setMinWidth(200);

    tableView.getColumns().add(column1);
    tableView.getColumns().add(column2);
    tableView.getColumns().add(column3);

    // currently there's no data to display, will work on this later
    tableView.setPlaceholder(new Label("No rows to display"));

    VBox vbox = new VBox(tableView);
    root.setCenter(vbox);

    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    final Stage stage = new Stage();

    HBox hbox = new HBox(600);
    Image exportImage = new Image("export.png", 30, 30, false, false);
    ImageView exportView = new ImageView(exportImage);
    Button export = new Button("export to file", exportView);
    export.setOnAction(e -> outputName(stage));

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    returns.setOnAction(e -> exitCurrScreen(stage));
    hbox.getChildren().addAll(returns, export);
    root.setBottom(hbox);

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(primaryStage);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Let user enter a year to generate annual report
   * 
   * @author Chelsea Chen
   * @param primaryStage - prev stage
   */
  public void annualReportHelper(Stage primaryStage) {
    BorderPane root = new BorderPane();
    VBox v1 = new VBox();
    Label message = new Label("Please enter a valid year: ");
    TextField yearInput = new TextField();
    Button submit = new Button("submit");
    v1.getChildren().addAll(message, yearInput, submit);
    root.setCenter(v1);

    Scene scene = new Scene(root, 600, 400);
    final Stage stage = new Stage();

    submit.setOnAction(e -> generateAnnualReport(stage));

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    returns.setOnAction(e -> exitCurrScreen(stage));
    root.setBottom(returns);

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(primaryStage);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Generates annual report that shows total milk weight and percent share for all farms in a
   * specific year in a table.
   * 
   * @author Chelsea Chen
   * @param primaryStage - prev stage
   */
  public void generateAnnualReport(Stage primaryStage) {
    BorderPane root = new BorderPane();
    Label message = new Label("All farm's statistics in year 2001");
    root.setTop(message);
    TableView<String> tableView = new TableView<String>();

    TableColumn<String, String> column1 = new TableColumn<>("Farm ID");
    column1.setCellValueFactory(new PropertyValueFactory<>("name"));
    column1.setMinWidth(200);

    TableColumn<String, String> column2 = new TableColumn<>("Total Milk Weight");
    column2.setCellValueFactory(new PropertyValueFactory<>("weight"));
    column2.setMinWidth(200);

    TableColumn<String, String> column3 = new TableColumn<>("Percent total of All Farms");
    column3.setCellValueFactory(new PropertyValueFactory<>("percent"));
    column3.setMinWidth(200);

    tableView.getColumns().add(column1);
    tableView.getColumns().add(column2);
    tableView.getColumns().add(column3);

    // currently no data to display, will work on this later
    tableView.setPlaceholder(new Label("No rows to display"));

    VBox vbox = new VBox(tableView);
    root.setCenter(vbox);

    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    final Stage stage = new Stage();

    HBox hbox = new HBox(600);
    Image exportImage = new Image("export.png", 30, 30, false, false);
    ImageView exportView = new ImageView(exportImage);
    Button export = new Button("export to file", exportView);
    export.setOnAction(e -> outputName(stage));

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    returns.setOnAction(e -> exitCurrScreen(stage));
    hbox.getChildren().addAll(returns, export);
    root.setBottom(hbox);

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(primaryStage);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Let user select a specific month and year for monthly report.
   * 
   * @author Chelsea Chen
   * @param primaryStage - prev stage
   */
  public void monthlyReportHelper(Stage primaryStage) {
    BorderPane root = new BorderPane();
    Scene scene = new Scene(root, 600, 400);
    final Stage stage = new Stage();

    VBox v1 = new VBox();
    Label message = new Label("Please select/enter a valid month and year: ");
    TextField yearInput = new TextField();

    ObservableList<String> months =
        FXCollections.observableArrayList("January", "Feburary", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December");
    ComboBox<String> cb = new ComboBox<String>(months);

    Button submit = new Button("submit");
    v1.getChildren().addAll(message, cb, yearInput, submit);
    root.setCenter(v1);
    submit.setOnAction(e -> generateMonthlyReport(stage));

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    returns.setOnAction(e -> exitCurrScreen(stage));
    root.setBottom(returns);

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(primaryStage);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Generates monthly report that shows total milk weight and percent share of all farms in a
   * specific month and year in a table.
   * 
   * @author Chelsea Chen
   * @param primaryStage - prev stage
   */
  public void generateMonthlyReport(Stage primaryStage) {
    BorderPane root = new BorderPane();
    Label message = new Label("All farm's statistics in July of 2010");
    root.setTop(message);
    TableView<String> tableView = new TableView<String>();

    TableColumn<String, String> column1 = new TableColumn<>("Farm ID");
    column1.setCellValueFactory(new PropertyValueFactory<>("name"));
    column1.setMinWidth(200);

    TableColumn<String, String> column2 = new TableColumn<>("Total Milk Weight");
    column2.setCellValueFactory(new PropertyValueFactory<>("weight"));
    column2.setMinWidth(200);

    TableColumn<String, String> column3 = new TableColumn<>("Percent total of All Farms");
    column3.setCellValueFactory(new PropertyValueFactory<>("percent"));
    column3.setMinWidth(200);

    tableView.getColumns().add(column1);
    tableView.getColumns().add(column2);
    tableView.getColumns().add(column3);

    tableView.setPlaceholder(new Label("No rows to display"));

    VBox vbox = new VBox(tableView);
    root.setCenter(vbox);

    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    final Stage stage = new Stage();

    HBox hbox = new HBox(600);
    Image exportImage = new Image("export.png", 30, 30, false, false);
    ImageView exportView = new ImageView(exportImage);
    Button export = new Button("export to file", exportView);
    export.setOnAction(e -> outputName(stage));

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    returns.setOnAction(e -> exitCurrScreen(stage));
    hbox.getChildren().addAll(returns, export);
    root.setBottom(hbox);

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(primaryStage);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Let user select the start and end date for date range report
   * 
   * @author Chelsea Chen
   * @param primaryStage - prev stage
   */
  public void dateRangeReportHelper(Stage primaryStage) {
    BorderPane root = new BorderPane();
    Scene scene = new Scene(root, 600, 400);
    final Stage stage = new Stage();

    Label message = new Label("Please select the start and end date for the report: ");

    HBox h1 = new HBox(10);
    HBox h2 = new HBox(10);
    VBox v1 = new VBox(30);
    Label l1 = new Label("Start date: ");
    Label l2 = new Label("End date: ");
    ObservableList<String> months =
        FXCollections.observableArrayList("January", "Feburary", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December");
    ComboBox<String> cb1 = new ComboBox<String>(months);
    TextField yearInput1 = new TextField();
    ComboBox<String> cb2 = new ComboBox<String>(months);
    TextField yearInput2 = new TextField();
    h1.getChildren().addAll(l1, cb1, yearInput1);
    h2.getChildren().addAll(l2, cb2, yearInput2);
    Button submit = new Button("submit");
    v1.getChildren().addAll(message, h1, h2, submit);
    root.setCenter(v1);
    submit.setOnAction(e -> generateDateRangeReport(stage));

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    returns.setOnAction(e -> exitCurrScreen(stage));
    root.setBottom(returns);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(primaryStage);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Generates date_range report that shows total milk weight and percent share from a specific
   * start date to a specific end date for all farms in a table.
   * 
   * @author Chelsea Chen
   * @param primaryStage - prev stage
   */
  public void generateDateRangeReport(Stage primaryStage) {
    BorderPane root = new BorderPane();
    Label message = new Label("All farm's statistics from September 2011 to January 2013");
    root.setTop(message);
    TableView<String> tableView = new TableView<String>();

    TableColumn<String, String> column1 = new TableColumn<>("Farm ID");
    column1.setCellValueFactory(new PropertyValueFactory<>("name"));
    column1.setMinWidth(200);

    TableColumn<String, String> column2 = new TableColumn<>("Total Milk Weight");
    column2.setCellValueFactory(new PropertyValueFactory<>("weight"));
    column2.setMinWidth(200);

    TableColumn<String, String> column3 = new TableColumn<>("Percent total of All Farms");
    column3.setCellValueFactory(new PropertyValueFactory<>("percent"));
    column3.setMinWidth(200);

    tableView.getColumns().add(column1);
    tableView.getColumns().add(column2);
    tableView.getColumns().add(column3);

    tableView.setPlaceholder(new Label("No rows to display"));

    VBox vbox = new VBox(tableView);
    root.setCenter(vbox);

    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    final Stage stage = new Stage();

    HBox hbox = new HBox(600);
    Image exportImage = new Image("export.png", 30, 30, false, false);
    ImageView exportView = new ImageView(exportImage);
    Button export = new Button("export to file", exportView);
    export.setOnAction(e -> outputName(stage));

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    returns.setOnAction(e -> exitCurrScreen(stage));
    hbox.getChildren().addAll(returns, export);
    root.setBottom(hbox);

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(primaryStage);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Let user enter a file name as the output file. This does not generate the output file itself ->
   * only shows how GUI is displayed
   * 
   * @author Chelsea Chen
   * @param primaryStage - prev stage
   */
  public void outputName(Stage primaryStage) {
    BorderPane root = new BorderPane();
    VBox v1 = new VBox();
    Label message = new Label("Please provide the name of the output file: ");
    TextField output = new TextField();
    Button submit = new Button("submit");
    v1.getChildren().addAll(message, output, submit);

    root.setCenter(v1);
    Scene scene = new Scene(root, 600, 400);
    final Stage stage = new Stage();

    submit.setOnAction(e -> successReport(stage));

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    Button returns = new Button("return to previous screen", returnPrev);
    returns.setOnAction(e -> exitCurrScreen(stage));
    root.setBottom(returns);

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(primaryStage);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Prints out the success message for saving output file and goodbye message for the user.
   * 
   * @author Ruiwen Wang
   * @param primaryStage - prev stage
   */
  public void successReport(Stage primaryStage) {
    BorderPane root = new BorderPane();
    Label successMessage =
        new Label("Your report is successfully generated" + " to the output file you requested."
            + "\n" + "You can find the" + " file in the same program folder.");
    Label goodBye =
        new Label("Thank you so much for using the Milk Analyzer!" + "\n" + "Have a great day!");
    Image smile = new Image("smile.jpg", 50, 50, false, false);

    VBox v1 = new VBox();
    v1.getChildren().addAll(successMessage, new Label(""), goodBye, new ImageView(smile));

    root.setCenter(v1);

    Scene scene = new Scene(root, 600, 400);
    final Stage stage = new Stage();

    Image returnToPrev = new Image("return.png", 30, 30, false, false);
    Image exit = new Image("exit.png", 30, 30, false, false);
    ImageView returnPrev = new ImageView(returnToPrev);
    ImageView exitImage = new ImageView(exit);
    Button returns = new Button("return to previous screen", returnPrev);
    Button exitProgram = new Button("exit", exitImage);

    HBox h1 = new HBox(300);
    h1.getChildren().addAll(returns, exitProgram);

    returns.setOnAction(e -> exitCurrScreen(stage));
    exitProgram.setOnAction(e -> Platform.exit());

    root.setBottom(h1);

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(primaryStage);
    stage.setScene(scene);
    stage.show();
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
