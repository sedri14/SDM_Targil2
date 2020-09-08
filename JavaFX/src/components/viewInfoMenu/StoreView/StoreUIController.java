package components.viewInfoMenu.StoreView;

import Logic.SDM.SDMManager;
import Logic.Store.Store;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.ResourceBundle;

public class StoreUIController implements Initializable {



    @FXML
    private TableView<Store> basicInfoTableView;

    @FXML
    private TableColumn<Store, Integer> storeIDColumn;

    @FXML
    private TableColumn<Store, String> storeNameColumn;

    @FXML
    private TableColumn<Store, List<Integer>> storeLocationColumn;

    @FXML
    private TableColumn<Store, Integer> storePPKColumn;

    @FXML
    private TableColumn<Store, Float> storeDeliveryIncomeColumn;

    @FXML
    private TableView<Store> storeInventoryTableView;

    @FXML
    private TableColumn<Store, Integer> itemIDColumn;

    @FXML
    private TableColumn<Store, String> itemNameColumn;

    @FXML
    private TableColumn<Store, String> itemCategoryColumn;

    @FXML
    private TableColumn<Store, Integer> itemPriceColumn;

    @FXML
    private TableColumn<Store, Float> itemAmountSoldColumn;

    @FXML
    private Label storeOrdersTableView;

    @FXML
    private TableColumn<Store, LocalDate> orderDateColumn;

    @FXML
    private TableColumn<Store, String> orderIDColumn;

    @FXML
    private TableColumn<Store, Integer> numItemsInCartColumn;

    @FXML
    private TableColumn<Store, Float> cartSubtotalColumn;

    @FXML
    private TableColumn<Store, Float> deliveryFeeColumn;

    @FXML
    private TableColumn<Store, Float> orderTotalColumn;

    @FXML
    private ListView<Store> listview;

    private SDMManager sdmManager;
    private Store selectedStore;
    private final ObservableList<Store> storeList = FXCollections.observableArrayList(Store.extractor);
    // Observable objects returned by extractor (applied to each list element) are listened for changes and
    // transformed into "update" change of ListChangeListener.
    private ChangeListener<Store> storeChangeListener;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Inside StoreUIController initialize()");
        sdmManager = SDMManager.getInstance();
        sdmManager.fillSampleData(storeList);

        // Use a sorted list; sort by lastname; then by firstname
        SortedList<Store> sortedList = new SortedList<>(storeList);

        // sort by lastname first, then by firstname; ignore notes
        sortedList.setComparator((p1, p2) -> {
            if (p1.getStoreId() < p2.getStoreId())
                return -1;
            else
                return 1;
        });
        listview.setItems(sortedList);

        storeIDColumn.setCellValueFactory(new PropertyValueFactory<Store,Integer>("storeId"));
        storeNameColumn.setCellValueFactory(new PropertyValueFactory<Store,String>("storeName"));
        storeDeliveryIncomeColumn.setCellValueFactory(new PropertyValueFactory<Store,Float>("totalDeliveryIncome"));
        storePPKColumn.setCellValueFactory(new PropertyValueFactory<Store,Integer>("deliveryPpk"));


        listview.getSelectionModel().selectedItemProperty().addListener(
                storeChangeListener = (((observable, oldValue, newValue) -> {

                    System.out.println("Selected item: " + newValue);
                    //newValue can be null if nothing is selected
                    selectedStore = newValue;

                    if (newValue != null){
                        System.out.println("newVluae is " + newValue);
                        basicInfoTableView.setItems(getStore(newValue));

                        //populate controls with selected store information

                        //storeIDColumn.set
                    }
                }))
        );

        listview.getSelectionModel().selectFirst();
    }

    private ObservableList<Store> getStore(Store selectedStore) {
        ObservableList<Store> currentStore = FXCollections.observableArrayList();
        currentStore.removeAll();
        currentStore.add(selectedStore);

        return currentStore;
    }
}

//https://openjfx.io/javadoc/11/javafx.controls/javafx/scene/control/TableView.html

//http://tutorials.jenkov.com/javafx/scrollpane.html