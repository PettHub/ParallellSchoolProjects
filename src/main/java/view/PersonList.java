package main.java.view;

import main.java.model.Admin;
import main.java.model.Employee;
import main.java.model.Observer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.*;

/**
 * @author Oliver Andersson
 * EmployeeTab of the program. Root node for "employeetab"
 * @since 2020-10-07
 */
public class PersonList extends AnchorPane implements Observer {
    private final Map<Employee, EmployeeView> employeeEmployeeViewMap;
    private final List<EmployeeView> employeeViews;
    @FXML
    ListView<EmployeeView> employeeViewPane;
    @FXML
    Button buttonCreateEmployee;
    @FXML
    AnchorPane paneDetailView;

    public PersonList() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PersonList.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.employeeEmployeeViewMap = new HashMap<>();
        this.employeeViews = new ArrayList<>();
        generatePersonViews();
        generateButtons();
        Admin.getInstance().addObserver(this);
    }

    private void generateButtons() {
        buttonCreateEmployee.setOnAction(actionEvent -> createEmployee());
    }

    private void createEmployee() {
        paneDetailView.getChildren().clear();
        paneDetailView.getChildren().add(new DetailEmployeeView());
        paneDetailView.setVisible(true);
    }

    private void sortEmployeesAlphabetically(List<Employee> employees) {
        employees.sort(Comparator.comparing(Employee::getName));
    }

    private void generatePersonViews() {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < Admin.getInstance().getEmployeeListSize(); i++)
            employees.add(Admin.getInstance().getEmployee(i));
        sortEmployeesAlphabetically(employees);
        employeeViewPane.getItems().clear();
        for (Employee e : employees) {
            if (employeeEmployeeViewMap.get(e) == null) {
                EmployeeView employeeView = new EmployeeView(e);
                employeeEmployeeViewMap.put(e, employeeView);
                employeeViews.add(employeeView);
                employeeView.setOnMouseClicked(mouseEvent -> {
                    paneDetailView.getChildren().clear();
                    paneDetailView.getChildren().add(new DetailEmployeeView(e));
                });
            }
            employeeViewPane.getItems().add(employeeEmployeeViewMap.get(e));
        }
    }

    @Override
    public void update() {
        generatePersonViews();
    }
}
