package com.example.mytennis.adapter

import Torneio
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytennis.R

class TournamentAdapter(private val torneios: List<Torneio>) : RecyclerView.Adapter<TournamentAdapter.TorneioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TorneioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_tournament, parent, false)
        return TorneioViewHolder(view)
    }

    override fun onBindViewHolder(holder: TorneioViewHolder, position: Int) {
        holder.bind(torneios[position])
    }

    override fun getItemCount() = torneios.size

    class TorneioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeTextView: TextView = itemView.findViewById(R.id.torneio_nome)
        private val localTextView: TextView = itemView.findViewById(R.id.torneio_local)
        private val dataInicioTextView: TextView = itemView.findViewById(R.id.torneio_data_inicio)
        private val dataFimTextView: TextView = itemView.findViewById(R.id.torneio_data_fim)
        private val pisoTextView: TextView = itemView.findViewById(R.id.torneio_piso)
        private val tipoTextView: TextView = itemView.findViewById(R.id.torneio_tipo)

        fun bind(torneio: Torneio) {
            nomeTextView.text = torneio.nome
            localTextView.text = torneio.local
            dataInicioTextView.text = "Início: ${torneio.data_inicio}"
            dataFimTextView.text = "Fim: ${torneio.data_fim}"
            pisoTextView.text = "Piso: ${torneio.piso_id}"
            tipoTextView.text = "Tipo: ${torneio.tipo_torneio_id}"
        }
    }
}
