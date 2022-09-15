package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
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

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        if(quizViewModel.currentQuestionAnswered) {
            toastShow(R.string.question_answered)
            return
        }

        quizViewModel.makeQuestionAnswered()
        quizViewModel.amountAllAnswers++
        val correctAnswer = quizViewModel.currentQuestionAnswer



        val messageResId = if (userAnswer == correctAnswer) {
            quizViewModel.amountRightAnswers++
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        toastShow(messageResId)

        if(quizViewModel.amountAllAnswers == quizViewModel.amountAllQuestions) {
            toastShow(
                "Congratulations! Your score: ${quizViewModel.amountRightAnswers}/${quizViewModel.amountAllAnswers}.")
            quizViewModel.resetQuestions()
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
        quizViewModel.moveToNext()
        updateQuestion()
    }

    private fun prevQuestion() {
        quizViewModel.moveToPrev()
        updateQuestion()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }
}