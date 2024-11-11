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
import lk.ijse.gdse.dto.OrdersDto;
import lk.ijse.gdse.dto.Tm.CustomerTm;
import lk.ijse.gdse.model.CustomerModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {
    CustomerModel customerModel = new CustomerModel();

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button butGenearateReport;

    @FXML
    private TableColumn<CustomerTm, String> colAddress;

    @FXML
    private TableColumn<CustomerTm, String> colContact;

    @FXML
    private TableColumn<CustomerTm, String> colCustomerId;

    @FXML
    private TableColumn<CustomerTm, String> colEmail;

    @FXML
    private TableColumn<CustomerTm, String> colName;

    @FXML
    private Label lblCustomerId;

    @FXML
    private TableView<CustomerTm> tbCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    void onMouseClicked(MouseEvent event) {
        CustomerTm customerTm  = tbCustomer.getSelectionModel().getSelectedItem();
        if (customerTm != null) {
            lblCustomerId.setText(customerTm.getCusId());
            txtName.setText(customerTm.getCusName());
            txtEmail.setText(customerTm.getEmail());
            txtContact.setText(customerTm.getContact());
            txtAddress.setText(customerTm.getAddress());
        }
    }

    @FXML
    void OnActionbutGenearateReport(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        boolean result = customerModel.deleteCustomer(lblCustomerId.getText());

        if (result) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Delete Successful").show();
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Customer Delete UnSuccessful").show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String customerId = lblCustomerId.getText();
        String customerName = txtName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();

        boolean isResult = customerModel.saveCustomer(new CustomerDto(customerId, customerName, email, contact, address));

        if (isResult) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Save Successful").show();
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Customer Save UnSuccessful").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        boolean result = customerModel.updateCustomer(new CustomerDto(lblCustomerId.getText(), txtName.getText(), txtEmail.getText(), txtContact.getText(), txtAddress.getText()));

        if (result) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Update Successful").show();
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Customer Update UnSuccessful").show();
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException {
        reset();
    }

    void getNextCustomerId() throws SQLException {
        String customerId = customerModel.getNextCustomerId();
        lblCustomerId.setText(customerId);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        try {
            reset();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTableData() throws SQLException {
        ArrayList<CustomerDto> customerDTOS = customerModel.getAllCustomers();

        ObservableList<CustomerTm> customerTms = FXCollections.observableArrayList();

        for (CustomerDto customerDto : customerDTOS) {
            CustomerTm customerTm = new CustomerTm(
                    customerDto.getCusId(),
                    customerDto.getCusName(),
                    customerDto.getEmail(),
                    customerDto.getContact(),
                    customerDto.getAddress()
            );
            customerTms.add(customerTm);
        }

        tbCustomer.setItems(customerTms);
    }

    private void reset() throws SQLException {
        getNextCustomerId();
        loadTableData();

        txtName.setText("");
        txtEmail.setText("");
        txtContact.setText("");
        txtAddress.setText("");
    }
}
