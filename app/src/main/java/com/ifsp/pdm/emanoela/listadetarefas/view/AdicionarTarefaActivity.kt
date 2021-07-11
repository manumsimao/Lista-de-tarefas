package com.ifsp.pdm.emanoela.listadetarefas.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ifsp.pdm.emanoela.listadetarefas.AutenticacaoFirebase
import com.ifsp.pdm.emanoela.listadetarefas.controller.TarefaController
import com.ifsp.pdm.emanoela.listadetarefas.databinding.ActivityAdicionarTarefaBinding
import com.ifsp.pdm.emanoela.listadetarefas.model.Tarefa
import java.text.SimpleDateFormat
import java.util.*

class AdicionarTarefaActivity : AppCompatActivity() {
    private lateinit var activityAdicionarTarefaBinding: ActivityAdicionarTarefaBinding

    @SuppressLint("SimpleDateFormat")
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    private lateinit var tarefaController: TarefaController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAdicionarTarefaBinding = ActivityAdicionarTarefaBinding.inflate(layoutInflater)
        setContentView(activityAdicionarTarefaBinding.root)

        tarefaController = TarefaController()
    }

    fun onClick(view: View) {
        val tarefa: Tarefa
        val titulo = activityAdicionarTarefaBinding.tituloET.text.toString()
        if (view == activityAdicionarTarefaBinding.salvarBTN) {
            if (tarefaController.existeTarefa(titulo) == -1) {
                with(activityAdicionarTarefaBinding) {
                    tarefa = Tarefa(
                        titulo,
                        descricaoET.text.toString(),
                        AutenticacaoFirebase.firebaseAuth.currentUser?.email.toString(),
                        sdf.format(Date()),
                        dataPrevistaET.text.toString(),
                        StatusTarefas.TAREFA_ABERTA
                    )
                }


                val retornoIntent = Intent()
                retornoIntent.putExtra("tarefa", tarefa)
                setResult(RESULT_OK, retornoIntent)
                finish()
            } else {
                Toast.makeText(this, "Já existe uma tarefa com esse título", Toast.LENGTH_LONG)
                    .show()
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