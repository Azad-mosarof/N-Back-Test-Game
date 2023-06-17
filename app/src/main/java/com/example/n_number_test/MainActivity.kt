package com.example.n_number_test

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.n_number_test.data.Task
import com.example.n_number_test.databinding.ActivityMainBinding
import com.google.gson.Gson
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Timer
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    var TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    // For Text to Speech
    private var tts: TextToSpeech? = null
    var gameMode = 0
    var changeLangMode: Boolean = false
    var correctOutput = 0

    var task_1_attempt = 1
    var task_2_attempt = 1
    var task_3_attempt = 1
    var task_4_attempt = 1
    var flow_Id = ""
    var randomNumberList = mutableListOf<Int>()
    private lateinit var userName: String
    private lateinit var backgroundMediaPlayer: MediaPlayer
    private lateinit var tapMediaPlayer: MediaPlayer
    private lateinit var successMediaPlayer: MediaPlayer
    private lateinit var failMediaPlayer: MediaPlayer
    private var noOfGamePlayed = 0

    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backgroundMediaPlayer = MediaPlayer.create(this, R.raw.music1)
        // Set the volume to 0.5 (50% of the maximum volume)
        backgroundMediaPlayer.setVolume(0.05f, 0.05f)
        backgroundMediaPlayer.start()
        backgroundMediaPlayer.setOnCompletionListener {
            backgroundMediaPlayer.seekTo(0)
            backgroundMediaPlayer.start()
        }

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        userName = intent.getStringExtra("name").toString().uppercase()

