package com.example.geoquiz

import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel() {
    private var questionBank = listOf(
        makeQuestion(R.string.question_text_australia, true),
        makeQuestion(R.string.question_oceans, true),
        makeQuestion(R.string.question_mideast, false),
        makeQuestion(R.string.question_africa, false),
        makeQuestion(R.string.question_americas, true),
        makeQuestion(R.string.question_asia, true),
    )

    private fun makeQuestion(idStr: Int, answer: Boolean): Question {
        return Question(idStr, answer, false, false)
    }

    var currentIndex = 0
    var amountRightAnswers = 0
    var amountAllAnswers = 0

    val amountAllQuestions = questionBank.size

    val currentQuestionAnswered: Boolean get() = questionBank[currentIndex].answered
    val currentQuestionCheated: Boolean get() = questionBank[currentIndex].cheated
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

    fun makeQuestionCheated() {
        questionBank[currentIndex].cheated = true
    }

    fun resetQuestions() {
        amountRightAnswers = 0
        amountAllAnswers = 0
        questionBank.forEach { question ->
            question.answered = false
            question.cheated = false
        }
        tipsCount = 0u
    }

    private val maxTips: UInt = 3u
    var tipsCount: UInt = 0u

    fun catTakeTip(): Boolean {
        return tipsCount < maxTips
    }
}