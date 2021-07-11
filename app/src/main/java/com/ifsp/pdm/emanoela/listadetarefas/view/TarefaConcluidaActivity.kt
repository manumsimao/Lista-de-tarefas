package com.ifsp.pdm.emanoela.listadetarefas.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ifsp.pdm.emanoela.listadetarefas.AutenticacaoFirebase
import com.ifsp.pdm.emanoela.listadetarefas.databinding.ActivityTarefaConcluidaBinding
import com.ifsp.pdm.emanoela.listadetarefas.model.Tarefa

class TarefaConcluidaActivity : AppCompatActivity() {
    private lateinit var activityTarefaConcluidaBinding: ActivityTarefaConcluidaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTarefaConcluidaBinding = ActivityTarefaConcluidaBinding.inflate(layoutInflater)
        setContentView(activityTarefaConcluidaBinding.root)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val tarefa: Tarefa? = bundle.getParcelable("tarefa")
            if (tarefa != null) {
                with(activityTarefaConcluidaBinding) {
                    tituloTV.text = tarefa.titulo
                    descricaoTV.text = tarefa.descricao
                    usuarioCriadorTV.text = tarefa.usuarioCriador
                    dataCriacaoTV.text = tarefa.dataCriacao
                    dataPrevistaTV.text = tarefa.dataPrevista
                    usuarioConclusaoTV.text = tarefa.usuarioConcluiu
                    dataConclusaoTV.text = tarefa.dataConclusao
                }
            }
        }
    }

    fun onClick(view: View) {
        if (view == activityTarefaConcluidaBinding.voltarBTN) {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        if (AutenticacaoFirebase.firebaseAuth.currentUser == null) {
            finish()
        }
    }
}