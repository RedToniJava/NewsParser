import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;


public class Searching extends Application {
    private ArrayList<Integer> findNews = new ArrayList<>();

    private ParseNewsNsn parseNewsNsn;
    private ParseNewsNN parseNewsNN;
    private ParseNewsRbc parseNewsRbc;
    private String[] strings;
    private Scene scene;
    private Stage stage1;
    private int num = 1;

    //construct class Searching
    public Searching(ParseNewsNsn parseNewsNsn, ParseNewsRbc parseNewsRbc, ParseNewsNN parseNewsNN, String[] strings, Stage stage1, Scene scene) {
        this.parseNewsNsn = parseNewsNsn;
        this.parseNewsRbc = parseNewsRbc;
        this.parseNewsNN = parseNewsNN;
        this.strings = strings;
        this.stage1 = stage1;
        this.scene = scene;

    }

    private ArrayList<Integer> startFind() {
        return findNews;
    }


    @Override
    public void start(Stage stage) throws Exception {

        //Create new buttons (close and back)
        HBox hboxPage1 = new HBox();
        hboxPage1.setSpacing(5);
        hboxPage1.setPadding(new Insets(5));
        Button closeProgramButton = new Button("Close");
        Button backButton = new Button("Back");

        closeProgramButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                stage.close();
            }
        });
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                stage.setScene(scene);
                stage.show();
            }
        });
        //Create  grid panel for location buttons
        GridPane grid = new GridPane();

        Label heading = new Label("Heading");
        Label text_news = new Label("Text news");

        grid.getColumnConstraints().add(new ColumnConstraints(550));
        grid.getColumnConstraints().add(new ColumnConstraints(150));
        GridPane.setColumnIndex(heading, 0);
        GridPane.setColumnIndex(text_news, 1);
        grid.getRowConstraints().add(new RowConstraints(30));
        GridPane.setMargin(closeProgramButton, new Insets(45));
        grid.add(backButton, 0, 0);
        grid.add(closeProgramButton, 1, 0);

        //number of string for link and text button
        num++;

        //searching
        search(parseNewsNsn);
        startFind().forEach(i -> {
            Button textButton = new Button("Text");
            GridPane.setMargin(textButton, new Insets(5));

            //create links and text button
            Hyperlink hyperlink = new Hyperlink(parseNewsNsn.getLastNewsUrl().get(i));
            hyperlink.setText("NSN: " + parseNewsNsn.getLastNews().get(i));
            hyperlink.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    getHostServices().showDocument(parseNewsNsn.getLastNewsUrl().get(i));
                }
            });

            textButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent actionEvent) {
                    NewsText newsText = new NewsText(parseNewsNsn, i);
                    try {
                        newsText.start(stage1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            //add link and text button
            grid.add(textButton, 1, num + parseNewsNsn.getLastNewsUrl().indexOf(i));
            grid.add(hyperlink, 0, num + parseNewsNsn.getLastNewsUrl().indexOf(i));
            num++;

        });

        findNews.clear();

        search(parseNewsRbc);
        startFind().forEach(i -> {
            Button textButton = new Button("Text");
            GridPane.setMargin(textButton, new Insets(5));

            //create links and text button
            Hyperlink hyperlink1 = new Hyperlink(parseNewsRbc.getLastNewsUrl().get(i));
            hyperlink1.setText("RBC: " + parseNewsRbc.getLastNews().get(i));
            hyperlink1.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    getHostServices().showDocument(parseNewsRbc.getLastNewsUrl().get(i));
                }
            });
            textButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    NewsText newsText = new NewsText(parseNewsRbc, i);
                    try {
                        newsText.start(stage1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            //add link and text button
            grid.add(textButton, 1, num + parseNewsRbc.getLastNewsUrl().indexOf(i));
            grid.add(hyperlink1, 0, num + parseNewsRbc.getLastNewsUrl().indexOf(i));
            num++;


        });

        findNews.clear();

        search(parseNewsNN);
        startFind().forEach(i -> {
            Button textButton = new Button("Text");
            GridPane.setMargin(textButton, new Insets(5));

            //create links and text button
            Hyperlink hyperlink2 = new Hyperlink(parseNewsNN.getLastNewsUrl().get(i));
            hyperlink2.setText("NN: " + parseNewsNN.getLastNews().get(i));
            hyperlink2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    getHostServices().showDocument(parseNewsNN.getLastNewsUrl().get(i));
                }
            });

            textButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    NewsText newsText = new NewsText(parseNewsNN, i);

                    try {
                        newsText.start(stage1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            //add link and text button
            grid.add(textButton, 1, num + parseNewsNN.getLastNewsUrl().indexOf(i));
            grid.add(hyperlink2, 0, num + parseNewsNN.getLastNewsUrl().indexOf(i));
            num++;


        });

        findNews.clear();
        Scene scene1 = new Scene(grid, 650, 500);

        stage.setTitle("News");
        stage.setScene(scene1);
        stage.show();

    }

    //replacing string for searching
    private void search(ParseNewsNsn parseNews) {

        parseNews.getLastNews().forEach(n -> {
            n.replaceAll("[.,;\":]", "");

            String[] str = n.split(" ");
            searchingInHeading(n, str, parseNews);

        });
        parseNews.getTextsNews().forEach(n -> {
            n.replaceAll("[.,;\":]", "");

            String[] str = n.split(" ");
            searchingInText(n, str, parseNews);

        });
    }

    //searching in text news
    private void searchingInText(String n, String[] news, ParseNewsNsn parseNews) {
        Arrays.stream(strings).forEach(s -> {
            Arrays.stream(news).forEach(word -> {
                if (s.toUpperCase(Locale.ROOT).equals(word.toUpperCase(Locale.ROOT))) {
                    if (!findNews.contains(parseNews.getTextsNews().indexOf(n))) {
                        findNews.add(parseNews.getTextsNews().indexOf(n));
                    }
                }
            });
        });
    }

    //searching in heading news
    private void searchingInHeading(String n, String[] news, ParseNewsNsn parseNews) {
        Arrays.stream(strings).forEach(s -> {
            Arrays.stream(news).forEach(word -> {
                if (s.toUpperCase(Locale.ROOT).equals(word.toUpperCase(Locale.ROOT))) {
                    if (!findNews.contains(parseNews.getLastNews().indexOf(n))) {
                        findNews.add(parseNews.getLastNews().indexOf(n));
                    }
                }
            });
        });
    }


}
