package lk.ijse.gdse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.dto.UserDto;
import lk.ijse.gdse.model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpFormController implements Initializable {
    UserModel userModel = new UserModel();

    @FXML
    private AnchorPane mainAnchorpane;

    @FXML
    private Button butSignIn;

    @FXML
    private Label labUserId;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtRole;

    @FXML
    private TextField txtUsername;

    @FXML
    void butSignInOnAction(ActionEvent event) throws SQLException, IOException {
        String usId = labUserId.getText();
        String role = txtRole.getText();
        String password = txtPassword.getText();
        String username = txtUsername.getText();

        if (usId.isEmpty() || role.isEmpty() || password.isEmpty() || username.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please enter usId , role , username and password").show();
        }else {
            boolean result = userModel.seveUser(new UserDto(usId,role,password,username));

            if (result){
                Stage currentStage = (Stage) mainAnchorpane.getScene().getWindow();
                currentStage.close();

                Parent load = FXMLLoader.load(getClass().getResource("/view/UserNamaAndPassword.fxml"));
                Scene scene = new Scene(load);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("User Credentials");
                stage.setResizable(false);
                stage.show();

            }else{
                new Alert(Alert.AlertType.ERROR, "Unsuccessful Sign in..!").show();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String id = null;
        try {
            id = userModel.getNextId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while fetching the next user ID. Please try again.").showAndWait();
        }
        labUserId.setText(id);
    }
}
