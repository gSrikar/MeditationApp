package com.gsrikar.meditationapp.ui.meditation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.material.snackbar.Snackbar
import com.gsrikar.meditationapp.BuildConfig
import com.gsrikar.meditationapp.R
import com.gsrikar.meditationapp.model.SessionModel
import com.gsrikar.meditationapp.network.Resource
import com.gsrikar.meditationapp.network.Status
import com.gsrikar.meditationapp.type.MeditationType
import com.gsrikar.meditationapp.type.MeditationType.CALM_DOWN
import com.gsrikar.meditationapp.type.MeditationType.DE_STRESS
import com.gsrikar.meditationapp.type.MeditationType.MEDITATION
import com.gsrikar.meditationapp.type.MeditationType.RELAX
import com.gsrikar.meditationapp.utils.ViewModelFactory
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.meditation_fragment.*

class MeditationFragment : Fragment() {

    companion object {
        // Log cat tag
        private val TAG = MeditationFragment::class.java.simpleName
        // True for debug builds and false otherwise
        private val DBG = BuildConfig.DEBUG
    }

    private lateinit var viewModel: MeditationViewModel

    /**
     * An instance of the player
     */
    private lateinit var player: SimpleExoPlayer

    /**
     * List of arguments passed to the fragment
     */
    private val args: MeditationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.meditation_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFactory = ViewModelFactory(MeditationRepository())
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MeditationViewModel::class.java)

        setListeners()
        fetchDocument(args.type)
    }

    private fun setListeners() {
        viewModel.modelLiveData.observe(
            viewLifecycleOwner,
            Observer {
                updateTitle(it.name)
            }
        )
        viewModel.sessionLiveData.observe(
            viewLifecycleOwner,
            Observer {
                parseSession(it)
            }
        )
    }

    private fun updateTitle(title: String?) {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar?.title = title
        } else {
            activity?.actionBar?.title = title
        }
    }

    private fun fetchDocument(type: MeditationType) {
        when (type) {
            MEDITATION -> fetchMeditation()
            CALM_DOWN -> fetchCalmDown()
            DE_STRESS -> fetchDeStress()
            RELAX -> fetchRelax()
        }
    }

    private fun fetchMeditation() {
        viewModel.fetchMeditation()
    }

    private fun fetchCalmDown() {
        viewModel.fetchCalmDown()
    }

    private fun fetchDeStress() {
        viewModel.fetchDeStress()
    }

    private fun fetchRelax() {
        viewModel.fetchRelax()
    }

    private fun loadImage(url: String?) {
        Picasso.get().load(url).into(meditationImageView, object : Callback {
            override fun onSuccess() {
                loadImageSuccess()
            }

            override fun onError(e: Exception?) {
                showError(e?.message)
            }

        })
    }

    private fun loadImageSuccess() {
        // Hide the progress bar
        hideProgressBar()
        // Play the audio
        playAudio()
    }

    private fun parseSession(resource: Resource<SessionModel>?) {
        when (resource?.status) {
            Status.SUCCESS -> sessionSuccess(resource.data)
            Status.ERROR -> showError(resource.message)
            Status.LOADING -> loading()
        }
    }

    private fun sessionSuccess(data: SessionModel?) {
        // Update the audio link
        viewModel.audioLink = data?.link
        // Load the image
        loadImage(data?.imageLink)
    }

    private fun hideProgressBar() {
        progressBar.visibility = GONE
        playerGroup.visibility = VISIBLE
    }

    private fun showError(error: String?) {
        Snackbar.make(meditation, error.toString(), Snackbar.LENGTH_INDEFINITE).show()
    }

    private fun loading() {
        progressBar.visibility = VISIBLE
    }

    private fun playAudio() {
        // Create the media source
        val mediaSourceFactory =
            ExtractorMediaSource.Factory(
                DefaultDataSourceFactory(context, "exo-player-user-agent-1")
            )
        val mediaSource = mediaSourceFactory.createMediaSource(Uri.parse(viewModel.audioLink))
        // Auto play once the player is ready
        player.playWhenReady = true
        // Prepare the player
        player.prepare(mediaSource)
    }

    private fun initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(context)
        // Attach player to the view
        playerControlView.player = player
    }

    private fun releasePlayer() {
        player.release()
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
        if (DBG) Log.d(TAG, "Current Position: ${viewModel.currentPosition}")
        if (DBG) Log.d(TAG, "Audio Link: ${viewModel.audioLink}")
        // Prepare the audio
        viewModel.currentPosition?.let {
            // Play the audio
            playAudio()
            // Move to the position
            player.seekTo(it)
        }
    }

    override fun onStop() {
        super.onStop()
        // Save the current position of the player
        viewModel.currentPosition = player.currentPosition
        // Release the player
        releasePlayer()
    }

}
