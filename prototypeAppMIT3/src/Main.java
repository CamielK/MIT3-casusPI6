import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GUI/GUI.fxml"));

        Scene scene = new Scene(root, 1000, 750);

        stage.setTitle("Movie predictor");
        stage.setScene(scene);
        stage.show();
    }




//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("MIT3 prototype");
//
//        //add grid pane
//        GridPane grid = new GridPane();
//        grid.setAlignment(Pos.CENTER);
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(25,25,25,25));
//
//
//        //>>> add components <<<
//        Text scenetitle = new Text("Movie rating and revenue predictor");
//        scenetitle.setId("welcome-text");
//        grid.add(scenetitle, 0, 0, 3, 1);
//
//        Label userName = new Label("User Name:");
//        grid.add(userName, 0, 1);
//
//        TextField userTextField = new TextField();
//        grid.add(userTextField, 1, 1);
//
//        Label pw = new Label("Password:");
//        grid.add(pw, 0, 2);
//
//        PasswordField pwBox = new PasswordField();
//        grid.add(pwBox, 1, 2);
//
//        Button btn = new Button("Sign in");
//        HBox hbBtn = new HBox(10);
//        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//        hbBtn.getChildren().add(btn);
//        grid.add(hbBtn, 1, 4);
//
//        final Text actiontarget = new Text();
//        grid.add(actiontarget, 1, 6);
//
//
//
//
//
//        //action listeners
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                actiontarget.setId("actiontarget");
//                actiontarget.setText("Sign in button pressed");
//            }
//        });
//
//
//
//        //display grid lines (debugging)
//        grid.setGridLinesVisible(true);
//
//
//        //create scene
//        Scene scene = new Scene(grid, 1000, 600);
//        primaryStage.setScene(scene);
//
//        //add style sheet file to scene
//        scene.getStylesheets().add(Main.class.getResource("guiStyles.css").toExternalForm());
//
//        //finalize form
//        primaryStage.show();
//    }
}