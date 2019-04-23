package ru.zaharova.oxana.gym.databases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class WeatherTable {
    private final static String TABLE_WEATHER = "Weather";
    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_CITY = "city";
    private final static String COLUMN_TEMP = "temperature";
    private final static String COLUMN_HUM = "humidity";
    private final static String COLUMN_PRESS = "pressure";

    public static void createTable(@NonNull SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + TABLE_WEATHER + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CITY + " TEXT," +
                COLUMN_TEMP + " REAL," +
                COLUMN_HUM + " INTEGER," +
                COLUMN_PRESS + " INTEGER);"
        );
    }

    public static void addNote(@NonNull String city, float temp, int hum, int press,
                               @NonNull SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY, city);
        values.put(COLUMN_TEMP, temp);
        values.put(COLUMN_HUM, hum);
        values.put(COLUMN_PRESS, press);
        database.insert(TABLE_WEATHER, null, values);
    }

    public static void editNote(String city, float temp, int hum, int press, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEMP, temp);
        values.put(COLUMN_HUM, hum);
        values.put(COLUMN_PRESS, press);
        String selection = COLUMN_CITY + " = ?";
        String[] selectionArgs = { city };
        database.update(TABLE_WEATHER, values, selection, selectionArgs);
    }

    public static void deleteNote(@NonNull String city, @NonNull SQLiteDatabase database) {
        database.delete(TABLE_WEATHER, COLUMN_CITY + " = '" + city + "'", null);
    }

    public static void deleteAll(@NonNull SQLiteDatabase database) {
        database.delete(TABLE_WEATHER, null, null);
    }

    @NonNull
    public static List<WeatherNote> getNote(@NonNull SQLiteDatabase database, @NonNull String city) {
        String selection = COLUMN_CITY + " = ?";
        String[] selectionArgs = { city };
        Cursor cursor = database.query(TABLE_WEATHER, null, selection,
                selectionArgs, null, null, null);
        return getResultFromCursor(cursor);
    }

    @NonNull
    public static List<WeatherNote> getAllNotes(@NonNull SQLiteDatabase database) {

        Cursor cursor = database.query(TABLE_WEATHER, null, null,
                null, null, null, null);
        return getResultFromCursor(cursor);
    }

    private static List<WeatherNote> getResultFromCursor(Cursor cursor) {
        List<WeatherNote> result = null;

        if(cursor != null && cursor.moveToFirst()) {
            result = new ArrayList<>(cursor.getCount());

            int cityIdx = cursor.getColumnIndex(COLUMN_CITY);
            int tempIdx = cursor.getColumnIndex(COLUMN_TEMP);
            int humIdx = cursor.getColumnIndex(COLUMN_HUM);
            int pressIdx = cursor.getColumnIndex(COLUMN_PRESS);
            do {
                String city = cursor.getString(cityIdx);
                float temp = cursor.getFloat(tempIdx);
                int hum = cursor.getInt(humIdx);
                int press = cursor.getInt(pressIdx);
                WeatherNote weatherNote = new WeatherNote(city, temp, hum, press);
                result.add(weatherNote);
            } while (cursor.moveToNext());
        }

        try {
            cursor.close();
        } catch (Exception ignored) {}
        return result == null ? new ArrayList<WeatherNote>(0) : result;
    }
}