//        val sharedPreferences = getSharedPreferences("NBackGamePrefs", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.remove("taskList")
//        editor.clear()
//        editor.apply()

        initUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        backgroundMediaPlayer.release()
        tapMediaPlayer.release()
    }

    private fun takeInput(){
        touch = 0
        TOUCH_0_TIME = Calendar.getInstance().timeInMillis.toInt()
        val date = Calendar.getInstance().time
        binding.touchView.setOnClickListener{
            //paly touch sound
            tapMediaPlayer = MediaPlayer.create(this, R.raw.tap)
            tapMediaPlayer.setVolume(1f, 1f)
            tapMediaPlayer.start()
            touch +=1
            when(touch) {
                1 -> {
                    TOUCH_1_TIME = Calendar.getInstance().timeInMillis.toInt()
                }
                2 -> {
                    TOUCH_2_TIME = Calendar.getInstance().timeInMillis.toInt()
                }
                3 -> {
                    TOUCH_3_TIME = Calendar.getInstance().timeInMillis.toInt()
                }
                4 -> {
                    TOUCH_4_TIME = Calendar.getInstance().timeInMillis.toInt()
                }
                5 -> {
                    TOUCH_5_TIME = Calendar.getInstance().timeInMillis.toInt()
                }
                6 -> {
                    TOUCH_6_TIME = Calendar.getInstance().timeInMillis.toInt()
                }
            }
        }
    }

    private fun initUI() {
        tts = TextToSpeech(this@MainActivity) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage(destLanguage)
                //change the reading speed of tts
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e(TAG, "The Language not supported!")
                } else {
                    tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                        override fun onStart(utteranceId: String) {
                            handleOnStartUtterance(utteranceId)
                        }

                        override fun onDone(utteranceId: String) {
                            handleOnDoneUtterance(utteranceId)
                        }

                        @Deprecated("Deprecated in Java")
                        override fun onError(utteranceId: String) {
                            handleOnErrorUtterance(utteranceId)
                        }
                    })

                    if (allPermissionsGranted()) {
                        playInstruction(INSTRUCTION_5, INSTRUCTION_5_ID)
                        Toast.makeText(this, "All permission are granted", Toast.LENGTH_SHORT).show()
                    } else {
                        ActivityCompat.requestPermissions(
                            this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
                        )
                    }
                }
            }
        }
    }

    private fun playInstruction(instructionText: String, instructionId: String) {
        tts!!.speak(instructionText, TextToSpeech.QUEUE_FLUSH, null, instructionId)
    }

    private fun generateRandomString(length: Int): String {
        val allowedChars = ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    private fun resetGame(){
        task_1_attempt = 1
        task_2_attempt = 1
        task_3_attempt = 1
        task_4_attempt = 1
        randomNumberList = mutableListOf<Int>()
    }

    private fun convertNumberListToBengali(numberList: MutableList<Int>): MutableList<String>{
        val bengaliNumberList = mutableListOf<String>()
        for (i in numberList){
            when(i){
                0 -> bengaliNumberList.add("শূন্য")
                1 -> bengaliNumberList.add("এক")
                2 -> bengaliNumberList.add("দুই")
                3 -> bengaliNumberList.add("তিন")
                4 -> bengaliNumberList.add("চার")
                5 -> bengaliNumberList.add("পাঁচ")
                6 -> bengaliNumberList.add("ছয়")
                7 -> bengaliNumberList.add("সাত")
                8 -> bengaliNumberList.add("আট")
                9 -> bengaliNumberList.add("নয়")
            }
        }
        return bengaliNumberList
    }

    private fun playInstruction1() {
        flow_Id = generateRandomString(32)
        playInstruction(INSTRUCTION_1, INSTRUCTION_1_ID)
    }

    private fun playRandomNumbersList(randomNumberList: MutableList<Int>){
        if(changeLangMode){
            tts?.setSpeechRate(0.7f)
            val randomList  = convertNumberListToBengali(randomNumberList)
            var randomNumberListText = ""
            for (i in randomList){
                randomNumberListText += i + "    "
            }
            playInstruction(randomNumberListText, INSTRUCTION_RANDOM_NUMBER_LIST_ID)
        }
        else{
            tts?.setSpeechRate(0.2f)
            var randomNumberListText = ""
            for (i in randomNumberList){
                randomNumberListText += i.toString() + "  "
            }
            playInstruction(randomNumberListText, INSTRUCTION_RANDOM_NUMBER_LIST_ID)
        }
    }

    private fun playSelectedGameLevel(level: Int){
        when(level){
            1 -> {
                gameMode = 1
                playInstruction(INSTRUCTION_LEVEL_1, INSTRUCTION_LEVEL_ID)
            }
            2 -> {
                gameMode = 2
                playInstruction(INSTRUCTION_LEVEL_2, INSTRUCTION_LEVEL_ID)
            }
            else -> {
                gameMode = 3
                playInstruction(INSTRUCTION_LEVEL_3, INSTRUCTION_LEVEL_ID)
            }
        }
    }

    private fun generateRandomNumbers(mode: Int): MutableList<Int>{
        val randomList = mutableListOf<Int>()
        val flNum = (1..9).random()
        randomList.add(flNum)
        when(mode){
            1 -> {
                for (i in 1..(1..2).random()) {
                    val random = (1..9).random()
                    randomList.add(random)
                }
            }
            2 -> {
                for (i in 1..(3..4).random()) {
                    val random = (1..9).random()
                    randomList.add(random)
                }
            }
            3 -> {
                for (i in 1..(5..7).random()) {
                    val random = (1..9).random()
                    randomList.add(random)
                }
            }
        }
        randomList.add(flNum)
        return randomList
    }

    private fun selectTouchTime(touch:Int): Int{
        when(touch){
            1 -> {
                return TOUCH_1_TIME
            }
            2 -> {
                return TOUCH_2_TIME
            }
            3 -> {
                return TOUCH_3_TIME
            }
            4 -> {
                return TOUCH_4_TIME
            }
            5 -> {
                return TOUCH_5_TIME
            }
            6 -> {
                return TOUCH_6_TIME
            }
        }
        return 0
    }

    private fun addTaskToSharedPref(task: Task) {
        val sharedPreferences = getSharedPreferences("NBackGamePrefs", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("taskList", null)
        val taskList: MutableList<Task> = if (json != null) {
            gson.fromJson(json, Array<Task>::class.java).toMutableList()
        } else {
            ArrayList()
        }

        taskList.add(task)

        val json2 = gson.toJson(taskList)
        val editor = sharedPreferences.edit()
        editor.putString("taskList", json2)
        editor.apply()
    }

    private fun createExelSheet() {
        val sharedPreferences = getSharedPreferences("NBackGamePrefs", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("taskList", "")

        val workbook = HSSFWorkbook()
        val downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadsDirectory, "NBackGame.xlsx")

        if (!json.isNullOrEmpty()) {
            val taskList = gson.fromJson(json, Array<Task>::class.java).toList()
            try {
                val sheet = workbook.createSheet("NBackGame")

                val headerRow = sheet.createRow(0)
                headerRow.createCell(0).setCellValue("Date")
                headerRow.createCell(1).setCellValue("Time")
                headerRow.createCell(2).setCellValue("User Name")
                headerRow.createCell(3).setCellValue("Flow Id")
                headerRow.createCell(4).setCellValue("Task No")
                headerRow.createCell(5).setCellValue("Attempt")
                headerRow.createCell(6).setCellValue("Start Time")
                headerRow.createCell(7).setCellValue("End Time")
                headerRow.createCell(8).setCellValue("Delta Time")
                headerRow.createCell(9).setCellValue("Remarks")

                for (i in taskList.indices) {
                    val row = sheet.createRow(i + 1)
                    row.createCell(0).setCellValue(taskList[i].date)
                    row.createCell(1).setCellValue(taskList[i].time)
                    row.createCell(2).setCellValue(taskList[i].userName)
                    row.createCell(3).setCellValue(taskList[i].flowID)
                    row.createCell(4).setCellValue(taskList[i].taskNo.toString())
                    row.createCell(5).setCellValue(taskList[i].attempts.toString())
                    row.createCell(6).setCellValue(taskList[i].startTime.toString())
                    row.createCell(7).setCellValue(taskList[i].endTime.toString())
                    row.createCell(8).setCellValue(taskList[i].deltaTime.toString())
                    row.createCell(9).setCellValue(taskList[i].remarks)
                }

                val fileOutputStream = FileOutputStream(file)
                workbook.write(fileOutputStream)
                fileOutputStream.close()

                runOnUiThread {
                    Toast.makeText(applicationContext, "File Saved", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(applicationContext, "Error saving file", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            //create empty file
            try {
                val sheet = workbook.createSheet("NBackGame")

                val headerRow = sheet.createRow(0)
                headerRow.createCell(0).setCellValue("Date")
                headerRow.createCell(1).setCellValue("Time")
                headerRow.createCell(2).setCellValue("User Name")
                headerRow.createCell(3).setCellValue("Flow Id")
                headerRow.createCell(4).setCellValue("Task No")
                headerRow.createCell(5).setCellValue("Attempt")
                headerRow.createCell(6).setCellValue("Start Time")
                headerRow.createCell(7).setCellValue("End Time")
                headerRow.createCell(8).setCellValue("Delta Time")
                headerRow.createCell(9).setCellValue("Remarks")

                val fileOutputStream = FileOutputStream(file)
                workbook.write(fileOutputStream)
                fileOutputStream.close()

                runOnUiThread {
                    Toast.makeText(applicationContext, "File Saved", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(applicationContext, "Error saving file", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun startLangSelectionTask(){
        takeInput()
        Timer().schedule(delay = delay * 1000){
            if(touch == 0){
                playInstruction(INSTRUCTION_5, INSTRUCTION_5_ID)
            }
            else{
                if(touch == 1){
                    changeLangMode = true
                    switchToBengali()
                }
                else if(touch==3){
                    createExelSheet()
                }
                playInstruction1()
            }
        }
    }

    private fun startTask1(){
        takeInput()
        Timer().schedule(delay = delay * 1000){
            if(touch == 0) {
                task_1_attempt++
                playInstruction1()
            }
            else{
                val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                val time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                val task1 = Task(date,time, userName ,flow_Id, 1, task_1_attempt, -TOUCH_0_TIME,
                    -selectTouchTime(touch), -TOUCH_0_TIME + selectTouchTime(touch), "null")

                addTaskToSharedPref(task1)
                playSelectedGameLevel(touch)
            }
        }
    }

    private fun startTask2(){
        if(noOfGamePlayed == 0){
            takeInput()
            Timer().schedule(delay = delay * 1000){
                if(touch == 0){
                    playSelectedGameLevel(gameMode)
                    task_2_attempt++
                }
                else{
                    val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                    val time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                    val task2 = Task(date, time, userName ,flow_Id, 2, task_2_attempt, -TOUCH_0_TIME,
                        -selectTouchTime(touch), -TOUCH_0_TIME + selectTouchTime(touch), "null")

                    addTaskToSharedPref(task2)

                    if(touch == 1){
                        randomNumberList = generateRandomNumbers(gameMode)
                        correctOutput = randomNumberList.size - 2
                        playRandomNumbersList(randomNumberList)
                    }
                    else{
                        playInstruction(INSTRUCTION_4, INSTRUCTION_4_ID)
                        finish()
                    }
                }
            }
        }
        else{
            val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            val time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            val task2 = Task(date, time, userName ,flow_Id, 2, task_2_attempt, -TOUCH_0_TIME,
                -selectTouchTime(touch), -TOUCH_0_TIME + selectTouchTime(touch), "null")

            addTaskToSharedPref(task2)

            randomNumberList = generateRandomNumbers(gameMode)
            correctOutput = randomNumberList.size - 2
            playRandomNumbersList(randomNumberList)
        }
    }

    private fun startTask3(){
        takeInput()
        Timer().schedule(delay = delay * 1000 * (gameMode + 1)){
            if(touch == 0){
                playRandomNumbersList(randomNumberList)
                task_3_attempt++
            }
            else{
                val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                val time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                val task3 = Task(date, time, userName ,flow_Id, 3, task_3_attempt, -TOUCH_0_TIME,
                    -selectTouchTime(touch), -TOUCH_0_TIME + selectTouchTime(touch), if (touch == correctOutput) "correct" else "incorrect")

                addTaskToSharedPref(task3)
                noOfGamePlayed++

                INSTRUCTION_1 = __INSTRUCTION_1
                INSTRUCTION_LEVEL_1 = __INSTRUCTION_LEVEL_1
                INSTRUCTION_LEVEL_2 = __INSTRUCTION_LEVEL_2
                INSTRUCTION_LEVEL_3 = __INSTRUCTION_LEVEL_3

                if (touch == correctOutput){
                    successMediaPlayer = MediaPlayer.create(this@MainActivity, R.raw.win)
                    successMediaPlayer.start()
                    Timer().schedule(delay = delay * 500){
                        playInstruction(INSTRUCTION_2, INSTRUCTION_SF_ID)
                    }
                }
                else{
                    failMediaPlayer = MediaPlayer.create(this@MainActivity, R.raw.error)
                    failMediaPlayer.start()
                    Timer().schedule(delay = delay * 500){
                        playInstruction(INSTRUCTION_3, INSTRUCTION_SF_ID)
                    }
                }
            }
        }
    }

    private fun startTask4(){
        takeInput()
        Timer().schedule(delay = delay * 1000){
            if(touch==0){
                playInstruction(INSTRUCTION_6, INSTRUCTION_6_ID)
                task_4_attempt++
            }
            else{
                val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                val time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                val task4 = Task(date,time, userName ,flow_Id, 4, task_4_attempt, -TOUCH_0_TIME, -selectTouchTime(touch), -TOUCH_0_TIME + selectTouchTime(touch), "null")
                addTaskToSharedPref(task4)
                if (touch == 1){
                    resetGame()
                    playInstruction1()
                }
                else{
                    playInstruction(INSTRUCTION_4, INSTRUCTION_4_ID)
                }
            }
        }
    }

    private fun handleOnStartUtterance(utteranceId: String) {
        Log.d(TAG, "TTS Started $utteranceId")
        when (utteranceId) {
            INSTRUCTION_1_ID -> {

            }
            INSTRUCTION_LEVEL_ID -> {

            }
            INSTRUCTION_RANDOM_NUMBER_LIST_ID -> {

            }
            INSTRUCTION_SF_ID -> {

            }
            INSTRUCTION_5_ID -> {

            }
            _INSTRUCTION_5_ID -> {

            }
            INSTRUCTION_6_ID -> {

            }
            else -> {
                // Do nothing if Instruction ID don't match
            }
        }
    }

    private fun handleOnDoneUtterance(utteranceId: String) {
        Log.d(TAG, "TTS Done $utteranceId")
        when (utteranceId) {
            INSTRUCTION_1_ID -> {
                startTask1()
            }
            INSTRUCTION_LEVEL_ID -> {
                startTask2()
            }
            INSTRUCTION_RANDOM_NUMBER_LIST_ID -> {
                tts?.setSpeechRate(1f)
                playInstruction(INSTRUCTION_7, INSTRUCTION_7_ID)
            }
            INSTRUCTION_SF_ID -> {
                playInstruction(INSTRUCTION_6, INSTRUCTION_6_ID)
            }
            INSTRUCTION_4_ID -> {
                finish()
            }
            INSTRUCTION_5_ID -> {
                changeLangMode = true
                playInstruction(_INSTRUCTION_5, _INSTRUCTION_5_ID)
            }
            _INSTRUCTION_5_ID -> {
                changeLangMode = false
                startLangSelectionTask()
            }
            INSTRUCTION_6_ID -> {
                startTask4()
            }
            INSTRUCTION_7_ID -> {
                startTask3()
            }
            else -> {
                // Do nothing if Instruction ID don't match
            }
        }
    }

    private fun handleOnErrorUtterance(utteranceId: String) {
        Log.d(TAG, "TTS Error $utteranceId")
        when (utteranceId) {
            INSTRUCTION_1_ID -> {

            }
            INSTRUCTION_LEVEL_ID -> {

            }
            INSTRUCTION_RANDOM_NUMBER_LIST_ID -> {

            }
            INSTRUCTION_SF_ID -> {

            }
            INSTRUCTION_5_ID -> {

            }
            _INSTRUCTION_5_ID -> {

            }
            INSTRUCTION_6_ID -> {

            }
            else -> {
                // Do nothing if Instruction ID don't match
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Permissions granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
        }
    }

    private fun switchToBengali(){
        tts?.language = Locale.forLanguageTag("bn_IN")
        destLanguage = Locale.forLanguageTag("bn_In")
        INSTRUCTION_1 = _INSTRUCTION_1
        INSTRUCTION_LEVEL_1 = _INSTRUCTION_LEVEL_1
        INSTRUCTION_LEVEL_2 = _INSTRUCTION_LEVEL_2
        INSTRUCTION_LEVEL_3 = _INSTRUCTION_LEVEL_3
        INSTRUCTION_2 = _INSTRUCTION_2
        INSTRUCTION_3 = _INSTRUCTION_3
        INSTRUCTION_4 = _INSTRUCTION_4
        INSTRUCTION_5 = _INSTRUCTION_5
        INSTRUCTION_6 = _INSTRUCTION_6
        INSTRUCTION_7 = _INSTRUCTION_7
        __INSTRUCTION_1 = ___INSTRUCTION_1
        __INSTRUCTION_LEVEL_1 = ___INSTRUCTION_LEVEL_1
        __INSTRUCTION_LEVEL_2 = ___INSTRUCTION_LEVEL_2
        __INSTRUCTION_LEVEL_3 = ___INSTRUCTION_LEVEL_3
    }

    companion object {
        private const val TAG = "BlindAssistant"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ).toTypedArray()
    }
}