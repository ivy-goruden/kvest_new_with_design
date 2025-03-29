//package com.example.kvest_new
//
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.Paint
//import android.util.AttributeSet
//import android.view.View
//import androidx.core.content.ContextCompat
//
//var coordinates: ArrayList<Float> = ArrayList()
//
//class CanvasView @JvmOverloads constructor(
//    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
//) : View(context, attrs, defStyleAttr) {
//    var canvas:Canvas = Canvas()
//    override fun onDraw(canvas: Canvas) {
//        val paint2 = Paint()
//
//        paint2.color = Color.rgb(255,0,0)
//        paint2.strokeWidth = 20f
//        this.canvas = canvas;
//        draw_map(this.canvas);
//        super.onDraw(canvas)
//    }
//
//        fun draw_map(canvas:Canvas) {
//
//            val paint1 = Paint()
//
//            paint1.color = Color.rgb(255,0,0)
//            paint1.strokeWidth = 20f
//            canvas.drawCircle(0f,0f,20f,paint1)
//            if (coordinates.size != 0) {
//                run {
//                    var i = 0
//                    while (i < 10) {
//                        canvas.drawCircle(
//                            coordinates.get(i),
//                            coordinates.get(i + 1),
//                            20f,
//                            paint1
//                        )
//                        i = i + 2
//                    }
//                }
//                run {
//                    var i = 10
//                    while (i < 20) {
//                        canvas.drawCircle(
//                            coordinates.get(i),
//                            coordinates.get(i + 1),
//                            20f,
//                            paint1
//                        )
//                        i = i + 2
//                    }
//                }
//                var i = 20
//                while (i < 30) {
//                    canvas.drawCircle(
//                        coordinates.get(i),
//                        coordinates.get(i + 1),
//                        20f,
//                        paint1
//                    )
//                    i = i + 2
//                }
//            }
//        }
//    fun settings(coordinates1:ArrayList<Float>){
//        coordinates = coordinates1
//        invalidate()
//    }
//
//
//}
package com.example.kvest_new

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat


class CanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    var canvas:Canvas = Canvas()
    var num = 4
    var road_1_w = listOf(0.06f, 0.13f, 0.3f, 0.5f, 0.8f)
    var road_1_h = listOf(0.1f, 0.4f, 0.8f, 0.9f, 0.93f)
    var road_2_w = listOf(0.044f, 0.3f, 0.69f, 0.66f, 0.61f)
    var road_2_h = listOf(0.3f, 0.17f, 0.5f, 0.7f, 0.8f)
    var road_3_w = listOf(0.8f, 0.6f, 0.69f, 0.5f, 0.41f)
    var location: Int = 1
    var road_3_h  = listOf(0.3f, 0.17f, 0.5f, 0.7f, 0.8f)
    override fun onDraw(canvas: Canvas) {
        val paint2 = Paint()

        paint2.color = Color.rgb(255,0,0)
        paint2.strokeWidth = 20f
        this.canvas = canvas;
        draw_map(this.canvas);
        super.onDraw(canvas)
    }

    fun draw_map(canvas:Canvas) {

        val paint1 = Paint()

        paint1.strokeWidth = 10f

        paint1.color = Color.rgb(0,0,0)

        val width_map = canvas.width
        val height_map = canvas.height
        var radius = 25f

        // Рисуем координатную сетку, чтобы было проще потом координаты для точек на карте найти
//            for (i in 0..9){
//                    canvas.drawLine(
//                        0f,
//                        height_map * i * 0.1f,
//                        width_map * 1f,
//                        height_map * i *0.1f,
//                        paint1
//                    )
//            }
//
//            for (i in 0..9){
//                canvas.drawLine(
//                    width_map * i * 0.1f,
//                    0f,
//                    width_map * i * 0.1f,
//                    height_map * 1f,
//                    paint1
//                )
//            }

        // Когда все точки нанесены, то прячем код с сеткой


        //Тропа 1
        if (num == 1){
            paint1.color = android.graphics.Color.rgb(255,0,0)
            road_1_w.zip(road_1_h).forEach{ pair ->
            canvas.drawCircle(width_map * pair.first,height_map * pair.second,radius,paint1)
            }
            paint1.color = android.graphics.Color.rgb(128,0,128)
            canvas.drawCircle(width_map * road_1_w[location - 1],height_map * road_1_h[location - 1],radius,paint1)
//            canvas.drawCircle(width_map * 0.06f,height_map * 0.1f,radius,paint1)
//            canvas.drawCircle(width_map * 0.13f,height_map * 0.4f,radius,paint1)
//            canvas.drawCircle(width_map * 0.3f,height_map * 0.8f,radius,paint1)
//            canvas.drawCircle(width_map * 0.5f,height_map * 0.9f,radius,paint1)
//            canvas.drawCircle(width_map * 0.8f,height_map * 0.93f,radius,paint1)
        }

        if (num==2){
            paint1.color = Color.rgb(0,0,255)
            road_2_w.zip(road_2_h).forEach{ pair ->
                canvas.drawCircle(width_map * pair.first,height_map * pair.second,radius,paint1)
            }
            paint1.color = android.graphics.Color.rgb(128,0,128)
            canvas.drawCircle(width_map * road_2_w[location - 1],height_map * road_2_h[location - 1],radius,paint1)
//            canvas.drawCircle(width_map * 0.044f,height_map * 0.3f,radius,paint1)
//            canvas.drawCircle(width_map * 0.3f,height_map * 0.17f,radius,paint1)
//            canvas.drawCircle(width_map * 0.69f,height_map * 0.5f,radius,paint1)
//            canvas.drawCircle(width_map * 0.66f,height_map * 0.7f,radius,paint1)
//            canvas.drawCircle(width_map * 0.61f,height_map * 0.8f,radius,paint1)
        }
        //Тропа 2
        if (num==3){
            paint1.color = Color.rgb(0,255,0)
            road_3_w.zip(road_3_h).forEach{ pair ->
                canvas.drawCircle(width_map * pair.first,height_map * pair.second,radius,paint1)
            }
            paint1.color = android.graphics.Color.rgb(128,0,128)
            canvas.drawCircle(width_map * road_3_w[location - 1],height_map * road_3_h[location - 1],radius,paint1)
//            canvas.drawCircle(width_map * 0.8f,height_map * 0.3f,radius,paint1)
//            canvas.drawCircle(width_map * 0.6f,height_map * 0.17f,radius,paint1)
//            canvas.drawCircle(width_map * 0.69f,height_map * 0.5f,radius,paint1)
//            canvas.drawCircle(width_map * 0.5f,height_map * 0.7f,radius,paint1)
//            canvas.drawCircle(width_map * 0.41f,height_map * 0.8f,radius,paint1)
        }
        if (num==4){
            paint1.color = android.graphics.Color.rgb(255,0,0)
            canvas.drawCircle(width_map * 0.06f,height_map * 0.1f,radius,paint1)
            canvas.drawCircle(width_map * 0.13f,height_map * 0.4f,radius,paint1)
            canvas.drawCircle(width_map * 0.3f,height_map * 0.8f,radius,paint1)
            canvas.drawCircle(width_map * 0.5f,height_map * 0.9f,radius,paint1)
            canvas.drawCircle(width_map * 0.8f,height_map * 0.93f,radius,paint1)
            paint1.color = Color.rgb(0,0,255)
            canvas.drawCircle(width_map * 0.044f,height_map * 0.3f,radius,paint1)
            canvas.drawCircle(width_map * 0.3f,height_map * 0.17f,radius,paint1)
            canvas.drawCircle(width_map * 0.69f,height_map * 0.5f,radius,paint1)
            canvas.drawCircle(width_map * 0.66f,height_map * 0.7f,radius,paint1)
            canvas.drawCircle(width_map * 0.61f,height_map * 0.8f,radius,paint1)
            paint1.color = Color.rgb(0,255,0)
            canvas.drawCircle(width_map * 0.8f,height_map * 0.3f,radius,paint1)
            canvas.drawCircle(width_map * 0.6f,height_map * 0.17f,radius,paint1)
            canvas.drawCircle(width_map * 0.69f,height_map * 0.5f,radius,paint1)
            canvas.drawCircle(width_map * 0.5f,height_map * 0.7f,radius,paint1)
            canvas.drawCircle(width_map * 0.41f,height_map * 0.8f,radius,paint1)
        }

//            if (coordinates.size != 0) {
//                run {
//                    var i = 0
//                    while (i < 10) {
//                        canvas.drawCircle(
//                            coordinates.get(i),
//                            coordinates.get(i + 1),
//                            20f,
//                            paint1
//                        )
//                        i = i + 2
//                    }
//                }
//                run {
//                    var i = 10
//                    while (i < 20) {
//                        canvas.drawCircle(
//                            coordinates.get(i),
//                            coordinates.get(i + 1),
//                            20f,
//                            paint1
//                        )
//                        i = i + 2
//                    }
//                }
//                var i = 20
//                while (i < 30) {
//                    canvas.drawCircle(
//                        coordinates.get(i),
//                        coordinates.get(i + 1),
//                        20f,
//                        paint1
//                    )
//                    i = i + 2
//                }
//            }
    }

    fun settings(numb:Int, location1: Int = 0){
        num = numb
        location = location1
        invalidate()
    }


}