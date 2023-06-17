package com.example.n_number_test.data

import java.util.Date
import java.util.SimpleTimeZone

data class Task(var date: String, val time: String, var userName: String, var flowID: String, var taskNo: Int, var attempts: Int, var startTime: Int, var endTime: Int, var deltaTime: Int, var remarks: String)