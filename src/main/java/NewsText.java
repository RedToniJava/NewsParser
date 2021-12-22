import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class NewsText extends Application {
    private ParseNewsNsn parseNewsNsn;
    private int numberNews;

    public NewsText(ParseNewsNsn parseNewsNsn, int numberNews) {
        this.parseNewsNsn = parseNewsNsn;
        this.numberNews = numberNews;

    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox(10);

        TextArea textArea = new TextArea();
        Font font = new Font(15);
        textArea.setFont(font);
        Scene scene = new Scene(root, 700, 400);

        Button closeProgramButton = new Button("Close");
        closeProgramButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                stage.close();
            }
        });

        String[] strings = parseNewsNsn.getTextsNews().get(numberNews).split(" ");
        StringBuilder textNews = new StringBuilder();
        int i = 0;
        for (String s : strings) {
            if (!s.equals("Подписывайтесь")) {
                textNews.append(s + " ");
                i++;
            } else break;

            if (i % 10 == 0) {
                textNews.append("\n");
            }
        }

        root.getChildren().addAll(closeProgramButton, textArea);
        textArea.setText(textNews.toString());
        stage.setTitle(parseNewsNsn.getLastNews().get(numberNews));
        stage.setScene(scene);
        stage.show();

    }
}
