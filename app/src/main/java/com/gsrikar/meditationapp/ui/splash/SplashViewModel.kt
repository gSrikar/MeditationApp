package com.gsrikar.meditationapp.ui.splash

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gsrikar.meditationapp.constants.SPLASH_DELAY_IN_MILLIS


class SplashViewModel : ViewModel() {

    /**
     * Live data is updated once the handler is finished
     */
    private val delayMutableLiveData = MutableLiveData<Boolean>()

    /**
     * An instance of handler
     */
    private val handler = Handler()

    /**
     * An instance of runnable
     */
    private val runnable = Runnable {
        delayMutableLiveData.value = true
    }

    /**
     * Create a delay with a handler
     */
    fun createDelay() {
        handler.postDelayed(runnable, SPLASH_DELAY_IN_MILLIS)
    }

    /**
     * Observer the live data to know once the delay is finished
     */
    val delayLiveData = delayMutableLiveData as LiveData<Boolean>
}