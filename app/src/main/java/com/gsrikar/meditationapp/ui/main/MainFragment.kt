package com.gsrikar.meditationapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gsrikar.meditationapp.R
import com.gsrikar.meditationapp.type.MeditationType
import com.gsrikar.meditationapp.type.MeditationType.*
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        meditationCardView.setOnClickListener { openMusicFragment(MEDITATION) }
        calmCardView.setOnClickListener { openMusicFragment(CALM_DOWN) }
        deStressCardView.setOnClickListener { openMusicFragment(DE_STRESS) }
        relaxCardView.setOnClickListener { openMusicFragment(RELAX) }
    }

    private fun openMusicFragment(type: MeditationType) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToMeditationFragment(
                type
            )
        )
    }

}
