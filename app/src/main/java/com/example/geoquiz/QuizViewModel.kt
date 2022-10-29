package com.example.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel() {
    private var questionBank = listOf(
        Question(R.string.question_text_australia, true, false),
        Question(R.string.question_oceans, true, false),
        Question(R.string.question_mideast, false, false),
        Question(R.string.question_africa, false, false),
        Question(R.string.question_americas, true, false),
        Question(R.string.question_asia, true, false),
    )
    var currentIndex = 0
    var amountRightAnswers = 0
    var amountAllAnswers = 0

    var isCheater = false

    val amountAllQuestions = questionBank.size

    val currentQuestionAnswered: Boolean get() = questionBank[currentIndex].answered
    val currentQuestionAnswer: Boolean get() = questionBank[currentIndex].answer
    val currentQuestionText: Int get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex--
        if(currentIndex < 0)
            currentIndex = questionBank.size - 1
    }

    fun makeQuestionAnswered() {
        questionBank[currentIndex].answered = true
    }

    fun resetQuestions() {
        amountRightAnswers = 0
        amountAllAnswers = 0
        questionBank.forEach { question -> question.answered = false }
    }
}