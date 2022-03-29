package com.example.guesstheword.screens.game

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService

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


        viewModel.eventBuzz.observe(viewLifecycleOwner) { buzzType ->
            if (buzzType != GameViewModel.BuzzType.NO_BUZZ) {
                buzz(buzzType.pattern)
                viewModel.onBuzzComplete()
            }

        }
        return binding.root
    }

    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService<Vibrator>()
        buzzer?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            }
            else{
                buzzer.vibrate(pattern,-1)
            }
        }

    }


    /** Methods for updating the UI **/


}