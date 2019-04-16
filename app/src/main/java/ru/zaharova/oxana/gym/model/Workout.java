package ru.zaharova.oxana.gym.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Workout {

    private String title;
    private String description;
    private int recordRepsCount;
    private Date recordDate;
    private int recordWeight;

    Workout(String title) {
        this.title = title;
    }

    public String getFormattedRecordDate(){
        return new SimpleDateFormat("dd MMMM yyyy", Locale.ROOT).format(recordDate);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public int getRecordRepsCount() {
        return recordRepsCount;
    }

    public void setRecordRepsCount(int recordRepsCount) {
        this.recordRepsCount = recordRepsCount;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public int getRecordWeight() {
        return recordWeight;
    }

    public void setRecordWeight(int recordWeight) {
        this.recordWeight = recordWeight;
    }
}
