package com.example.n_number_test

import java.util.Locale

//var INSTRUCTION_1 = "Welcome to the N back test game. Please tap on the screen to select the " +
//        "game level. Tap 1 times for easy level, 2 times for medium level and 3 times for hard level."
var INSTRUCTION_LEVEL_1 = "Please tap 1 times on the screen to start the game or tap 2 times to exit the game."
//var INSTRUCTION_LEVEL_2 = "Please tap 1 times on the screen to start the game or tap 2 times to exit the game."
//var INSTRUCTION_LEVEL_3 = "Please tap 1 times on the screen to start the game or tap 2 times to exit the game."
var INSTRUCTION_2 = "Yeah, you win the game."
var INSTRUCTION_3 = "Oops, you lose the game."
var INSTRUCTION_4 = "Closing the app"
//var INSTRUCTION_5 = "For bengali tap 1 times on the screen and for english tap 2 times."
var INSTRUCTION_6 = "Please tap 1 times on the screen to play the game again or tap 2 times to exit from the game."
var INSTRUCTION_7 = "Tell the total number of digits between the first and last digits"
//var INSTRUCTION_8 = "Tell me the total sum of the digits present between the first and the last number"
//var INSTRUCTION_9 = "Tell the total number of digits between the first and last digits"
var INSTRUCTION_10 = "Tell me how many times you heard the beep"

//const val _INSTRUCTION_1 = "এন ব্যাক টেস্ট গেমে স্বাগতম। গেমের স্তর নির্বাচন করতে স্ক্রিনে স্পর্শ করুন। সহজ স্তরের জন্য 1 বার," +
//        " মাঝারি স্তরের জন্য 2 বার এবং কঠিন স্তরের জন্য 3 বার স্পর্শ করুন"
const val _INSTRUCTION_LEVEL_1 = "গেম শুরু করতে স্ক্রিনে 1 বার স্পর্শ করুন অথবা গেম থেকে বের হতে 2 বার স্পর্শ করুন"
const val _INSTRUCTION_LEVEL_2 = "গেম শুরু করতে 1 বার স্পর্শ করুন অথবা গেম থেকে বের হতে 2 বার স্পর্শ করুন"
const val _INSTRUCTION_LEVEL_3 = "গেম শুরু করতে 1 বার স্পর্শ করুন অথবা গেম থেকে বের হতে 2 বার স্পর্শ করুন"
const val _INSTRUCTION_2 = "অসাধারণ, আপনি গেম জিতেছেন।"
const val _INSTRUCTION_3 = "উফ, আপনি ভুল বলেছেন।"
const val _INSTRUCTION_4 = "অ্যাপ বন্ধ করা হচ্ছে"
//const val _INSTRUCTION_5 = "বাংলা ভাষার জন্য স্ক্রিনে 1 বার স্পর্শ করুন এবং ইংরেজি ভাষার জন্য 2 বার স্পর্শ করুন"
const val _INSTRUCTION_6 = "গেম আবার খেলতে স্ক্রিনে 1 বার স্পর্শ করুন অথবা গেম থেকে বের হতে 2 বার স্পর্শ করুন"
const val _INSTRUCTION_7 = "প্রথম এবং শেষ সংখ্যার মধ্যে মোট কত সংখ্যা আছে তা বলুন"
//const val _INSTRUCTION_8 = "প্রথম এবং শেষ সংখ্যার মধ্যে উপস্থিত সংখ্যার মোট যোগফল আমাকে বলুন"
//const val _INSTRUCTION_9 = "first এবং last সংখ্যার মধ্যে মোট কত সংখ্যা আছে তা বলুন"
const val _INSTRUCTION_10 = "আপনি কতবার বিপ শব্দ শুনেছেন বলুন"

//var __INSTRUCTION_1 = "Tap on the screen to select the game level"
//const val ___INSTRUCTION_1 = "গেমের স্তর নির্বাচন করতে স্ক্রিনে স্পর্শ করুন"
//var __INSTRUCTION_LEVEL_1 = "You have selected the easy level"
//var __INSTRUCTION_LEVEL_2 = "You have selected the medium level"
//var __INSTRUCTION_LEVEL_3 = "You have selected the hard level"
//const val ___INSTRUCTION_LEVEL_1 = "আপনি সহজ স্তর নির্বাচন করেছেন"
//const val ___INSTRUCTION_LEVEL_2 = "আপনি মাঝারি স্তর নির্বাচন করেছেন"
//const val ___INSTRUCTION_LEVEL_3 = "আপনি কঠিন স্তর নির্বাচন করেছেন"

//bengali numbers
const val _0 = "শূন্য"
const val _1 = "এক"
const val _2 = "দুই"
const val _3 = "তিন"
const val _4 = "চার"
const val _5 = "পাঁচ"
const val _6 = "ছয়"
const val _7 = "সাত"
const val _8 = "আট"
const val _9 = "নয়"
var zero = "zero"
const val _zero = "শূন্য"
var first = "first"
const val _first = "প্রথম"
var second = "second"
const val _second = "দ্বিতীয়"
var third = "third"
const val _third = "তৃতীয়"
var fourth = "fourth"
const val _fourth = "চতুর্থ"
var fifth = "fifth"
const val _fifth = "পঞ্চম"
var sixth = "sixth"
const val _sixth = "ষষ্ঠ"
var seventh = "seventh"
const val _seventh = "সপ্তম"
var eighth = "eighth"
const val _eighth = "অষ্টম"
var ninth = "ninth"
const val _ninth = "নবম"
var and = "and"
const val _and = "এবং"

fun numberSelector(number: Int): String {
    return when (number) {
        0 -> zero
        1 -> first
        2 -> second
        3 -> third
        4 -> fourth
        5 -> fifth
        6 -> sixth
        7 -> seventh
        8 -> eighth
        9 -> ninth
        else -> ""
    }
}

//const val INSTRUCTION_1_ID = "INSTRUCTION_1_ID"
const val INSTRUCTION_LEVEL_ID = "INSTRUCTION_LEVEL_ID"
const val INSTRUCTION_RANDOM_NUMBER_LIST_ID = "INSTRUCTION_RANDOM_NUMBER_LIST_ID"
const val INSTRUCTION_SF_ID = "INSTRUCTION_SF_ID" //success fail id
const val INSTRUCTION_4_ID = "INSTRUCTION_4_ID"
//const val INSTRUCTION_5_ID = "INSTRUCTION_5_ID"
//const val _INSTRUCTION_5_ID = "_INSTRUCTION_5_ID"
const val INSTRUCTION_6_ID = "INSTRUCTION_6_ID"
const val INSTRUCTION_7_ID = "INSTRUCTION_7_ID"
//const val INSTRUCTION_8_ID = "INSTRUCTION_8_ID"
//const val INSTRUCTION_9_ID = "INSTRUCTION_9_ID"
const val INSTRUCTION_10_ID = "INSTRUCTION_10_ID"

//take touch Input
val delay: Long = 4
const val WAITING_TIME = 3000
var touch = 0
var destLanguage: Locale = Locale.getDefault()
var TOUCH_0_TIME = 0
var TOUCH_1_TIME = 0
var TOUCH_2_TIME = 0
var TOUCH_3_TIME = 0
var TOUCH_4_TIME = 0
var TOUCH_5_TIME = 0
var TOUCH_6_TIME = 0
var _TOUCH_1_TIME = 0
var _TOUCH_2_TIME = 0
var _TOUCH_3_TIME = 0
var _TOUCH_4_TIME = 0
