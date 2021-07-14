package com.ifsp.pdm.emanoela.listadetarefas.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ifsp.pdm.emanoela.listadetarefas.AutenticacaoFirebase
import com.ifsp.pdm.emanoela.listadetarefas.controller.TarefaController
import com.ifsp.pdm.emanoela.listadetarefas.databinding.ActivityEditarTarefaBinding
import com.ifsp.pdm.emanoela.listadetarefas.model.Tarefa
import java.text.SimpleDateFormat
import java.util.*

class EditarTarefaActivity : AppCompatActivity() {
    private lateinit var editarTarefaBinding: ActivityEditarTarefaBinding
    private val sdf = SimpleDateFormat("dd/MM/yyyy")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editarTarefaBinding = ActivityEditarTarefaBinding.inflate(layoutInflater)
        setContentView(editarTarefaBinding.root)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val tarefa: Tarefa? = bundle.getParcelable("tarefa")
            if (tarefa != null) {
                with(editarTarefaBinding) {
                    tituloTV.text = tarefa.titulo
                    descricaoET.setText(tarefa.descricao)
                    usuarioCriadorTV.text = tarefa.usuarioCriador
                    dataCriacaoTV.text = tarefa.dataCriacao
                    dataPrevistaET.setText(tarefa.dataPrevista)
                }
            }
        }
    }

    fun onClick(view: View) {
        val tarefa: Tarefa
        with(editarTarefaBinding) {
            tarefa = Tarefa(
                tituloTV.text.toString(),
                descricaoET.text.toString(),
                usuarioCriadorTV.text.toString(),
                dataCriacaoTV.text.toString(),
                dataPrevistaET.text.toString(),
            )
        }
        val retornoIntent = Intent()
        when (view) {
            editarTarefaBinding.salvarBTN -> {
                tarefa.status = StatusTarefas.TAREFA_ABERTA
                retornoIntent.putExtra("tarefa", tarefa)
                setResult(RESULT_OK, retornoIntent)
                finish()
            }
            editarTarefaBinding.concluirBTN -> {
                with(editarTarefaBinding) {
                    tarefa.status = StatusTarefas.TAREFA_CONCLUIDA
                    tarefa.usuarioConcluiu = AutenticacaoFirebase.firebaseAuth.currentUser?.email.toString()
                    tarefa.dataConclusao = sdf.format(Date())
                }
                retornoIntent.putExtra("tarefa", tarefa)
                setResult(RESULT_OK, retornoIntent)
                finish()
            }
            editarTarefaBinding.excluirBTN -> {
                retornoIntent.putExtra("tarefa", tarefa)
                retornoIntent.putExtra("acao", "excluir")
                setResult(RESULT_OK, retornoIntent)
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (AutenticacaoFirebase.firebaseAuth.currentUser == null) {
            finish()
        }
    }
}