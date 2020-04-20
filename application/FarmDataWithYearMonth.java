package application;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import data.Farm;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

public class FarmDataWithYearMonth {
  
  private static final String add = "Add";
  private static final String edit = "Edit";
  private static final String remove = "Remove";
  private static final String TITLE = "Farm data in specified month";

  private String farmID;
  
  public FarmDataWithYearMonth(String id) {
    this.farmID = id;
  }
  
  public void ShowFarmDataTable() {
    Stage stage = new Stage();
    BorderPane root = new BorderPane();
    
    
    String top = "Farm " + farmID + "'s " + "data on";
    Label top_label = new Label(top);

    // populate the list of days in a month
    List<String> days = new ArrayList<String>();
    for(int i = 1; i <= 31; i ++) {
      days.add(Integer.toString(i));
    }
    ComboBox<String> months = new ComboBox<String>(FXCollections.observableArrayList(days));
    // months.setOnAction(); ????????
    // get the selected item
    String month = months.getSelectionModel().getSelectedItem();
    
    
    TextField year_tf = new TextField();
    year_tf.setPromptText("Enter a year");
    Button submit = new Button("OK");
    submit.setOnAction(e -> {
      String year = year_tf.getText();      
  });
    
    // year is got, use farm data to populate the table
    
    HBox hbox_top = new HBox(5);
    hbox_top.getChildren().addAll(top_label, months, year_tf);   
    root.setTop(hbox_top);
    
    
    
    TableView<Farm> table = new TableView<Farm>();
    TableColumn<Farm, String> column1 = new TableColumn<Farm,String>("Date");
    TableColumn<Farm, String> column2 = new TableColumn<Farm,String>("Weight");
    table.getColumns().add(column1);
    table.getColumns().add(column2);
    root.setCenter(table);

    
    HBox hbox_bottom = new HBox(100);
    // click button and add/edit/remove
    Button button1 = new Button(add);
    button1.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent e) {
      }
  });
    Button button2 = new Button(edit);
    button2.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent e) {
      }
  });
    Button button3 = new Button(remove);
    button3.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent e) {
      }
  });
    hbox_bottom.getChildren().addAll(button1, button2, button3);
    root.setBottom(hbox_bottom);

    
    
    Scene scene = new Scene(root, 500, 500);
    stage.setTitle(TITLE);
    stage.setScene(scene);
    stage.show();
    
  }

}
