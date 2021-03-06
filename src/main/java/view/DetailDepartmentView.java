package main.java.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import main.java.model.Admin;
import main.java.model.Department;
import main.java.model.Observer;

/**
 * @author Moa och Victor
 * View for creating and editing a department
 * @since 2020-10-09
 */

public class DetailDepartmentView extends AnchorPane implements Observer {
    private Department department;

    @FXML
    private TextField name;
    @FXML
    private Spinner<Integer> minPersonsOnShift;
    @FXML
    private Button saveChanges, deleteDepartment;
    @FXML
    private ColorPicker colorPicker;
    private SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Admin.getInstance().getEmployeeListSize() + 100, 1, 1);


    public DetailDepartmentView(Department department) {
        this.department = department;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetailDepartmentView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        minPersonsOnShift.setValueFactory(valueFactory);
        generateButtons();
        generateFXMLObjects();
        Admin.getInstance().addObserver(this);
    }

    public DetailDepartmentView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetailDepartmentView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        minPersonsOnShift.setValueFactory(valueFactory);

        generateButtons();
        generateFXMLObjects();
        Admin.getInstance().addObserver(this);
    }




    private void generateButtons(){
        saveChanges.setOnAction(actionEvent -> {
            if (department == null) {
                Admin.getInstance().createNewDepartment(name.getText(), Integer.parseInt(minPersonsOnShift.getEditor().getText()),colorPicker.getValue());
                department= Admin.getInstance().getDepartmentByName(name.getText());
            }
            else{
                Admin.getInstance().changeDepartmentName(department, name.getText());
                Admin.getInstance().getDepartmentByName(name.getText()).setColor(colorPicker.getValue());
                Admin.getInstance().getDepartmentByName(name.getText()).setMinPersonsOnShift(Integer.parseInt(minPersonsOnShift.getEditor().getText()));//TODO change maxPersonsOnBreak
                Admin.getInstance().notifyObservers();
            }
        });
        deleteDepartment.setOnAction(actionEvent -> deleteAction());
        deleteDepartment.setOnAction(actionEvent -> deleteAction());

    }

    private void deleteAction() {
        if (department == null) {
            name.setText("");
            minPersonsOnShift.getEditor().setText("");
        } else
            Admin.getInstance().removeDepartment(department);
        Admin.getInstance().removeObserver(this);
    }

    private void generateFXMLObjects() {
        if (department != null) {
            this.name.setText(department.getName().split(" ")[0]);
            this.minPersonsOnShift.getEditor().setText(String.valueOf(department.getMinPersonsOnShift()));
            this.colorPicker.setValue(department.getColor());
        }
    }

    @Override
    public void update() {
        generateFXMLObjects();
    }
}