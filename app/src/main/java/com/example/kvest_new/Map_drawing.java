package com.example.kvest_new;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class Map_drawing extends View {
    public ArrayList<Float> coordinates = new ArrayList();
    public Integer color_1 = R.color.red;
    public Integer color_2 = R.color.red;
    public Integer color_3 = R.color.red;
    public Canvas canvas;
    public Map_drawing(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        this.canvas = canvas;
        draw_map(canvas);
    }
    public void draw_map(Canvas canvas){
        Paint paint1 = new Paint();
        Paint paint2 = new Paint();
        Paint paint3 = new Paint();
        paint1.setColor(ContextCompat.getColor(getContext(),this.color_1));
        paint2.setColor(ContextCompat.getColor(getContext(),this.color_2));
        paint3.setColor(ContextCompat.getColor(getContext(),this.color_3));
        paint1.setStrokeWidth(20);
        paint2.setStrokeWidth(20);
        paint3.setStrokeWidth(20);
        if(this.coordinates.size() != 0) {
            for (int i = 0; i < 10; i = i + 2) {
                canvas.drawCircle(this.coordinates.get(i),  this.coordinates.get(i + 1), 20, paint1);
            }
            for (int i = 20; i < 20; i = i + 2) {
                canvas.drawCircle(this.coordinates.get(i), this.coordinates.get(i + 1), 20, paint2);
            }
            for (int i = 40; i < 30; i = i + 2) {
                canvas.drawCircle(this.coordinates.get(i), this.coordinates.get(i + 1), 20, paint3);
            }
        }
    }
    public void settings(ArrayList<Float>coordinates, Integer color_1, Integer color_2, Integer color_3){
        this.coordinates = new ArrayList<>(coordinates);
        this.color_1 = color_1;
        this.color_2 = color_2;
        this.color_3 = color_3;
        this.invalidate();
    }
}
