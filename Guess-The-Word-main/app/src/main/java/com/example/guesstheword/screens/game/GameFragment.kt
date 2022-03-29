package com.example.guesstheword.screens.game

import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.guesstheword.R
import com.example.guesstheword.databinding.FragmentGameBinding


class GameFragment : Fragment() {


    private lateinit var viewModel: GameViewModel


    private lateinit var binding: FragmentGameBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel

        binding.setLifecycleOwner(this)




        viewModel.currentTime.observe(this.viewLifecycleOwner) { newTime ->
            binding.timerText.text = DateUtils.formatElapsedTime(newTime)

        }


        viewModel.eventGameFinish.observe(this.viewLifecycleOwner) { isFinish ->
            if (isFinish) {
                viewModel.onGameFinishComplete()
                val currScore = viewModel.score.value ?: 0
                val action = GameFragmentDirections.actionGameToScore(
                    currScore
                )

                findNavController(this).navigate(action)

            }
        }

        return binding.root
    }


    /** Methods for updating the UI **/


}