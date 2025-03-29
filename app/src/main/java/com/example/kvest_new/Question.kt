package com.example.kvest_new

// Здесь мы только описываем структуру каждого вопроса, а не его содержание.

// Для создания объекта вопроса необходимо обязательно заполнить поле title, то есть заголовок.
data class Question(
    // Заголовок вопроса
    var title: String,
    // Поле для текста вопроса
    var text: String,
    // Поле для подсказки
    var hint: String,
    // Поле для правильного ответа
    var answer: String,
    // Картинка вопроса
    var image: Int,
    // Значение QR
    var qr: String,
    // Возможно сделать список правильных ответов
    // var answers_list:ArrayList<String>,
    // Порядковый номер вопроса в тропе
    var number_in_road: Int,
    // Информация о растении
    var info1: String,
    var info2: String,
    var info3: String,
    var info4: String,
    var info5: String,
    var info6: String,


)
