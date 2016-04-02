import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;


/**
 * Created by Camiel on 02-Apr-16.
 */
public class GUIcontroller {

    @FXML private Text actiontarget;

    @FXML protected void handleSubmitButtonAction(ActionEvent event) {
        actiontarget.setText("Sign in button pressed");
    }

}
