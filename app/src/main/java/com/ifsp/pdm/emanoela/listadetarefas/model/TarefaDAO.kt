package com.ifsp.pdm.emanoela.listadetarefas.model

interface TarefaDAO {
    fun createTarefa(tarefa: Tarefa)
    fun readTarefa(titulo: String): Tarefa
    fun readTarefas(): MutableList<Tarefa>
    fun updateTarefa(tarefa: Tarefa)
    fun deleteTarefa(titulo: String)
    fun existsTarefa(titulo: String): Int
}