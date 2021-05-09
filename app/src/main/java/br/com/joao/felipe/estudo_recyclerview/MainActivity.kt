package br.com.joao.felipe.estudo_recyclerview

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), CustomAdapter.OnItemClickListener {
    private val nomes = mutableListOf<Nota>()
    private var indice :Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerLista = findViewById<View>(R.id.recycler) as RecyclerView
        val botao : FloatingActionButton = findViewById(R.id.add_button)
        val adapter = CustomAdapter(nomes, this)

        botao.setOnClickListener {
            nomes.add(indice, Nota("Nota $indice"))
            adapter.notifyItemInserted(indice)
            indice++
        }

        recyclerLista.adapter = adapter

        recyclerLista.layoutManager = LinearLayoutManager(this)

        helper(adapter, recyclerLista)
    }

    private fun helper(
        adapter: CustomAdapter,
        recyclerLista: RecyclerView
    ) {
        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ) = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val note: Nota = nomes[position]

                    nomes.remove(note)
                    indice--
                    adapter.notifyItemRemoved(position)

                    Snackbar.make(viewHolder.itemView, "Nome deletado", Snackbar.LENGTH_LONG)
                        .apply {
                            setAction("UNDO") {
                                nomes.add(position, note)
                                indice++
                                adapter.notifyItemInserted(position)
                            }
                            setActionTextColor(Color.YELLOW)
                        }.show()
                }
            }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(recyclerLista)
        }
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, DetalheNome::class.java).apply {
            putExtra("nome", nomes[position].nome)
        }
        startActivity(intent)

    }
}