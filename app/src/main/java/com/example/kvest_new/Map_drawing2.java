package com.example.kvest_new;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Map_drawing2 extends View {
    public List<Float> coordinates = new ArrayList<>();
    public Integer x = 0;
    public Map_drawing2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        draw_map2(canvas);}
    public void draw_map2(Canvas canvas){

        Paint paint1 = new Paint();
        Paint paint2 = new Paint();
        paint1.setColor(ContextCompat.getColor(getContext(),R.color.red));
        paint2.setColor(ContextCompat.getColor(getContext(),R.color.blue));
        paint1.setStrokeWidth(20);
        paint2.setStrokeWidth(20);
        if (x != 0){
            this.x = this.x +1;
        }
        if(this.coordinates.size() != 0) {
            for (int i = 0; i < 20; i = i + 2) {
                canvas.drawCircle(this.coordinates.get(i), this.coordinates.get(i + 1), 20, paint1);
            }
            canvas.drawCircle(this.coordinates.get(this.x), this.coordinates.get(this.x+1), 20, paint2);
        }


    }
    public void settings(ArrayList<Float> coordinates, Integer x){
        this.coordinates = coordinates;
        this.x = x;
        this.invalidate();

}}
