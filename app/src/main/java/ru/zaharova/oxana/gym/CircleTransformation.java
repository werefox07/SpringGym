package ru.zaharova.oxana.gym;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;

import com.squareup.picasso.Transformation;

public class CircleTransformation implements Transformation {

    @NonNull
    @Override
    public Bitmap transform(final @NonNull Bitmap source) {
        Path path = new Path();
        path.addCircle(
                source.getWidth() / 2,
                source.getHeight() / 2,
                source.getWidth() / 2,
                Path.Direction.CCW
        );
        Bitmap answerBitMap = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(answerBitMap);
        canvas.clipPath(path);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawBitmap(source, 0, 0, paint);
        source.recycle();
        return answerBitMap;
    }

    @NonNull
    @Override
    public String key() {
        return "circle";
    }
}
