package com.gsrikar.meditationapp.ui.meditation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.gsrikar.meditationapp.model.MeditationModel
import com.gsrikar.meditationapp.model.SessionModel
import com.gsrikar.meditationapp.network.Resource


class MeditationViewModel(private val repository: MeditationRepository) : ViewModel() {

    /**
     * Current position of the track
     */
    var currentPosition: Long? = null

    /**
     * Link to the audio
     */
    var audioLink: String? = null

    private val modelMediatorLiveData = MediatorLiveData<MeditationModel>()
    private val sessionMediatorLiveData = MediatorLiveData<Resource<SessionModel>>()

    init {
        modelMediatorLiveData.addSource(repository.modelMutableLiveData) {
            modelMediatorLiveData.value = it
        }
        sessionMediatorLiveData.addSource(repository.sessionMutableLiveData) {
            sessionMediatorLiveData.value = it
        }
    }


    fun fetchMeditation() {
        repository.fetchMeditation()
    }

    fun fetchCalmDown() {
        repository.fetchCalmDown()
    }

    fun fetchDeStress() {
        repository.fetchDeStress()
    }

    fun fetchRelax() {
        repository.fetchRelax()
    }

    val modelLiveData = modelMediatorLiveData as LiveData<MeditationModel>

    val sessionLiveData = sessionMediatorLiveData as LiveData<Resource<SessionModel>>
}
