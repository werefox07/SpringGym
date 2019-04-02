package ru.zaharova.oxana.gym.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Workout {

    private String title;
    private String description;
    private int recordRepsCount;
    private Date recordDate;
    private int recordWeight;

    public Workout(String title) {
        this.title = title;
    }

    public Workout(String title, String description, int repsCount, Date date, int weight) {
        this.title = title;
        this.description = description;
        this.recordRepsCount = repsCount;
        this.recordDate = date;
        this.recordWeight = weight;
    }

    public String getFormattedRecordDate(){
        return new SimpleDateFormat("dd MMMM yyyy", Locale.ROOT).format(recordDate);
    }

    public void setRecordDateFromFormattedString(String stringDate) throws ParseException {
        DateFormat dateFormat= new SimpleDateFormat("dd MMMM yyyy", Locale.ROOT);
        this.setRecordDate(dateFormat.parse(stringDate));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRecordRepsCount() {
        return recordRepsCount;
    }

    public void setRecordRepsCount(int recordRepsCount) {
        this.recordRepsCount = recordRepsCount;
    }

    public Date getRecordDate() {
        return recordDate;
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
