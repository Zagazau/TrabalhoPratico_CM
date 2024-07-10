package com.example.mytennis.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.api.core.HttpClient
import com.example.api.core.RequestResult
import com.example.api.interfaces.ITorneioService
import requests.torneio.CreateTorneioRequest
import responses.torneios.CreateTorneiosResponse
import com.example.mytennis.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTournamentFragment : Fragment() {

    private lateinit var torneioService: ITorneioService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_tournament_admin, container, false)

        torneioService = HttpClient(ITorneioService::class.java).api

        val tournamentField = view.findViewById<EditText>(R.id.tournament_field)
        val surfaceSpinner = view.findViewById<Spinner>(R.id.surface_spinner)
        val tournamentTypeSpinner = view.findViewById<Spinner>(R.id.tournament_type_spinner)
        val localField = view.findViewById<EditText>(R.id.add_users_field_1)
        val startDateField = view.findViewById<EditText>(R.id.add_users_field_2)
        val endDateField = view.findViewById<EditText>(R.id.add_users_field_3)
        val addButton = view.findViewById<Button>(R.id.add_button)

        // Setup Spinners
        val surfaces = arrayOf("Duro", "Relva", "Terra")
        val surfaceAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, surfaces)
        surfaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        surfaceSpinner.adapter = surfaceAdapter

        val tournamentTypes = arrayOf("Simples")
        val tournamentTypeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tournamentTypes)
        tournamentTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tournamentTypeSpinner.adapter = tournamentTypeAdapter

        // Add Tournament Button
        addButton.setOnClickListener {
            val nome = tournamentField.text.toString()
            val piso = surfaceSpinner.selectedItem.toString()
            val tipoTorneio = tournamentTypeSpinner.selectedItem.toString()
            val local = localField.text.toString()
            val dataInicio = startDateField.text.toString()
            val dataFim = endDateField.text.toString()

            val createTorneioRequest = CreateTorneioRequest(
                nome = nome,
                piso_id = piso,
                tipo_torneio_id = tipoTorneio,
                local = local,
                data_inicio = dataInicio,
                data_fim = dataFim
            )

            torneioService.createTorneio(createTorneioRequest).enqueue(object : Callback<RequestResult.Success<CreateTorneiosResponse>> {
                override fun onResponse(
                    call: Call<RequestResult.Success<CreateTorneiosResponse>>,
                    response: Response<RequestResult.Success<CreateTorneiosResponse>>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Torneio adicionado com sucesso", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Erro ao adicionar torneio", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<RequestResult.Success<CreateTorneiosResponse>>, t: Throwable) {
                    Toast.makeText(requireContext(), "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        return view
    }
}
