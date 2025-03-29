package com.example.kvest_new

import com.example.kvest_new.Question

// Структура тропы

data class Road(
    // Заголовок тропы
    var title:String,
    // Порядковый номер тропы
    var road_number: Int,
    // Вопросы тропы
    var questions_road: ArrayList<Question>,
    //Звуки героя
    var beginning:Int,
    var hint:Int,
    var fail:Int,
    var success:Int,
    // Картинка героя
    var beginning_img:Int,
    var dictionary_img: Int,
    var dictionary_img2: Int,
    var hint_img: Int,
    var final_img: Int,
    var question_num: Int

    )