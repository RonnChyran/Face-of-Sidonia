package moe.chyyran.sidonia


fun convertDaiji(num: Int): String {
    var c = ""
    when (num) {
        0 -> c = "零"
        1 -> c = "壱"
        2 -> c = "弐"
        3 -> c = "参"
        4 -> c = "四"
        5 -> c = "五"
        6 -> c = "六"
        7 -> c = "七"
        8 -> c = "八"
        9 -> c = "九"
    }
    return c
}



fun convertDaijiFull(num: Int): String {
    var c = ""
    when (num) {
        0 -> c = "零"
        1 -> c = "壱"
        2 -> c = "弐"
        3 -> c = "参"
        4 -> c = "肆"
        5 -> c = "伍"
        6 -> c = "陸"
        7 -> c = "漆"
        8 -> c = "捌"
        9 -> c = "玖"
    }
    return c
}


fun convertKyuujitai(num: Int): String {
    var c = ""
    when (num) {
        0 -> c = "零"
        1 -> c = "壹"
        2 -> c = "貳"
        3 -> c = "參"
        4 -> c = "肆"
        5 -> c = "伍"
        6 -> c = "陸"
        7 -> c = "柒"
        8 -> c = "捌"
        9 -> c = "玖"
    }
    return c
}


fun convertSuuji(num: Int): String {
    var c = ""
    when (num) {
        0 -> c = "〇"
        1 -> c = "一"
        2 -> c = "二"
        3 -> c = "三"
        4 -> c = "四"
        5 -> c = "五"
        6 -> c = "六"
        7 -> c = "七"
        8 -> c = "八"
        9 -> c = "九"
    }
    return c
}

fun convertArabic(num: Int): String {
    var c = ""
    when (num) {
        0 -> c = "0"
        1 -> c = "1"
        2 -> c = "2"
        3 -> c = "3"
        4 -> c = "4"
        5 -> c = "5"
        6 -> c = "6"
        7 -> c = "7"
        8 -> c = "8"
        9 -> c = "9"
    }
    return c
}