package com.hansck.shadowingu.util

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Hans CK on 22-Jun-18.
 */
class FirebaseDB() {

	private val db = FirebaseDatabase.getInstance()

	companion object {
		val instance = FirebaseDB()
	}

	init {
		db.setPersistenceEnabled(true)
	}

	fun getDbReference(ref: String): DatabaseReference {
		return db.getReference(ref)
	}

	fun getKey(ref: String): String? {
		return getDbReference(ref).push().key
	}
}