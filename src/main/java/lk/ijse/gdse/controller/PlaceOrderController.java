package lk.ijse.gdse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.gdse.dto.*;
import lk.ijse.gdse.dto.Tm.CartTm;
import lk.ijse.gdse.dto.Tm.CustomerTm;
import lk.ijse.gdse.dto.Tm.PlaceOrderTm;
import lk.ijse.gdse.model.CustomerModel;
import lk.ijse.gdse.model.EmployeeModel;
import lk.ijse.gdse.model.OrderModel;
import lk.ijse.gdse.model.PlaceOrderModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlaceOrderController implements Initializable {

    PlaceOrderModel placeOrderModel = new PlaceOrderModel();
    CustomerModel customerModel = new CustomerModel();
    EmployeeModel employeeModel = new EmployeeModel();
    OrderModel orderModel = new OrderModel();

    private final ObservableList<CartTm> cartTms = FXCollections.observableArrayList();

    private OrdersDto ordersDto = new OrdersDto();
    private TireOrderDto tireOrderDto = new TireOrderDto();

    private double total;

    @FXML
    private TableColumn<PlaceOrderTm, String> colBrand;

    @FXML
    private TableColumn<PlaceOrderTm, String> colModel;

    @FXML
    private TableColumn<PlaceOrderTm, String> colPrice;

    @FXML
    private TableColumn<PlaceOrderTm, String> colSize;

    @FXML
    private TableColumn<PlaceOrderTm, String> colTireId;

    @FXML
    private TableColumn<PlaceOrderTm, String> colYear;

    @FXML
    private TableView<PlaceOrderTm> tireTable;

    @FXML
    private TableView<CartTm> tabCart;

    @FXML
    private ComboBox<String> empCombo;

    @FXML
    private TableColumn<CartTm, String> colTireId1;

    @FXML
    private TableColumn<CartTm, Integer> colQty;

    @FXML
    private TableColumn<CartTm, String> colDesc;

    @FXML
    private TableColumn<CartTm, Double> colPrice1;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TextField txtTireId;

    @FXML
    private TextField textContact;

    @FXML
    private TextField txtQty;

    @FXML
    private Label txtTotal;

    @FXML
    private Button butCheck;

    @FXML
    private Label labDate;

    @FXML
    private Label labOrderId;

    @FXML
    private Label labQty;

    public Label getDate(){
        return labDate;
    }

    public Label getLabOrderId(){
        System.out.println();
        return labOrderId;
    }

    public ObservableList<CartTm> getCartTms() {
        System.out.println();
        return cartTms;
    }

    public OrdersDto getOrdersDto() {
        System.out.println();
        return ordersDto;
    }

    public TireOrderDto getTireOrderDto() {
        System.out.println();
        return tireOrderDto;
    }

    public double getTotal() {
        System.out.println();
        return total;
    }

    private void set1CellValues() {
        colTireId1.setCellValueFactory(new PropertyValueFactory<>("tireId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colPrice1.setCellValueFactory(new PropertyValueFactory<>("price"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("butRemove"));

        tabCart.setItems(cartTms);
    }


    void loadEmpIds() throws SQLException {
        ArrayList<String> empId = employeeModel.getAllEmployeesIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (String id : empId) {
            observableList.addAll(id);
        }
        empCombo.setItems(observableList);
    }

    @FXML
    void butCheckOnAction(ActionEvent event) throws SQLException, IOException {
        String contact = textContact.getText();
        boolean check = customerModel.checkCustomer(contact);

        if (check) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Exist").showAndWait();
        } else {
            Parent load = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(load);
            stage.setScene(scene);
            stage.setTitle("Add Customer");
            stage.setResizable(false);
            stage.showAndWait();

            String contact1 = customerModel.getCustomerContactNo();
            textContact.setText(contact1);
        }
    }

    @FXML
    void MouseClicked(MouseEvent event) throws SQLException {
        PlaceOrderTm placeOrderTm = tireTable.getSelectionModel().getSelectedItem();
        if (placeOrderTm != null) {
            txtTireId.setText(placeOrderTm.getTireId());
        }

        String tireId = txtTireId.getText();

        int qty = placeOrderModel.getQty(tireId);
        labQty.setText(String.valueOf(qty));
    }

    private void loadTable() throws SQLException {
        ArrayList<PlaceOrderDto> placeOrderDTOS = placeOrderModel.getAllTires();

        ObservableList<PlaceOrderTm> placeOrderTms = FXCollections.observableArrayList();

        for (PlaceOrderDto placeOrderDto : placeOrderDTOS) {
            PlaceOrderTm placeOrderTm = new PlaceOrderTm(
                    placeOrderDto.getTireId(),
                    placeOrderDto.getTireBrand(),
                    placeOrderDto.getTireModel(),
                    placeOrderDto.getTireSize(),
                    placeOrderDto.getYear(),
                    placeOrderDto.getTirePrice()
            );
            placeOrderTms.add(placeOrderTm);
        }

        tireTable.setItems(placeOrderTms);

    }

    @FXML
    void butAddOnAction(ActionEvent event) throws SQLException {
        String tireId = txtTireId.getText();
        double totalPrice = 0;

        if (tireId == null || tireId.isEmpty() || txtQty.getText() == null || txtQty.getText().isEmpty() || textContact == null || textContact.getText().isEmpty() || empCombo == null || empCombo.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Missing Information").show();
            return;
        }

        PlaceOrderDto placeOrderDto = placeOrderModel.getTire(tireId);

        String description = placeOrderDto.getTireBrand() + " " + placeOrderDto.getTireModel();
        double price = placeOrderDto.getTirePrice();

        int qty = 0;

        try {
            qty = Integer.parseInt(txtQty.getText());
            if (qty == 0) {
                new Alert(Alert.AlertType.ERROR, "Invalid input. Please enter a valid number.").show();
                txtQty.clear();
                return;
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid input. Please enter a valid number.").show();
            txtQty.clear();
            return;
        }

        int stockQty = Integer.parseInt(labQty.getText());

        if (qty > stockQty) {
            new Alert(Alert.AlertType.ERROR, "Not enough stock").show();
            return;
        }

        txtQty.setText("");

        double newPrice = price * qty;

        if (cartTms.isEmpty()) {
            txtTotal.setText(String.valueOf(newPrice));
        } else {
            totalPrice = Double.parseDouble(txtTotal.getText());
        }

        for (CartTm cartTm : cartTms) {
            if (cartTm.getTireId().equals(tireId)) {
                int newQty = cartTm.getQty() + qty;
                cartTm.setQty(newQty);

                double updatedPrice = cartTm.getPrice() + (qty * price);
                cartTm.setPrice(updatedPrice);

                totalPrice += qty * price;
                txtTotal.setText(String.valueOf(totalPrice));
                this.total = Double.parseDouble(txtTotal.getText());

                tabCart.refresh();
                return;
            }
        }

        Button button = new Button("Remove");
        CartTm cartTm = new CartTm(
                tireId,
                qty,
                description,
                newPrice,
                button
        );

        button.setOnAction(actionEvent -> {
            cartTms.remove(cartTm);
            tabCart.refresh();

            double newTotalPrice = 0;
            for (CartTm item : cartTms) {
                newTotalPrice += item.getPrice();
            }

            txtTotal.setText(String.valueOf(newTotalPrice));
            this.total = Double.parseDouble(txtTotal.getText());

            if (cartTms.isEmpty()) {
                txtTotal.setText("0.0");
                this.total = 0.0;
            }
        });

        cartTms.add(cartTm);
        totalPrice += newPrice;
        txtTotal.setText(String.valueOf(totalPrice));
        this.total = Double.parseDouble(txtTotal.getText());
        tabCart.refresh();

        ordersDto.setOrderId(labOrderId.getText());
        ordersDto.setDate(labDate.getText());
        String custId = customerModel.getCustId(textContact.getText());
        ordersDto.setCustId(custId);
        ordersDto.setEmpId(empCombo.getValue());

//        for (CartTm cartTm1 : cartTms) {
//            tireOrderDto.setOrderId(labOrderId.getText());
//        }

    }


    @FXML
    void placeOrderOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ConformOrder.fxml"));
        Parent root = loader.load();

        ConformOrderController conformOrderController1 = loader.getController();

        conformOrderController1.setPlaceOrderController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Conform Order");
        stage.setResizable(false);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTireId.setCellValueFactory(new PropertyValueFactory<>("tireId"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("tireBrand"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("tireModel"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("tireSize"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("tirePrice"));

        labDate.setText(LocalDate.now().toString());

        try {
            labOrderId.setText(orderModel.getNextOrderId());
            loadEmpIds();
            loadTable();
            set1CellValues();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void reset() throws SQLException {
        labQty.setText("");
        labOrderId.setText(orderModel.getNextOrderId());
        txtTireId.setText("");
        txtQty.setText("");
        textContact.setText("");
        empCombo.setValue("");
        tabCart.getItems().clear();
        txtTotal.setText("");

    }
}
