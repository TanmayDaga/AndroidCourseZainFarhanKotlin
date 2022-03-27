package com.example.guesstheword.screens.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.guesstheword.R
import com.example.guesstheword.databinding.FragmentGameBinding


class GameFragment : Fragment() {


    private lateinit var viewModel: GameViewModel


    private lateinit var binding: FragmentGameBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)


        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()

        }
        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
        }

        viewModel.score.observe(this.viewLifecycleOwner, Observer {
            binding.scoreText.text = it.toString()
        })


        updateWordText()
        return binding.root
    }


    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val currScore = viewModel.score.value ?: 0
        val action = GameFragmentDirections.actionGameToScore(currScore)

        findNavController(this).navigate(action)
    }


    /** Methods for updating the UI **/

    private fun updateWordText() {
        binding.wordText.text = viewModel.word

    }


}