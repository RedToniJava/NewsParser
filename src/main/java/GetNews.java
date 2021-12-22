import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

public class GetNews extends Application {

    private final ParseNewsNsn parseNewsNsn;
    private final Stage stage1;
    private final Scene scene;


    public GetNews(Stage stage1, ParseNewsNsn parseNewsNsn, Scene scene) {
        this.stage1 = stage1;
        this.parseNewsNsn = parseNewsNsn;
        this.scene = scene;

    }

    @Override
    public void start(Stage stage) throws Exception {

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

        GridPane grid = new GridPane();

        Label heading = new Label("Heading");
        Label text_news = new Label("Text news");

        grid.getColumnConstraints().add(new ColumnConstraints(550));
        grid.getColumnConstraints().add(new ColumnConstraints(150));
        GridPane.setColumnIndex(heading, 0);
        GridPane.setColumnIndex(text_news, 1);
        grid.getRowConstraints().add(new RowConstraints(30));
        grid.add(hboxPage1, 0, 0);


        parseNewsNsn.getLastNewsUrl().forEach(u -> {

            Button textButton = new Button("Text");
            GridPane.setMargin(textButton, new Insets(5));
            Hyperlink hyperlink = new Hyperlink(u);
            hyperlink.setText(parseNewsNsn.getLastNews().get(parseNewsNsn.getLastNewsUrl().indexOf(u)));
            hyperlink.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    getHostServices().showDocument(u);
                }
            });

            //show text news
            textButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    NewsText newsText = new NewsText(parseNewsNsn, parseNewsNsn.getLastNewsUrl().indexOf(u));
                    try {
                        newsText.start(stage1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            //add buttons link and text
            grid.add(textButton, 1, 1 + parseNewsNsn.getLastNewsUrl().indexOf(u));
            grid.add(hyperlink, 0, 1 + parseNewsNsn.getLastNewsUrl().indexOf(u));


        });


        GridPane.setMargin(closeProgramButton, new Insets(45));
        grid.add(backButton, 0, 0);
        grid.add(closeProgramButton, 1, 0);

        Scene scene1 = new Scene(grid, 700, 500);

        stage.setTitle("News");
        stage.setScene(scene1);
        stage.show();


    }

}

    


