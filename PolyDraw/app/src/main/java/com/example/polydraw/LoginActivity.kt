package com.example.polydraw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
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

        //Criar botão no textView
        val textView : TextView  = findViewById(R.id.tvSignUp)//findViewById(R.id.tvSignUp)
        val text : String = "Don't have an account? Sign up here"
        val spannableString = SpannableString(text)

        val clickableSpan : ClickableSpan = object : ClickableSpan(){
            override fun onClick(widget: View) {
                onRegisto()
            }
        }
        spannableString.setSpan(clickableSpan, 23,35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.setText(spannableString)
        textView.setMovementMethod(LinkMovementMethod.getInstance())

        //--------------------------

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

    fun signInWithEmail(email:String, password:String){
        auth.signInWithEmailAndPassword(email,  password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail: success")
                        showUser(auth.currentUser)
                        loginSucess()
                    } else {
                        Log.d(TAG, "signInWithEmail: failure")
                        showUser(null)
                    }
                }

    }

    fun onLoginMail(view: View) {
        val email = findViewById<EditText>(R.id.tfEmail).text.toString()
        val pass = findViewById<EditText>(R.id.tfPass).text.toString()

        if(email.length==0 || pass.length == 0) {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
        }
        else {
            signInWithEmail(email, pass)

        }

    }

    fun onRegisto(){
        val intent = Intent (this, RegistarActivity::class.java)
        startActivity(intent)
    }


    fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 1234)
    }

    fun onAutenticarGmail(view: View) {
        signInWithGoogle()

    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithCredential:success")
                        showUser(auth.currentUser)
                        Log.i(TAG, "firebaseAuthWithGoogle: ${auth.currentUser?.displayName}")
                        loginSucess()
                    } else {
                        Log.d(TAG, "signInWithCredential:failure")
                        showUser(auth.currentUser)
                    }
                }

    }

    fun loginSucess(){
        val intent = Intent (this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }


    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int, data: Intent?) {
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
}