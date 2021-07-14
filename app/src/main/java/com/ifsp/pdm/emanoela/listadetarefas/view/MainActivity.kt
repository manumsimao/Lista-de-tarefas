package com.ifsp.pdm.emanoela.listadetarefas.view

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ifsp.pdm.emanoela.listadetarefas.AutenticacaoFirebase
import com.ifsp.pdm.emanoela.listadetarefas.R
import com.ifsp.pdm.emanoela.listadetarefas.adapter.TarefasAdapter
import com.ifsp.pdm.emanoela.listadetarefas.controller.TarefaController
import com.ifsp.pdm.emanoela.listadetarefas.databinding.ActivityMainBinding
import com.ifsp.pdm.emanoela.listadetarefas.model.Tarefa
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    lateinit var tarefasAdapter: TarefasAdapter
    private lateinit var tarefasLayoutManager: LinearLayoutManager
    private lateinit var novaTarefaLauncher: ActivityResultLauncher<Intent>
    private lateinit var editarTarefaLauncher: ActivityResultLauncher<Intent>
    private lateinit var tarefaController: TarefaController
    lateinit var tarefasList: MutableList<Tarefa>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        tarefasList = mutableListOf()

        tarefasAdapter = TarefasAdapter(tarefasList, this)
        activityMainBinding.tarefasRV.adapter = tarefasAdapter

        tarefasLayoutManager = LinearLayoutManager(this)
        activityMainBinding.tarefasRV.layoutManager = tarefasLayoutManager

        tarefaController = TarefaController(this)

        novaTarefaLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                if (activityResult.resultCode == RESULT_OK) {
                    val tarefa: Tarefa? =
                        activityResult.data?.getParcelableExtra("tarefa")
                    if (tarefa != null) {
                        tarefasList.add(tarefa)
                        tarefasAdapter.notifyDataSetChanged()
                        tarefaController.insereTarefa(tarefa)
                    }
                }
            }

        editarTarefaLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                if (activityResult.resultCode == RESULT_OK) {
                    val tarefa: Tarefa? =
                        activityResult.data?.getParcelableExtra("tarefa")
                    if (tarefa != null) {
                        val index =
                            tarefasList.indexOf(tarefasList.find { it.titulo == tarefa.titulo })
                        tarefasList[index] = tarefa
                        tarefaController.atualizaTarefa(tarefa)
                    }
                    tarefasAdapter.notifyDataSetChanged()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if (AutenticacaoFirebase.firebaseAuth.currentUser == null) {
            finish()
        }
    }

    fun onTarefaClick(posicao: Int) {
        val tarefa: Tarefa = tarefasList[posicao]
        if (tarefa.status == StatusTarefas.TAREFA_ABERTA) {
            val editarTarefaIntent = Intent(this, EditarTarefaActivity::class.java)
            editarTarefaIntent.putExtra("tarefa", tarefa)
            editarTarefaLauncher.launch(editarTarefaIntent)
        } else if (tarefa.status == StatusTarefas.TAREFA_CONCLUIDA) {
            val visualizarTarefaConcluidaIntent = Intent(this, TarefaConcluidaActivity::class.java)
            visualizarTarefaConcluidaIntent.putExtra("tarefa", tarefa)
            startActivity(visualizarTarefaConcluidaIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.novaTarefaMi -> {
                val novaTarefaIntent = Intent(this, AdicionarTarefaActivity::class.java)
                novaTarefaLauncher.launch(novaTarefaIntent)
            }
            R.id.sairMi -> {
                AutenticacaoFirebase.firebaseAuth.signOut()
                AutenticacaoFirebase.googleSignInClient?.signOut()
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}