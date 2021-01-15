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

    lateinit var tvLog : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)

        //Para testar se user está com login
        tvLog = findViewById(R.id.tvLog)


        showUser(auth.currentUser)

        //--------------------
    }

    fun onStart(view: View) {}
    fun onProfile(view: View) {}


    fun onAbout(view: View) {
        val intent = Intent (this, SobreActivity::class.java)
        startActivity(intent)
    }


    fun onLogout(view: View) {
        if(auth.currentUser == null)
            googleSignInClient.signOut()
                .addOnCompleteListener(this) {
                    //showUser(null)
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
        //showUser(auth.currentUser)
    }

    fun onLogin(){
        val intent = Intent (this, LoginActivity::class.java)
        startActivity(intent)
    }



    //Para testar se user está com login
    fun showUser(user : FirebaseUser?) {
        val str = if (user==null) {
            "No authenticated user"
        } else {
            "User: ${user.email}"
        }
        Snackbar.make(tvLog ,str, Snackbar.LENGTH_LONG).show()
        tvLog.text = str
    }
}