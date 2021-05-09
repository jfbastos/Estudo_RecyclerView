package br.com.joao.felipe.estudo_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DetalheNome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalhe_nota)

        val nomeNota : TextView= findViewById(R.id.textView2)

        val nome = intent.getStringExtra("nome")

        nomeNota.text = nome
    }
}