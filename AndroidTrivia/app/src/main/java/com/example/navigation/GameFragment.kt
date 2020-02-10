package com.example.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.navigation.databinding.FragmentGame1Binding

class GameFragment : Fragment() {

    data class Question(
        val text: String,
        val answers: List<String>
    )

    private val questions: MutableList<Question> = mutableListOf(
        Question(
            text = "What is Android Jetpack?",
            answers = listOf("all of these", "tools", "documentation", "libraries")
        ),
        Question(
            text = "Base class for Layout?",
            answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot")
        ),
        Question(
            text = "Layout for complex Screens?",
            answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout")
        ),
        Question(
            text = "Pushing structured data into a Layout?",
            answers = listOf("Data Binding", "Data Pushing", "Set Text", "OnClick")
        ),
        Question(
            text = "Inflate layout in fragments?",
            answers = listOf(
                "onCreateView",
                "onActivityCreated",
                "onCreateLayout",
                "onInflateLayout"
            )
        ),
        Question(
            text = "Build system for Android?",
            answers = listOf("Gradle", "Graddle", "Grodle", "Groyle")
        ),
        Question(
            text = "Android vector format?",
            answers = listOf(
                "VectorDrawable",
                "AndroidVectorDrawable",
                "DrawableVector",
                "AndroidVector"
            )
        ),
        Question(
            text = "Android Navigation Component?",
            answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitcher")
        ),
        Question(
            text = "Registers app with launcher?",
            answers = listOf("intent-filter", "app-registry", "launcher-registry", "app-launcher")
        ),
        Question(
            text = "Mark a layout for Data Binding?",
            answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>")
        )
    )

    lateinit var answers: MutableList<String>
    lateinit var currentQuestion: Question
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 3)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGame1Binding>(
            inflater,
            R.layout.fragment_game_1,
            container,
            false
        )

        // Shuffle the questions and sets the question index to the first question
        randomizeQuestions()

        // Bind this fragment class to the layout
        binding.game = this

        // Set onClickListener for the submitButton
        binding.buttonSubmitAnswer.setOnClickListener {
            val checkedAnswerId = binding.rgQuestion.checkedRadioButtonId

            // Do nothing if nothing is checked (id == -1)
            if(-1 != checkedAnswerId) {
                var answerIndex = 0
                when (checkedAnswerId) {
                    R.id.rb_second_answer -> answerIndex = 1
                    R.id.rb_third_answer -> answerIndex = 2
                    R.id.rb_fourth_answer -> answerIndex = 3
                }
                // The first answer in the original question is always the correct one
                // so if our answer matches, we have the correct answer.
                if(answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++
                    // Advance to the next question
                    if(questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    } else {
                        // We've won
                        // it.findNavController().navigate(R.id.action_gameFragment_to_gameWonFragment)
                        it.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(numQuestions, questionIndex))
                    }
                } else {
                    // Game Over
                    // it.findNavController().navigate(R.id.action_gameFragment_to_gameWonFragment)
                    it.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())
                }
            }
        }

        return binding.root
    }

    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)
    }

}