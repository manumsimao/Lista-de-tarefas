package com.ifsp.pdm.emanoela.listadetarefas.controller

import com.ifsp.pdm.emanoela.listadetarefas.model.Tarefa
import com.ifsp.pdm.emanoela.listadetarefas.model.TarefaDAO
import com.ifsp.pdm.emanoela.listadetarefas.model.TarefaFirebase

class TarefaController {
    private val tarefaDAO: TarefaDAO = TarefaFirebase()

    fun insereTarefa(tarefa: Tarefa) = tarefaDAO.createTarefa(tarefa)
    fun buscaTarefa(titulo: String) = tarefaDAO.readTarefa(titulo)
    fun existeTarefa(titulo: String) = tarefaDAO.existsTarefa(titulo)
    fun buscaTarefas() = tarefaDAO.readTarefas()
    fun atualizaTarefa(tarefa: Tarefa) = tarefaDAO.updateTarefa(tarefa)
    fun removeTarefa(titulo: String) = tarefaDAO.deleteTarefa(titulo)
}