package Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

public class MainAppController {

    @FXML
    private Label testLabel;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Button loadButton;

    @FXML
    private Button viewButton;

    @FXML
    private Button newOrderButton;

    @FXML
    private Button updateButton;

    @FXML
    void loadButtonAction(ActionEvent event) {

        Window stage = mainBorderPane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose SDM file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));

        try {
            File file = fileChooser.showOpenDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());
            //file loaded successfully
            testLabel.setVisible(true);

        } catch (Exception ex) {
            System.out.println("error");
        }
    }

    @FXML
    void newOrderAction(ActionEvent event) {

        loadPage("/components/ordermenu/OrderMenu");
    }

    @FXML
    void updateButtonAction(ActionEvent event) {

    }

    @FXML
    void viewButtonAction(ActionEvent event) {

        loadPage("/components/viewMenu/ViewMenu");
    }

    private void loadPage(String pageName) {

        Parent root  = null;

        try {
            root = FXMLLoader.load(getClass().getResource(pageName + ".fxml"));
        } catch (IOException e) {
            System.out.println("ERROR!!!! Exception thrown in function loadPage");
        }

        mainBorderPane.setCenter(root);
    }

}
