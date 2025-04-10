package com.example.gymcoach.data.network

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class FirebaseStorageService @Inject constructor(private val storage : FirebaseStorage) {
    fun uploadBasicImage(uri: Uri) {
        val reference = storage.reference.child(uri.lastPathSegment.orEmpty())
        reference.putFile(uri)
    }
}