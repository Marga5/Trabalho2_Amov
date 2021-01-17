package com.example.polydraw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MenuActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)


    }

    fun onStart(view: View) {
        val intent = Intent (this, EntrarActivity::class.java)
        startActivity(intent)
    }

    fun onProfile(view: View) {
        val intent = Intent (this, PerfilActivity::class.java)
        startActivity(intent)
    }

    fun onAbout(view: View) {
        val intent = Intent (this, SobreActivity::class.java)
        startActivity(intent)
    }

    fun onLogout(view: View) {
        if(auth.currentUser == null)
            googleSignInClient.signOut()
                .addOnCompleteListener(this) {
                }
        else
            signOut()

        finish()
        onLogin();
    }

    fun signOut() {
        if (auth.currentUser != null) {
            auth.signOut()
        }
    }

    fun onLogin(){
        val intent = Intent (this, LoginActivity::class.java)
        startActivity(intent)
    }

}