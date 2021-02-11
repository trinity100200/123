package ru.sapteh.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.Dao.Dao;
import ru.sapteh.Service.RoleService;
import ru.sapteh.Service.UserService;
import ru.sapteh.model.Role;
import ru.sapteh.model.User;

import java.util.Date;


public class Controller {

    private final ObservableList<User> userList = FXCollections.observableArrayList();
    private final ObservableList<Role> roleList = FXCollections.observableArrayList();

    // Table Users
    @FXML
    private TableView<User> tableUser;
    @FXML
    private TableColumn<User, String> columnFirstName;
    @FXML
    private TableColumn<User, String> columnLastName;

    @FXML
    private Label labelId;

    @FXML
    private Label labelFirstName;

    @FXML
    private Label labelLastName;

    @FXML
    private ComboBox<Role> comboRole;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelCount;

    @FXML
    private Label labelRegDate;


    public void initialize(){

        readAllUserBase();

//        columnId.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getId()));
//
        columnLastName.setCellValueFactory(
                u -> new SimpleObjectProperty<>(u.getValue().getName()));

        columnFirstName.setCellValueFactory(
                u -> new SimpleObjectProperty<>(u.getValue().getLastName()));

        tableUser.setItems(userList);

        tableUser.getSelectionModel().selectedItemProperty().addListener((observableValue, user, t1) -> showPersonDetails(t1));
//
//        columnEmail.setCellValueFactory(
//                u-> new SimpleObjectProperty<>(u.getValue().getEmail()));
//
//        columnRole.setCellValueFactory(
//                u-> new SimpleObjectProperty<>(u.getValue().getRole().iterator().next().getRole().getName()));
//
//        columnCount.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getRole().size()));
    }


    private void showPersonDetails(User newValue) {
        labelId.setText(String.valueOf(newValue.getId()));
        labelLastName.setText(newValue.getLastName());
        labelFirstName.setText(newValue.getName());
        comboRole.setItems(roleList);
        labelEmail.setText(newValue.getEmail());
        labelRegDate.setText(String.valueOf(newValue.getRole().iterator().next().getRegistrationDay()));
    }

    private void readAllUserBase(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Dao<User, Integer> userDao = new UserService(factory);
        Dao<Role, Integer> roleDao = new RoleService(factory);

        userList.addAll(userDao.readAll());
        roleList.addAll(roleDao.readAll());

        factory.close();
    }
}
