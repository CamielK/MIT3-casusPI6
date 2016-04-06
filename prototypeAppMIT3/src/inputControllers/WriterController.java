package inputControllers;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * Created by Camiel on 06-Apr-16.
 */
public class WriterController {

    private static String addedWriter;
    private static Stage parentStage;

    public String addWriter(ComboBox<String> comboBox) {
        final ObservableList<String> selectionModel = comboBox.getItems();
        this.parentStage = (Stage) comboBox.getScene().getWindow();


        return addedWriter;
    }

}
