package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private TextField january, february, march, april;
    private VBox vbox;
    private MenuBar menuBar;

    private String chartType = "bar";
    private boolean charted = false;
    private Double rainfall1, rainfall2, rainfall3, rainfall4;

    @Override
    public void start(Stage primaryStage) throws Exception{

        // NODES
        Label prompt = new Label("Enter the amount of rainfall (inches) in your area over a period of four months:");

        Label month1 = new Label("January");
        Label month2 = new Label("February");
        Label month3 = new Label("March");
        Label month4 = new Label("April");

        january = new TextField();
        february = new TextField();
        march = new TextField();
        april = new TextField();

        Button button = new Button("Submit");


        // BUTTON HANDLER
        button.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            String text1 = january.getText();
            String text2 = february.getText();
            String text3 = march.getText();
            String text4 = april.getText();

            List<String> list = new ArrayList<>();
            list.add(text1);
            list.add(text2);
            list.add(text3);
            list.add(text4);

            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).matches("[0-9.]+")) {
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText("Oops!");
                    alert.setContentText("Please enter a valid number for each of the months.");
                    alert.show();
                }
                else {
                    rainfall1 = Double.parseDouble(text1);
                    rainfall2 = Double.parseDouble(text2);
                    rainfall3 = Double.parseDouble(text3);
                    rainfall4 = Double.parseDouble(text4);

                    createChart();
                    primaryStage.getScene().setRoot(vbox);
                }
            }
        });


        // LAYOUT CONTAINERS
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(10);
        grid.add(month1, 6, 0);
        grid.add(january, 8, 0);
        grid.add(month2, 6, 2);
        grid.add(february, 8, 2);
        grid.add(month3, 6, 4);
        grid.add(march, 8, 4);
        grid.add(month4, 6, 6);
        grid.add(april, 8, 6);


        // MENU
        // MENU BAR
        menuBar = new MenuBar();

        Menu file = new Menu("File");
        Menu view = new Menu("View");
        menuBar.getMenus().addAll(file, view);

        // FILE MENU
        MenuItem reset = new MenuItem("New");
        reset.setOnAction(e -> {
            charted = false;
            vbox = new VBox(25, menuBar, prompt, grid, button);
            vbox.setAlignment(Pos.CENTER);
            primaryStage.getScene().setRoot(vbox);
        });

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> {
            primaryStage.close();
        });

        file.getItems().addAll(reset, exit);

        // VIEW MENU
        RadioMenuItem barChart = new RadioMenuItem("Bar Chart");
        barChart.setSelected(true);
        barChart.setOnAction(e -> {
            barChart.setSelected(true);
            chartType = "bar";
            if (charted) {
                createChart();
                primaryStage.getScene().setRoot(vbox);
            }
        });
        RadioMenuItem scatterChart = new RadioMenuItem("Scatter Chart");
        scatterChart.setOnAction(e -> {
            scatterChart.setSelected(true);
            chartType = "scatter";
            if (charted) {
                createChart();
                primaryStage.getScene().setRoot(vbox);
            }
        });
        RadioMenuItem lineChart = new RadioMenuItem("Line Chart");
        lineChart.setOnAction(e -> {
            lineChart.setSelected(true);
            chartType = "line";
            if (charted) {
                createChart();
                primaryStage.getScene().setRoot(vbox);
            }
        });

        ToggleGroup viewCharts = new ToggleGroup();
        barChart.setToggleGroup(viewCharts);
        scatterChart.setToggleGroup(viewCharts);
        lineChart.setToggleGroup(viewCharts);

        view.getItems().addAll(barChart, scatterChart, lineChart);


        // SET STAGE AND SCENE
        vbox = new VBox(25, menuBar, prompt, grid, button);
        vbox.setAlignment(Pos.CENTER);
        VBox.setMargin(prompt, new Insets(0, 50, 0, 50));
        VBox.setMargin(button, new Insets(20, 0, 20, 0));

        primaryStage.setTitle("Rainfall Chart");
        Scene sc = new Scene(vbox);
        primaryStage.setScene(sc);
        primaryStage.show();
    }


    private MenuBar returnMenu() {
        return menuBar;
    }


    private void createChart() {
        charted = true;
        CategoryAxis hAxis = new CategoryAxis();
        hAxis.setLabel("Months");
        NumberAxis vAxis = new NumberAxis();
        vAxis.setLabel("Rainfall (in)");

        XYChart.Series<String, Number> points = new
                XYChart.Series<>();

        points.getData().add( new XYChart.Data<>("January", rainfall1));
        points.getData().add( new XYChart.Data<>("February", rainfall2));
        points.getData().add( new XYChart.Data<>("March", rainfall3));
        points.getData().add( new XYChart.Data<>("April", rainfall4));

        switch (chartType) {
            case "bar":
                BarChart<String, Number> barChart = new BarChart<>(hAxis, vAxis);
                barChart.setTitle("Rainfall in Four Months");
                barChart.getData().add(points);
                barChart.setLegendVisible(false);
                vbox = new VBox(returnMenu(), barChart);
                break;
            case "scatter":
                ScatterChart<String, Number> scatterChart = new ScatterChart<>(hAxis, vAxis);
                scatterChart.setTitle("Rainfall in Four Months");
                scatterChart.getData().add(points);
                scatterChart.setLegendVisible(false);
                vbox = new VBox(returnMenu(), scatterChart);
                break;
            case "line":
                LineChart<String, Number> lineChart = new LineChart<>(hAxis, vAxis);
                lineChart.setTitle("Rainfall in Four Months");
                lineChart.getData().add(points);
                lineChart.setLegendVisible(false);
                vbox = new VBox(returnMenu(), lineChart);
                break;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
