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
    val status: String = "",
    val usuarioConcluiu: String = "",
    val dataConclusao: String = ""

) : Parcelable