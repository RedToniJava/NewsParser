import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
    private final static ParseNewsNsn parseNewsNsn = new ParseNewsNsn();
    private final static ParseNewsRbc parseNewsRbc = new ParseNewsRbc();
    private final static ParseNewsNN parseNewsNN = new ParseNewsNN();
    private final static ControlPanel controlPanel = new ControlPanel(parseNewsNsn, parseNewsRbc, parseNewsNN);

    public static void main(String[] args) {

             launch();
        new Thread(new Runnable() {

            @Override
            public void run() {
                while(parseNewsNsn.getStartParse()&& parseNewsNN.getStartParse()&& parseNewsRbc.getStartParse()){
                    //  System.out.println(parseNewsNsn.getStartParse()+" "+parseNewsNN.getStartParse()+" "+ parseNewsRbc.getStartParse());
                    if(!Thread.currentThread().isInterrupted()){
                        System.out.println("Yes!!!!!!!!!!");
                        parseNewsRbc.stopParse();
                        parseNewsNN.stopParse();
                        parseNewsNsn.stopParse();
                    }
                }

            }
        }).start();

    }

    @Override
    public void start(Stage stage) throws Exception {
        controlPanel.start(stage);


    }

}
