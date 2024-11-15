package lk.ijse.gdse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.dto.CustomerDto;
import lk.ijse.gdse.dto.StockDto;
import lk.ijse.gdse.dto.Tm.CustomerTm;
import lk.ijse.gdse.dto.Tm.StockTm;
import lk.ijse.gdse.model.StockModel;
import lk.ijse.gdse.util.CrudUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StockFormController implements Initializable {

    StockModel stockModel = new StockModel();

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button butGenearateReport;

    @FXML
    private TableColumn<StockTm, String> colDesc;

    @FXML
    private TableColumn<StockTm, String> colLastpdate;

    @FXML
    private TableColumn<StockTm, String> colLocation;

    @FXML
    private TableColumn<StockTm, String> colQty;

    @FXML
    private TableColumn<StockTm, String> colRecodeLevel;

    @FXML
    private TableColumn<StockTm, String> colStockId;

    @FXML
    private Label lbStockId;

    @FXML
    private TableView<StockTm> tbStock;

    @FXML
    private TextField txtLocation;;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtLastUpdate;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtRecodeLevel;

    @FXML
    void OnActionbutGenearateReport(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        boolean result = stockModel.deleteStock(lbStockId.getText());

        if (result) {
            new Alert(Alert.AlertType.INFORMATION, "Stock Delete Successful").show();
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Stock Delete UnSuccessful").show();
            reset();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String stockId = lbStockId.getText();
        String description = txtDesc.getText();
        String location = txtLocation.getText();
        String last_update = txtLastUpdate.getText();
        int recode_level = Integer.parseInt(txtRecodeLevel.getText());
        int qty = Integer.parseInt(txtQty.getText());
        
        boolean isSaved = stockModel.isSaved(new StockDto(
                stockId,
                description,
                location,
                last_update,
                recode_level,
                qty)
        );
        
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Stock Save Successful").showAndWait();
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Stock Save UnSuccessful").showAndWait();
            reset();
        }
    }
    
    void reset() throws SQLException {

        getAllStock();
        getNextStockId();

        txtDesc.setText("");
        txtLocation.setText("");
        txtLastUpdate.setText("");
        txtRecodeLevel.setText("");
        txtQty.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String stockId = lbStockId.getText();
        String description = txtDesc.getText();
        String location = txtLocation.getText();
        String last_update = txtLastUpdate.getText();
        int recode_level = Integer.parseInt(txtRecodeLevel.getText());
        int qty = Integer.parseInt(txtQty.getText());

        boolean isUpdate = stockModel.isUpdate(new StockDto(
                stockId,
                description,
                location,
                last_update,
                recode_level,
                qty)
        );

        if (isUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "Stock update Successful").showAndWait();
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Stock update UnSuccessful").showAndWait();
            reset();
        }
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        StockTm stockTm  = tbStock.getSelectionModel().getSelectedItem();
        if (stockTm != null) {
            lbStockId.setText(stockTm.getStockId());
            txtDesc.setText(stockTm.getDescription());
            txtLocation.setText(stockTm.getLocation());
            txtLastUpdate.setText(stockTm.getLast_update());
            txtRecodeLevel.setText(String.valueOf(stockTm.getRecode_level()));
            txtQty.setText(String.valueOf(stockTm.getQty()));
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException {
        reset();
    }

    void getAllStock() throws SQLException {
        ArrayList<StockDto> stockDTOS = stockModel.getAllStock();

        ObservableList<StockTm> stockTms = FXCollections.observableArrayList();

        for (StockDto stockDto : stockDTOS) {
            StockTm stockTm = new StockTm(
                    stockDto.getStockId(),
                    stockDto.getDescription(),
                    stockDto.getLocation(),
                    stockDto.getLast_update(),
                    stockDto.getRecode_level(),
                    stockDto.getQty()
            );
            stockTms.add(stockTm);
        }

        tbStock.setItems(stockTms);
    }

    void getNextStockId () throws SQLException {
        String stockId = stockModel.getNextStockId();
        lbStockId.setText(stockId);
    }
    

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colStockId.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colLastpdate.setCellValueFactory(new PropertyValueFactory<>("last_update"));
        colRecodeLevel.setCellValueFactory(new PropertyValueFactory<>("recode_level"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        try {
            reset();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
