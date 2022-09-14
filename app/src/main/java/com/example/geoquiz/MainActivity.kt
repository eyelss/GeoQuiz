package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private var questionBank = listOf(
        Question(R.string.question_text_australia, true, false),
        Question(R.string.question_oceans, true, false),
        Question(R.string.question_mideast, false, false),
        Question(R.string.question_africa, false, false),
        Question(R.string.question_americas, true, false),
        Question(R.string.question_asia, true, false),
    )
    private var currentIndex = 0
    private var amountRightAnswers = 0
    private var amountAllAnswers = 0

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnwser: Boolean) {
        if(questionBank[currentIndex].anwsered) {
            toastShow(R.string.question_answered)
            return
        }

        questionBank[currentIndex].anwsered = true
        val correctAnwser = questionBank[currentIndex].answer
        amountAllAnswers++
        val messageResId = if (userAnwser == correctAnwser) {
            R.string.correct_toast
            amountRightAnswers++
        } else {
            R.string.incorrect_toast
        }
        toastShow(messageResId)

        if(amountAllAnswers == questionBank.size) {
            toastShow("Congratulations! Your score: ${amountRightAnswers}/${amountAllAnswers}.")
            amountRightAnswers = 0
            amountAllAnswers = 0
            questionBank.forEach { question -> question.anwsered = false }
        }
    }

    private fun toastShow(textId: CharSequence) {
        val toast: Toast = Toast.makeText(
            this,
            textId,
            Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
        toast.show()
    }

    private fun toastShow(textId: Int) {
        val toast: Toast = Toast.makeText(
            this,
            textId,
            Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
        toast.show()
    }

    private fun nextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
        updateQuestion()
    }

    private fun prevQuestion() {
        currentIndex--
        if(currentIndex < 0)
            currentIndex = questionBank.size - 1
        updateQuestion()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        questionTextView = findViewById(R.id.question_text_view)
        questionTextView.setOnClickListener {
            nextQuestion()
        }

        trueButton = findViewById(R.id.true_button)
        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton = findViewById(R.id.false_button)
        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton = findViewById(R.id.next_button)
        nextButton.setOnClickListener {
            nextQuestion()
        }

        prevButton = findViewById(R.id.prev_button)
        prevButton.setOnClickListener {
            prevQuestion()
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}