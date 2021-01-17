package com.example.polydraw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


private const val TAG = "RegistarActivity"

class RegistarActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var tfUser : TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registar)

        tfUser = findViewById(R.id.tfUser)

        intent.extras?.apply {
            for(k in keySet()) {
                Log.i(TAG, "Extras: $k -> ${get(k)}")
            }
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)

        auth = Firebase.auth

        CriarFS()

    }

    override fun onStart() {
        super.onStart()
    }

    fun onCriarComEmail(view: View) {
        val username = findViewById<EditText>(R.id.tfUser).text.toString()
        val email = findViewById<EditText>(R.id.tfEmail).text.toString()
        val password = findViewById<EditText>(R.id.tfPass).text.toString()

        createUserWithEmail(email,password)

    }

    private fun createUserWithEmail(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "createUser: success")
                    atualizarFS()
                    finish()
                } else {
                    Log.i(TAG, "createUser: failure")
                }
            }
    }

    fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 1234)
    }

    fun onAutenticarGmail(view: View) {
        signInWithGoogle()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1234) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.i(TAG, "onActivityResult - Google authentication: failure")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    Log.i(TAG, "firebaseAuthWithGoogle: ${auth.currentUser?.displayName}")
                    atualizarFS()
                    finish()
                } else {
                    Log.d(TAG, "signInWithCredential:failure")
                }
            }
    }

    fun onVoltar(view: View) {
        finish()
    }

    fun CriarFS() {
        val db = Firebase.firestore

        val user = hashMapOf(
            "id" to 0,
            "Username" to " "
        )
        db.collection("Username").document("vvPENlC0vqzF4geY26UV").set(user)
    }

    fun atualizarFS(){
        val db = Firebase.firestore

        val username = db.collection("Username").document("vvPENlC0vqzF4geY26UV")

        db.runTransaction{ transition ->
            val doc = transition.get(username)
            val userName = tfUser

            transition.update(username, "Nome", userName)

        }
    }
}