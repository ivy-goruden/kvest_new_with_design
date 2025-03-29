package com.example.kvest_new

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.*
import android.widget.*
import android.widget.PopupMenu.OnMenuItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.example.kvest.Roads_data.get_Roads
import uk.co.deanwild.flowtextview.FlowTextView
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), MenuItem.OnMenuItemClickListener, OnMenuItemClickListener {


    var given_answers: ArrayList <String> = ArrayList()
    var temp_road_list: ArrayList <Road> = get_Roads()
    var curent_road = 0
    var voice_var = true
    var max_volume = 10.0
    var current_question = 1
    var PROMO = "BANANA"
    val MAX_QUESTIONS = 5
    val CAMERA_PER=100
    var result_data = 0
    var final_result = 0
    var result_qr: String = ""
    var temp_values: ArrayList <String> = ArrayList()
    var coordinates: ArrayList<Float> = ArrayList()
    var promo_visit = false
    var activity_val = "Start"
    var places: ArrayList <String> = ArrayList()
    var paths: ArrayList <String> = ArrayList()
    var dates: ArrayList <String> = ArrayList()
    var scores: ArrayList <String> = ArrayList()



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        var scanner_view = findViewById<CodeScannerView>(R.id.scanner_view)
        var codeScanner = CodeScanner(this, scanner_view)
        var main_menu_layout = findViewById<LinearLayout>(R.id.main_menu_layout)
        var road_map = findViewById<LinearLayout>(R.id.road_map)
        var question_layout = findViewById<LinearLayout>(R.id.question_layout)
        var qr_layout = findViewById<LinearLayout>(R.id.qr_layout)
        var dictionary_layout = findViewById<LinearLayout>(R.id.dictionary_layout)
        var test_result_layout = findViewById<LinearLayout>(R.id.test_result_layout)
        var final_layout = findViewById<LinearLayout>(R.id.final_layout)
        var rules_layout = findViewById<LinearLayout>(R.id.rules_layout)
        var map_layout = findViewById<LinearLayout>(R.id.map_layout)
        var hint_layout = findViewById<LinearLayout>(R.id.hint_layout)
        var helper_layout = findViewById<LinearLayout>(R.id.helper_layout)

        fun set_to_array(tag: String, pref: SharedPreferences): ArrayList<String> {
            var new = pref.getStringSet(tag, emptySet())
            var new2: ArrayList<String> = ArrayList()
            new2.addAll(new!!)
            return new2
        }

        var music: MediaPlayer = MediaPlayer.create(this@MainActivity, temp_road_list[0].beginning)
        val preference=getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        given_answers = set_to_array("answers", preference)
        curent_road = preference.getInt("road", 0)
        current_question = preference.getInt("question", 1)
        final_result = preference.getInt("result", 0)
        places = set_to_array("places", preference)
        paths = set_to_array("paths", preference)
        dates = set_to_array("dates", preference)
        temp_values = set_to_array("temp_values", preference)
//        var scores1: ArrayList<String> = set_to_array("scores_set", preference).map { it.toInt() }.toTypedArray()
        scores = set_to_array("scores", preference)
        activity_val = preference.getString("activity", "Start").toString()
        Log.d("TAG", temp_values.toString())
        Log.d("TAG", places.toString())
        Log.d("TAG", scores.toString())

        fun getAllChildrenBFS(v:View): ArrayList<View> {
            var visited = ArrayList<View>()
            var unvisited = ArrayList<View>()
            unvisited.add(v)

            while (!unvisited.isEmpty()) {
                var child = unvisited.removeAt(0)
                visited.add(child)
                if (!(child is ViewGroup)) continue
                var group = child
                var childCount = group.getChildCount()
                var i = 0
                while (i<childCount){
                    unvisited.add(group.getChildAt(i))
                    i++
                }
            }

            return visited
        }

        fun findViews() {
            val canvas_layout = findViewById<LinearLayoutCompat>(R.id.canvas_layout)
            var all_children = getAllChildrenBFS(canvas_layout)
            var i = 0
            Log.d("TAG", all_children.toString())
            while(i<all_children.size){
                var v = all_children[i]
                if (v is TextView) {
                    //do whatever you want ...
                    var font = resources.getFont(R.font.vetrino)
                    v.setTypeface(font)
                }
                else if(v is Button){
                    var font = resources.getFont(R.font.vetrino)
                    v.setTypeface(font)
                    v.setPadding(80, 30, 80, 30)
                    v.setTextColor(resources.getColor(R.color.white))
                    v.setTextSize(24F)
                    v.setBackgroundResource(R.drawable.flat_btn)


                }
                i += 1
            }


        }
        findViews()

        val flowTextView5 = findViewById<View>(R.id.ftv5) as FlowTextView
        val labelColor = resources.getColor(R.color.white)
        val colorString = String.format("%X", labelColor).substring(2) // !!strip alpha value!!
        val html = Html.fromHtml(
                String.format("<font color=\"#%s\">"+resources.getString(R.string.rules5)+"</font>", colorString), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        flowTextView5.text = html
        flowTextView5.invalidate()

        val flowTextView7 = findViewById<View>(R.id.ftv7) as FlowTextView
        flowTextView7.text = resources.getString(R.string.rules7)
        flowTextView7.invalidate()

        val flowTextView8 = findViewById<View>(R.id.ftv8) as FlowTextView
        flowTextView8.text = resources.getString(R.string.rules8)
        flowTextView8.invalidate()

        val flowTextView9 = findViewById<View>(R.id.about_ftv1) as FlowTextView
        flowTextView9.text = resources.getString(R.string.about4)
        flowTextView9.invalidate()
        val flowTextView10 = findViewById<View>(R.id.about_ftv2) as FlowTextView
        flowTextView10.text = resources.getString(R.string.about9)
        flowTextView10.invalidate()





        fun setAllVisGone(temp:View){
            main_menu_layout.visibility = View.GONE
            road_map.visibility = View.GONE
            question_layout.visibility = View.GONE
            qr_layout.visibility = View.GONE
            dictionary_layout.visibility = View.GONE
            test_result_layout.visibility = View.GONE
            final_layout.visibility = View.GONE
            rules_layout.visibility = View.GONE
            map_layout.visibility = View.GONE
            hint_layout.visibility = View.GONE
            helper_layout.visibility = View.GONE
            temp.visibility = View.VISIBLE

        }

        val continue_btn = findViewById<Button>(R.id.continue_btn)

        var start_game_btn = findViewById<Button>(R.id.start_game_btn)



        if (activity_val != "Start"){

            continue_btn.visibility = View.VISIBLE
            start_game_btn.setOnClickListener{
                given_answers = ArrayList()
                temp_road_list = get_Roads()
                curent_road = 0
                current_question = 1
                final_result = 0
                temp_values = ArrayList()
                temp_values = ArrayList()
                result_qr = ""
                promo_visit = false
                continue_btn.visibility = View.GONE
                activity_val = "Rules"
                setAllVisGone(rules_layout)

            }

        }else{
            start_game_btn.setOnClickListener {
                setAllVisGone(rules_layout)
                activity_val = "Rules"


            }
        }

        fun checkPermission(permission: String, requestCode: Int) {
            if (ContextCompat.checkSelfPermission(this@MainActivity, permission) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
            } else {
                Toast.makeText(this@MainActivity, "Permission already granted", Toast.LENGTH_SHORT).show()
            }
        }

        fun setDataToView(road_num: Int, q_number: Int){
            var question_title = findViewById<TextView>(R.id.question_title)
            question_title.setText(temp_road_list[road_num-1].questions_road[q_number-1].text)
            var given_answer = findViewById<TextView>(R.id.given_answer)
            given_answer.setText("")
        }

        fun setAllVisGone_test(temp:View){
            var result_start = findViewById<LinearLayout>(R.id.result_start)
            result_start.visibility = View.GONE
            var promo_start = findViewById<LinearLayout>(R.id.promo_start)
            promo_start.visibility = View.GONE
            var promo_submit = findViewById<LinearLayout>(R.id.promo_submit)
            promo_submit.visibility = View.GONE
            temp.visibility = View.VISIBLE

        }

        continue_btn.setOnClickListener{
//            if (activity_val == "Rules"){}
            when (activity_val) {
                "Rules" -> setAllVisGone(rules_layout)
                "Map" -> {
//                    var map_roads = findViewById<LinearLayout>(R.id.map_roads)
//                    map_roads.visibility = View.VISIBLE
                    var map_drawing = findViewById<CanvasView>(R.id.map_drawing)


                    map_drawing.visibility = View.GONE
                    var i = 0

                    map_drawing.settings(4)
                    map_drawing.visibility = View.VISIBLE
                    var road_map = findViewById<LinearLayout>(R.id.road_map)
                    setAllVisGone(road_map)
                    setAllVisGone(road_map)
                }
                "Helper" -> {
                    var helper_pic = findViewById<ImageView>(R.id.helper_pic)
                    helper_pic.setImageResource(temp_road_list[curent_road-1].beginning_img)
                    music = MediaPlayer.create(this@MainActivity, temp_road_list[curent_road-1].beginning)
                    music.start()
                    setAllVisGone(helper_layout)
                }
                "Qr" -> {
                    var qr_input = findViewById<TextView>(R.id.qr_input)
                    qr_input.setText("")
                    var qr_value = findViewById<TextView>(R.id.qr_value)
                    qr_value.setText(temp_road_list[curent_road-1].questions_road[current_question-1].qr)
                    checkPermission(Manifest.permission.CAMERA, CAMERA_PER)
                    setAllVisGone(qr_layout)
                }
                "Question" -> {
                    setDataToView(curent_road, current_question)
                    setAllVisGone(question_layout)
                }
                "Result" -> {
                    var v1 = findViewById<TextView>(R.id.v1)
                    var v2 = findViewById<TextView>(R.id.v2)
                    var v3 = findViewById<TextView>(R.id.v3)
                    var v4 = findViewById<TextView>(R.id.v4)
                    var v5 = findViewById<TextView>(R.id.v5)
                    var s1 = findViewById<ImageView>(R.id.s1)
                    var s2 = findViewById<ImageView>(R.id.s2)
                    var s3 = findViewById<ImageView>(R.id.s3)
                    var s4 = findViewById<ImageView>(R.id.s4)
                    var s5 = findViewById<ImageView>(R.id.s5)
                    var answer_fields =  listOf<TextView>(v1, v2, v3, v4, v5)
                    var tick_fields =  listOf<ImageView>(s1, s2, s3, s4, s5)
                    var i: Int = 0
                    Log.d("TAG", given_answers.toString())
                    while (i < answer_fields.size) {
                        answer_fields[i].setText(given_answers[i])
                        if (given_answers[i].lowercase() != get_Roads()[curent_road-1].questions_road[i].answer.lowercase() && given_answers[i].lowercase() != get_Roads()[curent_road-1].questions_road[i].answer.lowercase()+' '){
                            tick_fields[i].setImageResource(R.drawable.cross)
                        }
                        else{
                            final_result+=1
                        }
                        i += 1

                    }
                    var test_result = findViewById<TextView>(R.id.test_result)
                    test_result.setText(final_result.toString()+"/5")
                    var test_result_layout = findViewById<LinearLayout>(R.id.test_result_layout)
                    var result_start = findViewById<LinearLayout>(R.id.result_start)
                    setAllVisGone_test(result_start)
                    setAllVisGone(test_result_layout)
                }
                "Final" -> {
                    var final_img = findViewById<ImageView>(R.id.final_img)
                    final_img.setImageResource(get_Roads()[curent_road-1].final_img)
                    var final_result = findViewById<TextView>(R.id.final_result)
                    final_result.setText(this.final_result.toString()+"/5")

                    music = if (this.final_result <=3){
                        MediaPlayer.create(this@MainActivity, temp_road_list[curent_road-1].fail)

                    } else{
                        MediaPlayer.create(this@MainActivity, temp_road_list[curent_road-1].success)
                    }
                    music.start()
                    setAllVisGone(final_layout)
                }

            }
        }




        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.CONTINUOUS // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
                if (it.text.lowercase().trim() == temp_road_list[curent_road-1].questions_road[current_question-1].qr.lowercase()){
                    var dictionary_img = findViewById<ImageView>(R.id.dictionary_img)
                    dictionary_img.setImageResource(temp_road_list[curent_road-1].questions_road[current_question-1].image)
                    var helper_dict1 = findViewById<ImageView>(R.id.helper_dict1)
                    helper_dict1.setImageResource(temp_road_list[curent_road-1].dictionary_img)
                    var helper_dict2 = findViewById<ImageView>(R.id.helper_dict2)
                    helper_dict2.setImageResource(temp_road_list[curent_road-1].dictionary_img2)

                    var info = findViewById<TextView>(R.id.info)

                    info.setText(temp_road_list[curent_road-1].questions_road[current_question-1].qr.uppercase(
                        Locale.getDefault()
                    ))
                    var info1 = findViewById<TextView>(R.id.dictionary_text1)
                    info1.setText(temp_road_list[curent_road-1].questions_road[current_question-1].info1)
                    var info3 = findViewById<TextView>(R.id.dictionary_text3)
                    info3.setText(temp_road_list[curent_road-1].questions_road[current_question-1].info3)
                    var info5 = findViewById<TextView>(R.id.dictionary_text5)
                    info5.setText(temp_road_list[curent_road-1].questions_road[current_question-1].info5)
                    var info2 = findViewById<View>(R.id.dictionary_text2) as FlowTextView
                    info2.text = temp_road_list[curent_road-1].questions_road[current_question-1].info2
                    info2.invalidate()
                    var info4 = findViewById<View>(R.id.dictionary_text4) as FlowTextView
                    info4.text = temp_road_list[curent_road-1].questions_road[current_question-1].info4
                    info4.invalidate()
                    var info6 = findViewById<View>(R.id.dictionary_text6) as FlowTextView
                    info6.text = temp_road_list[curent_road-1].questions_road[current_question-1].info6
                    info6.invalidate()

                    setAllVisGone(dictionary_layout)
                }

            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }

        scanner_view.setOnClickListener {
            codeScanner.startPreview()
        }












        @Override
        fun onRequestPermissionsResult(requestCode: Int,
                                       permissions: Array<String>,
                                       grantResults: IntArray) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (requestCode == CAMERA_PER) {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this@MainActivity, "Camera Permission Granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }


        }









        var voice = findViewById<ImageButton>(R.id.voice)
        voice.setOnClickListener{
            voice_var = !voice_var
            if (voice_var){
                voice.setImageResource(R.drawable.volume_up)
                val volume_num = (1 - (Math.log(max_volume - 6) / Math.log(max_volume))).toFloat()
                music.setVolume(volume_num,volume_num);
            }
            else{
                music.setVolume(0F,0F);
                voice.setImageResource(R.drawable.volume_off)
            }
        }


        var to_map = findViewById<Button>(R.id.to_map)
        to_map.setOnClickListener{
            activity_val = "Map"
//            var map_roads = findViewById<LinearLayout>(R.id.map_roads)
//            map_roads.visibility = View.VISIBLE
            var map_drawing = findViewById<CanvasView>(R.id.map_drawing)


            map_drawing.visibility = View.GONE
            var i = 0

            map_drawing.settings(4)
//            map_roads.visibility = View.GONE
            map_drawing.visibility = View.VISIBLE
            var road_map = findViewById<LinearLayout>(R.id.road_map)
            setAllVisGone(road_map)
            Log.d("LOG", coordinates.toString())
        }
        var road1_btn = findViewById<Button>(R.id.road1_btn)
        road1_btn.setOnClickListener{
            curent_road = 1
            var helper_pic = findViewById<ImageView>(R.id.helper_pic)
            helper_pic.setImageResource(temp_road_list[curent_road-1].beginning_img)
            music = MediaPlayer.create(this@MainActivity, temp_road_list[curent_road-1].beginning)
            music.start()
            activity_val = "Helper"
            setAllVisGone(helper_layout)
        }
        var road2_btn = findViewById<Button>(R.id.road2_btn)
        road2_btn.setOnClickListener{
            curent_road = 2
            activity_val = "Helper"
            var helper_pic = findViewById<ImageView>(R.id.helper_pic)
            helper_pic.setImageResource(temp_road_list[curent_road-1].beginning_img)
            music = MediaPlayer.create(this@MainActivity, temp_road_list[curent_road-1].beginning)
            music.start()
            setAllVisGone(helper_layout)
        }
        var road3_btn = findViewById<Button>(R.id.road3_btn)
        road3_btn.setOnClickListener{
            curent_road = 3
            activity_val = "Helper"
            var helper_pic = findViewById<ImageView>(R.id.helper_pic)
            helper_pic.setImageResource(temp_road_list[curent_road-1].beginning_img)
            music = MediaPlayer.create(this@MainActivity, temp_road_list[curent_road-1].beginning)
            music.start()
            setAllVisGone(helper_layout)
        }
        var helper_btn = findViewById<Button>(R.id.helper_btn)
        helper_btn.setOnClickListener{

            var spinner_val = findViewById<Spinner>(R.id.map_spinner).getSelectedItem().toString()
            when (spinner_val){
                "Горный воздух" -> {temp_values.add("Горный\nвоздух")}
                "Ботанический сад" -> {temp_values.add("Бот.\nсад")}
            }
            temp_values.add(curent_road.toString())
            music.stop()
            activity_val = "Qr"
            var qr_input = findViewById<TextView>(R.id.qr_input)
            qr_input.setText("")
            var qr_value = findViewById<TextView>(R.id.qr_value)
            qr_value.setText(temp_road_list[curent_road-1].questions_road[current_question-1].qr)
            checkPermission(Manifest.permission.CAMERA, CAMERA_PER)
            setAllVisGone(qr_layout)
        }
        var to_qr_btn = findViewById<Button>(R.id.to_qr_btn)
        to_qr_btn.setOnClickListener{
            checkPermission(Manifest.permission.CAMERA, CAMERA_PER)
            activity_val = "Qr"
            current_question += 1
            var given_answer = findViewById<TextView>(R.id.given_answer)
            given_answers.add(given_answer.text.toString())
//            if (given_answer.text!!.last() != ' ') {
//                given_answers.add(given_answer.text.toString())
//            }else{
//                given_answers.add(given_answer.text.delete(-1).toString())
//            }
            if (current_question <= temp_road_list[0].questions_road.size){
                var qr_input = findViewById<TextView>(R.id.qr_input)
                var qr_value = findViewById<TextView>(R.id.qr_value)
                qr_value.setText(temp_road_list[curent_road-1].questions_road[current_question-1].qr)

                qr_input.setText("")
                var qr_layout = findViewById<LinearLayout>(R.id.qr_layout)
                setAllVisGone(qr_layout)
            }else{
                activity_val="Result"
                var v1 = findViewById<TextView>(R.id.v1)
                var v2 = findViewById<TextView>(R.id.v2)
                var v3 = findViewById<TextView>(R.id.v3)
                var v4 = findViewById<TextView>(R.id.v4)
                var v5 = findViewById<TextView>(R.id.v5)
                var s1 = findViewById<ImageView>(R.id.s1)
                var s2 = findViewById<ImageView>(R.id.s2)
                var s3 = findViewById<ImageView>(R.id.s3)
                var s4 = findViewById<ImageView>(R.id.s4)
                var s5 = findViewById<ImageView>(R.id.s5)
                var answer_fields =  listOf<TextView>(v1, v2, v3, v4, v5)
                var tick_fields =  listOf<ImageView>(s1, s2, s3, s4, s5)
                var i: Int = 0
                Log.d("TAG", given_answers.toString())
                while (i < answer_fields.size) {
                    answer_fields[i].setText(given_answers[i])
                    if (given_answers[i].lowercase() != get_Roads()[curent_road-1].questions_road[i].answer.lowercase() && given_answers[i].lowercase() != get_Roads()[curent_road-1].questions_road[i].answer.lowercase()+' '){
                        tick_fields[i].setImageResource(R.drawable.cross)
                    }
                    else{
                        tick_fields[i].setImageResource(R.drawable.tick)
                        final_result+=1
                    }
                    i += 1

                }
                var test_result = findViewById<TextView>(R.id.test_result)
                test_result.setText(final_result.toString()+"/5")
                var test_result_layout = findViewById<LinearLayout>(R.id.test_result_layout)
                var result_start = findViewById<LinearLayout>(R.id.result_start)
                setAllVisGone_test(result_start)
                setAllVisGone(test_result_layout)
            }

        }
        var to_dictionary = findViewById<Button>(R.id.to_dictionary)
        to_dictionary.setOnClickListener {
            var qr_input = findViewById<TextView>(R.id.qr_input)
            activity_val = "Question"
            if (qr_input.text.toString().lowercase().trim() == temp_road_list[curent_road-1].questions_road[current_question-1].qr.lowercase() ){
                var dictionary_img = findViewById<ImageView>(R.id.dictionary_img)
                dictionary_img.setImageResource(temp_road_list[curent_road-1].questions_road[current_question-1].image)
                var info = findViewById<TextView>(R.id.info)
                info.setText(temp_road_list[curent_road-1].questions_road[current_question-1].qr.uppercase(
                    Locale.getDefault()
                ))
                var helper_dict1 = findViewById<ImageView>(R.id.helper_dict1)
                helper_dict1.setImageResource(temp_road_list[curent_road-1].dictionary_img)
                var helper_dict2 = findViewById<ImageView>(R.id.helper_dict2)
                helper_dict2.setImageResource(temp_road_list[curent_road-1].dictionary_img2)
                var info1 = findViewById<TextView>(R.id.dictionary_text1)
                info1.setText(temp_road_list[curent_road-1].questions_road[current_question-1].info1)
                var info3 = findViewById<TextView>(R.id.dictionary_text3)
                info3.setText(temp_road_list[curent_road-1].questions_road[current_question-1].info3)
                var info5 = findViewById<TextView>(R.id.dictionary_text5)
                info5.setText(temp_road_list[curent_road-1].questions_road[current_question-1].info5)
                var info2 = findViewById<View>(R.id.dictionary_text2) as FlowTextView
                info2.text = temp_road_list[curent_road-1].questions_road[current_question-1].info2
                info2.invalidate()
                var info4 = findViewById<View>(R.id.dictionary_text4) as FlowTextView
                info4.text = temp_road_list[curent_road-1].questions_road[current_question-1].info4
                info4.invalidate()
                var info6 = findViewById<View>(R.id.dictionary_text6) as FlowTextView
                info6.text = temp_road_list[curent_road-1].questions_road[current_question-1].info6
                info6.invalidate()
                setAllVisGone(dictionary_layout)
            }
        }
        var question_btn = findViewById<Button>(R.id.question_btn)
        question_btn.setOnClickListener {
            activity_val = "Question"
            setDataToView(curent_road, current_question)
            setAllVisGone(question_layout)
        }
        var back_dictionary = findViewById<Button>(R.id.back_dictionary)
        back_dictionary.setOnClickListener {
            activity_val = "Question"
            var dictionary_img = findViewById<ImageView>(R.id.dictionary_img)
            dictionary_img.setImageResource(temp_road_list[curent_road-1].questions_road[current_question-1].image)
            var info = findViewById<TextView>(R.id.info)
            info.setText(temp_road_list[curent_road-1].questions_road[current_question-1].qr.uppercase(
                Locale.getDefault()
            ))
            var helper_dict1 = findViewById<ImageView>(R.id.helper_dict1)
            helper_dict1.setImageResource(temp_road_list[curent_road-1].dictionary_img)
            var helper_dict2 = findViewById<ImageView>(R.id.helper_dict2)
            helper_dict2.setImageResource(temp_road_list[curent_road-1].dictionary_img2)
            var info1 = findViewById<TextView>(R.id.dictionary_text1)
            info1.setText(temp_road_list[curent_road-1].questions_road[current_question-1].info1)
            var info3 = findViewById<TextView>(R.id.dictionary_text3)
            info3.setText(temp_road_list[curent_road-1].questions_road[current_question-1].info3)
            var info5 = findViewById<TextView>(R.id.dictionary_text5)
            info5.setText(temp_road_list[curent_road-1].questions_road[current_question-1].info5)
            var info2 = findViewById<View>(R.id.dictionary_text2) as FlowTextView
            info2.text = temp_road_list[curent_road-1].questions_road[current_question-1].info2
            info2.invalidate()
            var info4 = findViewById<View>(R.id.dictionary_text4) as FlowTextView
            info4.text = temp_road_list[curent_road-1].questions_road[current_question-1].info4
            info4.invalidate()
            var info6 = findViewById<View>(R.id.dictionary_text6) as FlowTextView
            info6.text = temp_road_list[curent_road-1].questions_road[current_question-1].info6
            info6.invalidate()
            setAllVisGone(dictionary_layout)
        }
        var hint_btn = findViewById<Button>(R.id.hint_btn)
        hint_btn.setOnClickListener {
            activity_val = "Question"
            music.stop()
            setDataToView(curent_road, current_question)
            setAllVisGone(question_layout)
        }
        var to_hint_btn = findViewById<Button>(R.id.to_hint_btn)
        to_hint_btn.setOnClickListener {
            activity_val = "Question"
            var hint_text = findViewById<TextView>(R.id.hint_text)
            hint_text.text = temp_road_list[curent_road-1].questions_road[current_question-1].hint
            var hint_image = findViewById<ImageView>(R.id.hint_image)
            hint_image.setImageResource(temp_road_list[curent_road-1].hint_img)
            music = MediaPlayer.create(this@MainActivity, temp_road_list[curent_road-1].hint)
            music.start()
            setAllVisGone(hint_layout)
        }
        var map_btn = findViewById<Button>(R.id.map_btn)
        map_btn.setOnClickListener {
            activity_val = "Qr"
            var map_drawing2 = findViewById<CanvasView>(R.id.map_drawing2)
            map_drawing2.settings(curent_road, current_question)
            setAllVisGone(map_layout)
        }
        var map_btn_back = findViewById<Button>(R.id.map_btn_back)
        map_btn_back.setOnClickListener {
            activity_val = "Qr"
            var qr_value = findViewById<TextView>(R.id.qr_value)
            qr_value.text = temp_road_list[curent_road-1].questions_road[current_question-1].qr
            var qr_input = findViewById<TextView>(R.id.qr_input)
            qr_input.setText("")
            setAllVisGone(qr_layout)
        }
        var finish_btn = findViewById<Button>(R.id.finish_btn)
        finish_btn.setOnClickListener {
            continue_btn.visibility = View.GONE
            activity_val = "Start"
            music.stop()
            given_answers = ArrayList()
            temp_road_list = get_Roads()
            curent_road = 0
            current_question = 1
            result_qr = ""
            promo_visit = false
            temp_values.add(final_result.toString())
            final_result = 0
            val formatter = SimpleDateFormat("dd.MM.yyyy")
            val date = Date()
            val current = formatter.format(date)
            temp_values.add(current)
            val  save_num = places.size
            places.add(save_num.toString() + temp_values[0])
            paths.add(save_num.toString() +temp_values[1])
            scores.add(save_num.toString() +temp_values[2])
            dates.add(save_num.toString() +temp_values[3])
            temp_values = ArrayList()
            setAllVisGone(main_menu_layout)
        }
        var result_start_btn = findViewById<Button>(R.id.result_start_btn)
        result_start_btn.setOnClickListener {
            if (!promo_visit){
                var promo_start = findViewById<LinearLayout>(R.id.promo_start)
                setAllVisGone_test(promo_start)
            }
            else{
                var final_img = findViewById<ImageView>(R.id.final_img)
                final_img.setImageResource(get_Roads()[curent_road-1].final_img)
                var final_result = findViewById<TextView>(R.id.final_result)
                final_result.setText(this.final_result.toString()+"/5")
                music = if (this.final_result <=3){
                    MediaPlayer.create(this@MainActivity, temp_road_list[curent_road-1].fail)

                } else{
                    MediaPlayer.create(this@MainActivity, temp_road_list[curent_road-1].success)
                }
                music.start()

                setAllVisGone(final_layout)
            }
        }
        var yes = findViewById<Button>(R.id.yes)
        yes.setOnClickListener {
            var promo_submit = findViewById<LinearLayout>(R.id.promo_submit)
            setAllVisGone_test(promo_submit)
        }
        var no = findViewById<Button>(R.id.no)
        no.setOnClickListener {
            var final_img = findViewById<ImageView>(R.id.final_img)
            final_img.setImageResource(get_Roads()[curent_road-1].final_img)
            var final_result = findViewById<TextView>(R.id.final_result)
            final_result.setText(this.final_result.toString()+"/5")

            music = if (this.final_result <=3){
                MediaPlayer.create(this@MainActivity, temp_road_list[curent_road-1].fail)

            } else{
                MediaPlayer.create(this@MainActivity, temp_road_list[curent_road-1].success)
            }
            music.start()

            Log.d("TAG", places.toString())


            setAllVisGone(final_layout)
        }
        var back = findViewById<Button>(R.id.back)
        back.setOnClickListener {
            var promo_start = findViewById<LinearLayout>(R.id.promo_start)
            setAllVisGone_test(promo_start)
        }
        var submit = findViewById<Button>(R.id.submit)
        submit.setOnClickListener {
            var promocode = findViewById<TextView>(R.id.promocode)
            if (promocode.text.toString() == PROMO){
                final_result+=2
                var test_result = findViewById<TextView>(R.id.test_result)
                test_result.setText(final_result.toString()+"/5")
                var result_start = findViewById<LinearLayout>(R.id.result_start)
                setAllVisGone(test_result_layout)
                setAllVisGone_test(result_start)
                promo_visit = true
            }

        }




        fun showMenu(v: View) {
            PopupMenu(this, v).apply {
                // MainActivity implements OnMenuItemClickListener
                setOnMenuItemClickListener(this@MainActivity)
                inflate(R.menu.menu)
                show()
            }
        }

        fun showPopup(v: View) {
            val popup = PopupMenu(this, v)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.menu, popup.menu)
            popup.show()
            showMenu(v)
        }

        var menu_btn = findViewById<ImageButton>(R.id.menu_btn)
        menu_btn.setOnClickListener{
            showPopup(menu_btn)
        }






}
    fun setAllVisGone_menu(temp:View){
        var menu_play_layout = findViewById<LinearLayout>(R.id.menu_play_layout)
        menu_play_layout.visibility = View.GONE
        var bot_sad_layout = findViewById<LinearLayout>(R.id.bot_sad_layout)
        bot_sad_layout.visibility = View.GONE
        var gorniy_vozdyh_layout = findViewById<LinearLayout>(R.id.gorniy_vozdyh_layout)
        gorniy_vozdyh_layout.visibility = View.GONE
        var best_result_layout = findViewById<LinearLayout>(R.id.best_result_layout)
        best_result_layout.visibility = View.GONE
        var about_us_layout = findViewById<LinearLayout>(R.id.about_us_layout)
        about_us_layout.visibility = View.GONE
        temp.visibility = View.VISIBLE

    }


    fun create_text_view(v: TextView, value: String): TextView{
        v.setText(value)
        v.setPadding(5, 0, 5, 0)
        v.setTextSize(19F)
        v.gravity = Gravity.CENTER
        v.background = resources.getDrawable(R.drawable.back);
        v.setTextColor(Color.parseColor("#000000"))
        v.layoutParams = TableRow.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT,
            ActionBar.LayoutParams.MATCH_PARENT
        )
        return v


    }
    @SuppressLint("ResourceAsColor")
    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.play -> {
                var menu_play_layout = findViewById<LinearLayout>(R.id.menu_play_layout)
                setAllVisGone_menu(menu_play_layout)
                true
            }
            R.id.best_result -> {
                var best_result_layout = findViewById<LinearLayout>(R.id.best_result_layout)

                var best_result_text = findViewById<TextView>(R.id.best_result_value)
                if (scores.size >0){
                    var best_result = scores.map {
                        it.toInt()
                        it.drop(1)
                    }.max()
                    Log.d("TAG", places.toString())
                    Log.d("TAG", paths.toString())
                    Log.d("TAG", dates.toString())
                    Log.d("TAG", scores.toString())
                    best_result_text.setText(best_result.toString() + "/" + MAX_QUESTIONS.toString())

                }
                else{
                    best_result_text.setText("?/" + MAX_QUESTIONS.toString())
                }
                var main_table = findViewById<TableLayout>(R.id.best_table)

                while (result_data < scores.size){
                    val row = TableRow(this)
                    row.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT)
                    val place_cell = create_text_view(TextView(this), places[result_data].drop(1))
                    val path_cell = create_text_view(TextView(this), paths[result_data].drop(1))
                    val dates_cell = create_text_view(TextView(this), dates[result_data].drop(1))
                    val score_cell = create_text_view(TextView(this), scores[result_data].drop(1))

                    row.addView(place_cell)
                    row.addView(path_cell)
                    row.addView(dates_cell)
                    row.addView(score_cell)
                    main_table.addView(row, TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT))
                    result_data+=1

                }
                setAllVisGone_menu(best_result_layout)
                true
            }
            R.id.bot_sad -> {
                var bot_sad_layout = findViewById<LinearLayout>(R.id.bot_sad_layout)
                setAllVisGone_menu(bot_sad_layout)
                true
            }
            R.id.gorniy_vozdyh -> {
                var gorniy_vozdyh = findViewById<LinearLayout>(R.id.gorniy_vozdyh_layout)
                setAllVisGone_menu(gorniy_vozdyh)
                true
            }
            R.id.about_us -> {
                var about_us_layout = findViewById<LinearLayout>(R.id.about_us_layout)
                setAllVisGone_menu(about_us_layout)
                true
            }
            else -> false
        }
    }

    override fun onStop() {
        super.onStop()
        val sharedPref = getSharedPreferences("my_prefs",MODE_PRIVATE).edit()
        sharedPref.putInt("result", final_result)
        sharedPref.putInt("road", curent_road)
        sharedPref.putInt("question", current_question)
        val answers: MutableSet<String> = HashSet()
        answers.addAll(given_answers)
        sharedPref.putStringSet("answers", answers)

        val places_set: MutableSet<String> = HashSet()
        places_set.addAll(places)
        sharedPref.putStringSet("places", places_set)

        val paths_set: MutableSet<String> = HashSet()
        paths_set.addAll(paths)
        sharedPref.putStringSet("paths", paths_set)

        val dates_set: MutableSet<String> = HashSet()
        dates_set.addAll(dates)
        sharedPref.putStringSet("dates", dates_set)

        val scores_set: MutableSet<String> = HashSet()
        scores_set.addAll(scores)
        sharedPref.putStringSet("scores", scores_set)

        val temp_set: MutableSet<String> = HashSet()
        temp_set.addAll(temp_values)
        sharedPref.putStringSet("temp_list", temp_set)

        sharedPref.putString("activity", activity_val)

        sharedPref.apply()
    }

    override fun onPause() {
        super.onPause()
        val sharedPref = getSharedPreferences("my_prefs", MODE_PRIVATE).edit()
        sharedPref.putInt("result", final_result)
        sharedPref.putInt("road", curent_road)
        sharedPref.putInt("question", current_question)
        val answers: MutableSet<String> = HashSet()
        answers.addAll(given_answers)
        sharedPref.putStringSet("answers", answers)

        val places_set: MutableSet<String> = HashSet()
        places_set.addAll(places)
        sharedPref.putStringSet("places", places_set)

        val paths_set: MutableSet<String> = HashSet()
        paths_set.addAll(paths)
        sharedPref.putStringSet("paths", paths_set)

        val dates_set: MutableSet<String> = HashSet()
        dates_set.addAll(dates)
        sharedPref.putStringSet("dates", dates_set)

        val scores_set: MutableSet<String> = HashSet()
        scores_set.addAll(scores)
        sharedPref.putStringSet("places", places_set)

        sharedPref.putString("activity", activity_val)

        sharedPref.apply()

//        var scanner_view = findViewById<CodeScannerView>(R.id.scanner_view)
//        var codeScanner = CodeScanner(this, scanner_view)
//        codeScanner.releaseResources()

    }


    override fun onResume() {
        super.onResume()
//        var scanner_view = findViewById<CodeScannerView>(R.id.scanner_view)
//        var codeScanner = CodeScanner(this, scanner_view)
//        codeScanner.startPreview()
    }





}


