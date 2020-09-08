package Main;

import Logic.SDM.SDMFileVerifier;
import Logic.SDM.SDMManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

public class MainAppController {

    @FXML
    private HBox orderMenuRef;
    private AnchorPane viewMenuRef;

    @FXML
    private Label testLabel;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private AnchorPane leftAnchorPane;

    @FXML
    private AnchorPane rightAnchorPane;


    @FXML
    private Button loadButton;

    @FXML
    private Button viewInfoButton;

    @FXML
    private Button newOrderButton;

    @FXML
    private Button updateButton;

    private SDMManager sdmManager;
    private Boolean hasLoadedSDMFile = false;


    @FXML
    void loadButtonAction(ActionEvent event) {

        Window stage = mainAnchorPane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose SDM file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));

        try {
            System.out.println("Inside try for loadButtonAction");
            File file = fileChooser.showOpenDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());

            SDMFileVerifier sdmForChosenFile = new SDMFileVerifier(file);

            System.out.printf("Checking if %s file is valid...\n", file.getName());

            if (sdmForChosenFile.getIsValidFile()){
                System.out.println("Valid, yay!!!");
                enableAllButtons();

                hasLoadedSDMFile = true;
                sdmManager.loadNewSDMFile(sdmForChosenFile);
                //file loaded successfully
                testLabel.setVisible(true);
                testLabel.setText(file.getName());
                testLabel.setTextFill(Color.GREEN);
                errorMessageLabel.setText("");
                loadXMLForOtherButtons();
            } else{
                System.out.printf("File %s appears to be invalid (GASP!)\n", file.getName());
                errorMessageLabel.setText(sdmForChosenFile.getLoadingErrorMessage());
                errorMessageLabel.setTextFill(Color.RED);
                errorMessageLabel.setVisible(true);
            }

        } catch (Exception ex) {
            System.out.println("error");
        }
    }

    private void enableAllButtons() {
        viewInfoButton.setDisable(false);
        newOrderButton.setDisable(false);
        updateButton.setDisable(false);
    }

    private void loadXMLForOtherButtons() {
        System.out.println("Going to try and store ref to viewMenu.fxml");
        try {
            viewMenuRef = FXMLLoader.load(getClass().getResource("/components/viewInfoMenu/ViewMenu.fxml"));
            System.out.println("Going to try and store ref to orderMenu.fxml");
            orderMenuRef = FXMLLoader.load(getClass().getResource("/components/newOrderMenu/OrderMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void newOrderAction(ActionEvent event) {

        //loadPage("/components/ordermenu/OrderMenu");
        System.out.println("Calling setCenterPane(orderMenuRef):");
        rightAnchorPane.getChildren().clear();
        rightAnchorPane.getChildren().add(orderMenuRef);
        //rightPane.getChildren().setAll(orderMenuRef);
    }

//    private void setCenterPane(Parent newCenter) {
//        mainAnchorPane.setCenter(newCenter);
//    }

    @FXML
    void updateButtonAction(ActionEvent event) {

    }

    @FXML
    void viewInfoButtonAction(ActionEvent event) {

        //loadPage("/components/viewMenu/ViewMenu");
        System.out.println("Calling setCenterPane(viewMenuRef):");
        rightAnchorPane.getChildren().clear();
        rightAnchorPane.getChildren().add(viewMenuRef);
        AnchorPane.setBottomAnchor(viewMenuRef, 0.0);
        AnchorPane.setLeftAnchor(viewMenuRef, 0.0);
        AnchorPane.setRightAnchor(viewMenuRef, 0.0);
        AnchorPane.setTopAnchor(viewMenuRef, 0.0);
        //setCenterPane(viewMenuRef);
        //rightPane.getChildren().setAll(viewMenuRef);

    }



    @FXML
    private void initialize(){
        System.out.println("Inside MainAppController initialize().");
        sdmManager = SDMManager.getInstance();
        viewInfoButton.setDisable(true);
        newOrderButton.setDisable(true);
        updateButton.setDisable(true);
    }

}
