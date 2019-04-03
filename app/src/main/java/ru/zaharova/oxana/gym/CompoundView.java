package ru.zaharova.oxana.gym;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class CompoundView extends RelativeLayout {
    ImageView imageView;
    CalendarView calendarView;

    public CompoundView(Context context) {
        super(context);
        initViews(context);
    }

    public CompoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public CompoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_compound_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
    }

    private void initViews() {
        imageView = this.findViewById(R.id.image_calendar);
        calendarView = this.findViewById(R.id.calendar_view);
    }
}
