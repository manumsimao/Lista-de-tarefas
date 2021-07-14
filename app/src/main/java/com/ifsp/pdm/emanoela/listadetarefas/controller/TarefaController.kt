package com.ifsp.pdm.emanoela.listadetarefas.controller

import android.app.Activity
import com.ifsp.pdm.emanoela.listadetarefas.model.Tarefa
import com.ifsp.pdm.emanoela.listadetarefas.model.TarefaDAO
import com.ifsp.pdm.emanoela.listadetarefas.model.TarefaFirebase
import com.ifsp.pdm.emanoela.listadetarefas.view.MainActivity

class TarefaController (mainActivity: MainActivity?) {
    private val tarefaDAO: TarefaDAO = TarefaFirebase(mainActivity)

    fun insereTarefa(tarefa: Tarefa) = tarefaDAO.createTarefa(tarefa)
    fun buscaTarefa(titulo: String) = tarefaDAO.readTarefa(titulo)
    fun existeTarefa(titulo: String) = tarefaDAO.existsTarefa(titulo)
    fun buscaTarefas() = tarefaDAO.readTarefas()
    fun atualizaTarefa(tarefa: Tarefa) = tarefaDAO.updateTarefa(tarefa)
    fun removeTarefa(titulo: String) = tarefaDAO.deleteTarefa(titulo)
}