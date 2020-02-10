package com.example.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.navigation.databinding.FragmentGameOverBinding

class GameOverFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGameOverBinding>(
            inflater,
            R.layout.fragment_game_over,
            container,
            false
        )

        binding.buttonTryAgain.setOnClickListener {
            it.findNavController().navigate(GameOverFragmentDirections.actionGameOverFragmentToGameFragment())
        }

        return binding.root
    }

}
