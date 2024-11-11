package lk.ijse.gdse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.dto.UserDto;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController {


    @FXML
    private Button butCustomerManage;

    @FXML
    private Button butEmployeeManage;

    @FXML
    private Button butOrdersView;

    @FXML
    private Button butPlaceOrder;

    @FXML
    private Button butStockManage;

    @FXML
    private Button butSupplierManage;

    @FXML
    private Button butUserManage;

    @FXML
    private AnchorPane changeAnchorPane;


    public void initializeDashBoard(UserDto userDto){
         if (userDto.getRole() != null && userDto.getRole().toLowerCase().equals("cashier")) {
             System.out.println(userDto.getRole());

             butCustomerManage.setDisable(false);
             butPlaceOrder.setDisable(false);

             butEmployeeManage.setDisable(true);
             butOrdersView.setDisable(true);
             butStockManage.setDisable(true);
             butSupplierManage.setDisable(true);
             butUserManage.setDisable(true);
         }

    }

    @FXML
    void butCustomerManageOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/CustomerForm.fxml");
    }

    @FXML
    void butEmployeeManageOnAction(ActionEvent event){

    }

    @FXML
    void butOrdersViewOnAction(ActionEvent event) {

    }

    @FXML
    void butPlaceOrderOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/PlaceOrder.fxml");
    }

    @FXML
    void butStockManageOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/StockForm.fxml");
    }

    @FXML
    void butSupplierManageOnAction(ActionEvent event) {

    }

    @FXML
    void butUserManageOnAction(ActionEvent event) {

    }


    void initialize(UserDto userDto) {
        initializeDashBoard(userDto);
    }

    private void navigateToAnchorPane(String path) throws IOException {
        changeAnchorPane.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));
        changeAnchorPane.getChildren().add(anchorPane);
    }

}
