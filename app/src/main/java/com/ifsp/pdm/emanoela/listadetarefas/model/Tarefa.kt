package com.ifsp.pdm.emanoela.listadetarefas.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tarefa(
    val titulo: String = "",
    val descricao: String = "",
    val usuarioCriador: String = "",
    val dataCriacao: String = "",
    val dataPrevista: String = "",
    var status: String = "",
    var usuarioConcluiu: String = "",
    var dataConclusao: String = ""

) : Parcelable