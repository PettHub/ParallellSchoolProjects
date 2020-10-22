package main.java.view;

import main.java.model.WorkDay;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.util.Calendar;
import java.util.Date;

public class DayScheduleViewMonth extends AnchorPane {
    private WorkDay thisDay;
    @FXML
    private Label dayLabel;
    private int dayOfMonth;

    public DayScheduleViewMonth(WorkDay day) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DayScheduleViewMonth.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        thisDay = day;
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date(day.DATE));
        dayOfMonth = instance.get(Calendar.DAY_OF_MONTH);
        dayLabel.setText(Integer.toString(dayOfMonth));
        setColor();
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    void setColor() {
        Color color;
        if (thisDay.isEmpty())
            color = new Color(0.5, 0.5, 0.5, 1);
        else if (thisDay.isFilled())
            color = new Color(0, 0.5, 0, 1);
        else
            color = new Color(0.5, 0, 0, 1);
        BackgroundFill tmp = new BackgroundFill(color, new CornerRadii(5), new Insets(-5));
        this.setBackground(new Background(tmp));
    }
}
