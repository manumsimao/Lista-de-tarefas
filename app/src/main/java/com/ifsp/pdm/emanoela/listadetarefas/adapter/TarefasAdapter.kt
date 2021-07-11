package com.ifsp.pdm.emanoela.listadetarefas.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ifsp.pdm.emanoela.listadetarefas.R
import com.ifsp.pdm.emanoela.listadetarefas.model.Tarefa
import com.ifsp.pdm.emanoela.listadetarefas.view.MainActivity
import com.ifsp.pdm.emanoela.listadetarefas.view.StatusTarefas

class TarefasAdapter(
    private val tarefasList: MutableList<Tarefa> = mutableListOf(),
    private val onTarefaClickListener: MainActivity
) : RecyclerView.Adapter<TarefasAdapter.TarefaViewHolder>() {
    inner class TarefaViewHolder(viewTarefa: View) : RecyclerView.ViewHolder(viewTarefa) {
        val tituloTV: TextView = viewTarefa.findViewById(R.id.tituloTV)
        val statusTV: TextView = viewTarefa.findViewById(R.id.statusTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val viewTarefa: View =
            LayoutInflater.from(parent.context).inflate(R.layout.view_tarefa, parent, false)
        return TarefaViewHolder(viewTarefa)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa: Tarefa = tarefasList[position]
        holder.tituloTV.text = tarefa.titulo
        holder.statusTV.text = tarefa.status

        if (tarefa.status == StatusTarefas.TAREFA_CONCLUIDA) {
            holder.tituloTV.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.tituloTV.paintFlags = 0
        }

        holder.itemView.setOnClickListener {
            onTarefaClickListener.onTarefaClick(position)
        }
    }

    override fun getItemCount(): Int = tarefasList.size

}