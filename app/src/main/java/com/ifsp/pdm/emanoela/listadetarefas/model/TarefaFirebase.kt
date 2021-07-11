package com.ifsp.pdm.emanoela.listadetarefas.model

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.ifsp.pdm.emanoela.listadetarefas.model.TarefaFirebase.Constantes.LISTA_TAREFAS_DATABASE

class TarefaFirebase : TarefaDAO {
    object Constantes {
        const val LISTA_TAREFAS_DATABASE = "listaTarefas"
    }

    private val tarefasListRtDb = Firebase.database.getReference(LISTA_TAREFAS_DATABASE)

    private var tarefasList: MutableList<Tarefa>

    init {
        tarefasListRtDb.keepSynced(true)
        tarefasList = mutableListOf()
        tarefasListRtDb.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tarefasList.clear()
                for (c in snapshot.children) {
                    tarefasList.add(c.getValue<Tarefa>() ?: Tarefa())
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        tarefasListRtDb.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val novaTarefa: Tarefa = (snapshot.getValue<Tarefa>() ?: Tarefa())

                if (tarefasList.indexOfFirst { it.titulo == novaTarefa.titulo } == -1) {
                    tarefasList.add(novaTarefa)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val tarefaEditada: Tarefa = (snapshot.getValue<Tarefa>() ?: Tarefa())
                val indice = tarefasList.indexOfFirst { it.titulo == tarefaEditada.titulo }
                tarefasList[indice] = tarefaEditada
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val tarefaRemovida: Tarefa = (snapshot.getValue<Tarefa>() ?: Tarefa())
                tarefasList.remove(tarefaRemovida)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun createTarefa(tarefa: Tarefa) {
        criaOuAtualizaTarefa(tarefa)
    }

    override fun readTarefa(titulo: String): Tarefa {
        return tarefasList[tarefasList.indexOfFirst { it.titulo == titulo }]
    }

    override fun existsTarefa(titulo: String): Int {
        return tarefasList.indexOfFirst { it.titulo == titulo }
    }

    override fun readTarefas(): MutableList<Tarefa> {
        return tarefasList
    }

    override fun updateTarefa(tarefa: Tarefa) {
        criaOuAtualizaTarefa(tarefa)
    }

    override fun deleteTarefa(titulo: String) {
        tarefasListRtDb.child(titulo).removeValue()
    }

    private fun criaOuAtualizaTarefa(tarefa: Tarefa) {
        tarefasListRtDb.child(tarefa.titulo).setValue(tarefa)
    }
}