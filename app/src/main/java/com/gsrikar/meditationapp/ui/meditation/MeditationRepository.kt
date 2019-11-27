package com.gsrikar.meditationapp.ui.meditation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.gsrikar.meditationapp.BuildConfig
import com.gsrikar.meditationapp.constants.COLLECTION_NAME_PRENT
import com.gsrikar.meditationapp.constants.COLLECTION_NAME_SESSION
import com.gsrikar.meditationapp.constants.DOCUMENT_NAME_CALM_DOWN
import com.gsrikar.meditationapp.constants.DOCUMENT_NAME_DE_STRESS
import com.gsrikar.meditationapp.constants.DOCUMENT_NAME_FOCUS
import com.gsrikar.meditationapp.constants.DOCUMENT_NAME_RELAX
import com.gsrikar.meditationapp.constants.SESSION_DOCUMENT_CALM_DOWN
import com.gsrikar.meditationapp.constants.SESSION_DOCUMENT_DE_STRESS
import com.gsrikar.meditationapp.constants.SESSION_DOCUMENT_FOCUS
import com.gsrikar.meditationapp.constants.SESSION_DOCUMENT_RELAX
import com.gsrikar.meditationapp.model.MeditationModel
import com.gsrikar.meditationapp.model.SessionModel
import com.gsrikar.meditationapp.network.Resource


class MeditationRepository {

    companion object {
        // Log cat tag
        private val TAG = MeditationRepository::class.java.simpleName
        // True for debug builds and false otherwise
        private val DBG = BuildConfig.DEBUG
    }

    /**
     * Firestore collection
     */
    private val collection by lazy {
        FirebaseFirestore.getInstance().collection(COLLECTION_NAME_PRENT)
    }

    val modelMutableLiveData = MutableLiveData<MeditationModel>()
    val sessionMutableLiveData = MutableLiveData<Resource<SessionModel>>()

    fun fetchMeditation() {
        // Start the data loading
        loading()
        // Get the meditation document
        val document = collection.document(DOCUMENT_NAME_FOCUS)
        // Listen to the document
        document.get().addOnSuccessListener {
            readRelaxDocument(it.toObject(MeditationModel::class.java))
        }.addOnFailureListener {
            error(it.message)
        }

        // Fetch the session
        fetchSession(
            document.collection(
                COLLECTION_NAME_SESSION
            ).document(
                SESSION_DOCUMENT_FOCUS
            )
        )
    }

    fun fetchCalmDown() {
        // Start the data loading
        loading()
        // Get the calm down document
        val document = collection.document(DOCUMENT_NAME_CALM_DOWN)
        document.get().addOnSuccessListener {
            readRelaxDocument(it.toObject(MeditationModel::class.java))
        }.addOnFailureListener {
            error(it.message)
        }

        // Fetch the session
        fetchSession(
            document.collection(
                COLLECTION_NAME_SESSION
            ).document(
                SESSION_DOCUMENT_CALM_DOWN
            )
        )
    }

    fun fetchDeStress() {
        // Start the data loading
        loading()
        // Get the de stress document
        val document = collection.document(DOCUMENT_NAME_DE_STRESS)
        document.get().addOnSuccessListener {
            readRelaxDocument(it.toObject(MeditationModel::class.java))
        }.addOnFailureListener {
            error(it.message)
        }

        // Fetch the session
        fetchSession(
            document.collection(
                COLLECTION_NAME_SESSION
            ).document(
                SESSION_DOCUMENT_DE_STRESS
            )
        )
    }

    fun fetchRelax() {
        // Start the data loading
        loading()
        // Get the relax document
        val document = collection.document(DOCUMENT_NAME_RELAX)
        document.get().addOnSuccessListener {
            readRelaxDocument(it.toObject(MeditationModel::class.java))
        }.addOnFailureListener {
            error(it.message)
        }

        // Fetch the session
        fetchSession(
            document.collection(
                COLLECTION_NAME_SESSION
            ).document(
                SESSION_DOCUMENT_RELAX
            )
        )
    }

    private fun fetchSession(document: DocumentReference) {
        // Listen to the collection
        document.get().addOnSuccessListener {
            readSessionDocument(it.toObject(SessionModel::class.java))
        }.addOnFailureListener {
            error(error(it.message))
        }
    }

    private fun readSessionDocument(sessionModel: SessionModel?) {
        if (DBG) Log.d(TAG, "Image Link: ${sessionModel?.imageLink}")
        if (DBG) Log.d(TAG, "Audio Link: ${sessionModel?.link}")
        sessionMutableLiveData.value = Resource.success(sessionModel)
    }

    private fun loading() {
        sessionMutableLiveData.value = Resource.loading()
    }

    private fun error(message: String?) {
        sessionMutableLiveData.value = Resource.error(message)
    }

    private fun readRelaxDocument(meditationModel: MeditationModel?) {
        if (DBG) Log.d(TAG, "Meditation Name: ${meditationModel?.name}")
        modelMutableLiveData.value = meditationModel
    }

}