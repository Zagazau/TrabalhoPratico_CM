package com.example.mytennis.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.api.core.HttpClient
import com.example.api.core.RequestResult
import com.example.api.interfaces.IJogadorService
import com.example.api.models.responses.jogadores.CreateJogadorResponse
import requests.jogador.CreateJogadorRequest
import com.example.mytennis.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AddPlayerFragment : Fragment() {

    private lateinit var jogadorService: IJogadorService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_player, container, false)

        jogadorService = HttpClient(IJogadorService::class.java).api

        val nomeField = view.findViewById<EditText>(R.id.jogador_nome_field)
        val nacionalidadeField = view.findViewById<EditText>(R.id.jogador_nacionalidade_field)
        val addButton = view.findViewById<Button>(R.id.add_button)

        addButton.setOnClickListener {
            val nome = nomeField.text.toString()
            val nacionalidade = nacionalidadeField.text.toString()

            val createJogadorRequest = CreateJogadorRequest(
                id = UUID.randomUUID().toString(),
                nome = nome,
                nacionalidade = nacionalidade
            )

            Log.d("AddPlayerFragment", "CreateJogadorRequest: $createJogadorRequest")

            jogadorService.createJogador(createJogadorRequest).enqueue(object : Callback<RequestResult.Success<CreateJogadorResponse>> {
                override fun onResponse(
                    call: Call<RequestResult.Success<CreateJogadorResponse>>,
                    response: Response<RequestResult.Success<CreateJogadorResponse>>
                ) {
                    Log.d("AddPlayerFragment", "onResponse called")
                    Log.d("AddPlayerFragment", "Response code: ${response.code()}")
                    Log.d("AddPlayerFragment", "Response body: ${response.body()}")
                    Log.d("AddPlayerFragment", "Response error body: ${response.errorBody()?.string()}")

                    if (response.isSuccessful) {
                        val result = response.body()?.data
                        if (result != null) {
                            Toast.makeText(requireContext(), "Jogador adicionado com sucesso", Toast.LENGTH_SHORT).show()

                            // Navegar de volta para o menu do admin
                            val adminMenuFragment = AdminMenuFragment()
                            val transaction = requireActivity().supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.fragment_container, adminMenuFragment)
                            transaction.addToBackStack(null)
                            transaction.commit()
                        } else {
                            Toast.makeText(requireContext(), "Erro: Resposta inesperada do servidor", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Erro ao adicionar jogador", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<RequestResult.Success<CreateJogadorResponse>>, t: Throwable) {
                    Log.e("AddPlayerFragment", "onFailure: ${t.message}")
                    Toast.makeText(requireContext(), "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        return view
    }
}
