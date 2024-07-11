package com.example.mytennis.fragments

import Jogador
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.api.core.HttpClient
import com.example.api.core.RequestResult
import com.example.api.interfaces.IJogadorService
import com.example.api.interfaces.ITorneioService
import com.example.api.interfaces.IJogoService
import com.example.api.models.responses.jogos.CreateJogosResponse
import com.example.mytennis.R
import requests.jogo.CreateJogoRequest
import responses.torneios.GetAllTorneiosResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddGameFragment : Fragment() {

    private lateinit var torneioService: ITorneioService
    private lateinit var jogoService: IJogoService
    private lateinit var jogadorService: IJogadorService
    private lateinit var jogadores: List<Jogador>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_games_admin, container, false)

        torneioService = HttpClient(ITorneioService::class.java).api
        jogoService = HttpClient(IJogoService::class.java).api
        jogadorService = HttpClient(IJogadorService::class.java).api

        val tournamentSpinner = view.findViewById<Spinner>(R.id.tournament_spinner)
        val tournamentTypeSpinner = view.findViewById<Spinner>(R.id.tournament_type_spinner)
        val player1Spinner = view.findViewById<Spinner>(R.id.player1_spinner)
        val player2Spinner = view.findViewById<Spinner>(R.id.player2_spinner)
        val arbitroField = view.findViewById<EditText>(R.id.arbitro_field)
        val dataField = view.findViewById<EditText>(R.id.data_field)
        val duracaoField = view.findViewById<EditText>(R.id.duracao_field)
        val addButton = view.findViewById<Button>(R.id.add_button)
        val closeButton = view.findViewById<ImageView>(R.id.close_icon)


        closeButton.setOnClickListener {
            val adminMenuFragment = AdminMenuFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, adminMenuFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        // Fill tournament spinner
        torneioService.getTorneios().enqueue(object : Callback<RequestResult.Success<GetAllTorneiosResponse>> {
            override fun onResponse(call: Call<RequestResult.Success<GetAllTorneiosResponse>>, response: Response<RequestResult.Success<GetAllTorneiosResponse>>) {
                if (response.isSuccessful) {
                    val torneios = response.body()?.data?.torneios ?: emptyList()
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, torneios.map { "${it.nome} (${it.id})" })
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    tournamentSpinner.adapter = adapter
                } else {
                    Toast.makeText(requireContext(), "Erro ao carregar torneios", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RequestResult.Success<GetAllTorneiosResponse>>, t: Throwable) {
                Toast.makeText(requireContext(), "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        // Fill tournament type spinner
        val tournamentTypes = arrayOf("Simples")
        val tournamentTypeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tournamentTypes)
        tournamentTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tournamentTypeSpinner.adapter = tournamentTypeAdapter

        // Fill player spinners
        jogadorService.getJogadores().enqueue(object : Callback<List<Jogador>> {
            override fun onResponse(call: Call<List<Jogador>>, response: Response<List<Jogador>>) {
                if (response.isSuccessful) {
                    jogadores = response.body() ?: emptyList()
                    Log.d("AddGameFragment", "Jogadores carregados: $jogadores")
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, jogadores.map { "${it.nome} (${it.id})" })
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    player1Spinner.adapter = adapter
                    player2Spinner.adapter = adapter
                } else {
                    Log.e("AddGameFragment", "Erro ao carregar jogadores: ${response.errorBody()?.string()}")
                    Toast.makeText(requireContext(), "Erro ao carregar jogadores", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Jogador>>, t: Throwable) {
                Log.e("AddGameFragment", "Erro ao carregar jogadores: ${t.message}")
                Toast.makeText(requireContext(), "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        // Add game button
        addButton.setOnClickListener {
            val selectedTournament = tournamentSpinner.selectedItem.toString().split(" (")[1].removeSuffix(")")
            val selectedType = tournamentTypeSpinner.selectedItem.toString()
            val player1 = player1Spinner.selectedItem.toString().split(" (")[1].removeSuffix(")")
            val player2 = player2Spinner.selectedItem.toString().split(" (")[1].removeSuffix(")")
            val arbitro = arbitroField.text.toString()
            val data = dataField.text.toString()
            val duracao = duracaoField.text.toString()

            Log.d("AddGameFragment", "Selected Tournament: $selectedTournament")
            Log.d("AddGameFragment", "Selected Type: $selectedType")
            Log.d("AddGameFragment", "Player 1 ID: $player1")
            Log.d("AddGameFragment", "Player 2 ID: $player2")
            Log.d("AddGameFragment", "Árbitro: $arbitro")
            Log.d("AddGameFragment", "Data: $data")
            Log.d("AddGameFragment", "Duração: $duracao")

            // Create request object
            val createJogoRequest = CreateJogoRequest(
                torneio_id = selectedTournament,
                data = data,
                duracao = duracao,
                arbitro = arbitro,
                jogadores = listOf(player1, player2)
            )

            Log.d("AddGameFragment", "CreateJogoRequest: $createJogoRequest")

            // Call API to create game
            jogoService.createJogo(createJogoRequest).enqueue(object : Callback<RequestResult.Success<CreateJogosResponse>> {
                override fun onResponse(call: Call<RequestResult.Success<CreateJogosResponse>>, response: Response<RequestResult.Success<CreateJogosResponse>>) {
                    Log.d("AddGameFragment", "onResponse called")
                    Log.d("AddGameFragment", "Response code: ${response.code()}")
                    Log.d("AddGameFragment", "Response body: ${response.body()}")
                    Log.d("AddGameFragment", "Response error body: ${response.errorBody()?.string()}")

                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Jogo adicionado com sucesso", Toast.LENGTH_SHORT).show()
                        // Navigate back to admin menu
                        val adminMenuFragment = AdminMenuFragment()
                        val transaction = requireActivity().supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.fragment_container, adminMenuFragment)
                        transaction.addToBackStack(null)
                        transaction.commit()
                    }
                }

                override fun onFailure(call: Call<RequestResult.Success<CreateJogosResponse>>, t: Throwable) {
                    Log.e("AddGameFragment", "Erro ao adicionar jogo: ${t.message}")
                    Toast.makeText(requireContext(), "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        return view
    }
}

