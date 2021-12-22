import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ControlPanel extends Application {

    private final ParseNewsNsn parseNewsNsn;
    private final ParseNewsRbc parseNewsRbc;
    private final ParseNewsNN parseNewsNN;
    private final Stage stage1 = new Stage();

    public ControlPanel(ParseNewsNsn parseNewsNsn, ParseNewsRbc parseNewsRbc, ParseNewsNN parseNewsNN) {
        this.parseNewsNsn = parseNewsNsn;
        this.parseNewsRbc = parseNewsRbc;
        this.parseNewsNN = parseNewsNN;
    }


    @Override
    public void start(Stage stage) throws Exception {

        int weight = 550;
        int height = 75;

        VBox root = new VBox(10);

        Scene scene = new Scene(root, weight, height);

        new Thread(parseNewsNsn).start();
        new Thread(parseNewsNN).start();
        new Thread(parseNewsRbc).start();

        HBox hboxStart = new HBox();
        hboxStart.setSpacing(5);
        hboxStart.setPadding(new Insets(5));


        TextField textField = new TextField();

        Image image = new Image("C:\\Users\\user\\Desktop\\FX\\search.jpg");

        Button getNewsNsnButton = new Button("Get news NSN");
        Button getNewsRbcButton = new Button("Get news RBC");
        Button getNewsNNButton = new Button("Get news NN");
        Button closeProgramButton = new Button("Close");
        Button searchButton = new Button("", new ImageView(image));

        hboxStart.getChildren().addAll(textField, searchButton, getNewsNsnButton, getNewsRbcButton, getNewsNNButton, closeProgramButton);
        root.getChildren().add(hboxStart);

        //search button
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Searching searching = new Searching(parseNewsNsn, parseNewsRbc, parseNewsNN, textField.getText().split(" "), stage1, scene);

                try {
                    searching.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        //button for show news
        getNewsNsnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GetNews getNSN = new GetNews(stage1, parseNewsNsn, scene);

                try {
                    getNSN.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        getNewsRbcButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GetNews getNSN = new GetNews(stage1, parseNewsRbc, scene);

                try {
                    getNSN.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        getNewsNNButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GetNews getNSN = new GetNews(stage1, parseNewsNN, scene);

                try {
                    getNSN.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //close program
        closeProgramButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });

        //show start page
        stage.setTitle("News panel");
        stage.setScene(scene);
        stage.show();

    }

}
