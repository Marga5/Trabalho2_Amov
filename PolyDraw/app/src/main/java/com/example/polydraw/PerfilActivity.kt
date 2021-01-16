package com.example.polydraw

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

class   PerfilActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var tvLog : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

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

    //Para testar se user está com login
    fun showUser(user : FirebaseUser?) {
        val str = if (user==null) {
            "No authenticated user"
        } else {
            "${user.email}"
        }
        Snackbar.make(tvLog ,str, Snackbar.LENGTH_LONG).show()
        tvLog.text = str
    }



    fun onVoltar(view: View) {
        finish()
    }
}