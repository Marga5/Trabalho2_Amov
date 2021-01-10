package com.example.polydraw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    lateinit var tvLog : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        intent.extras?.apply {
            for(k in keySet()) {
                Log.i(TAG, "Extras: $k -> ${get(k)}")
            }
        }

        tvLog = findViewById(R.id.tvLog)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)

        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
    }

    fun showUser(user : FirebaseUser?) {
        val str = if (user==null) {
            "No authenticated user"
        } else {
            "User: ${user.email}"
        }
        Snackbar.make(tvLog ,str, Snackbar.LENGTH_LONG).show()
        tvLog.text = str
    }

    fun signInWithEmail(email:String,password:String) {
        auth.signInWithEmailAndPassword(email,  password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail: success")
                        showUser(auth.currentUser)
                    } else {
                        Log.d(TAG, "signInWithEmail: failure")
                        showUser(null)
                    }
                }
    }

    fun onLoginMail(view: View) {
        val email = findViewById<EditText>(R.id.edEmail).text.toString()
        val pass = findViewById<EditText>(R.id.edPassword).text.toString()


        signInWithEmail(email, pass)
    }


    fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 1234)
    }

    fun onAutenticarGmail(view: View) {
        signInWithGoogle()
        showUser(auth.currentUser)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithCredential:success")
                        showUser(auth.currentUser)
                        Log.i(TAG, "firebaseAuthWithGoogle: ${auth.currentUser?.displayName}")
                    } else {
                        Log.d(TAG, "signInWithCredential:failure")
                        showUser(auth.currentUser)
                    }
                }
    }
}