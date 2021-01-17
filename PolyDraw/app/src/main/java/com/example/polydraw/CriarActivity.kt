package com.example.polydraw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class CriarActivity : AppCompatActivity() {
    lateinit var etNomeEquipa : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar)

        etNomeEquipa = findViewById(R.id.etEquipa)

        CriarFS()
    }

    fun onVoltar(view: View) {
        finish()
    }

    fun onCriar(view: View)
    {
        atualizarFS()

        val intent = Intent (this, EsperaActivity::class.java)
        startActivity(intent)
    }

    fun CriarFS() {
        val db = FirebaseFirestore.getInstance()

        val equipa = hashMapOf(
            "IP" to 0,
            "Membros" to 0,
            "Nome" to "xpto"
        )
        db.collection("Equipa").document("RvxuZ2VFhlpyjlTEESO6").set(equipa)
    }

    fun atualizarFS(){
        val db = FirebaseFirestore.getInstance()

        val equipa = db.collection("Equipa").document("RvxuZ2VFhlpyjlTEESO6")

        db.runTransaction{ transition ->
            val doc = transition.get(equipa)
            val nomeEquipa = etNomeEquipa

            transition.update(equipa, "Nome", nomeEquipa)

        }
    }


}