package com.example.mytennis.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mytennis.R

class AdminMenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_admin, container, false)

        val addTournaments = view.findViewById<TextView>(R.id.adicionar_torneios)
        val deleteTournaments = view.findViewById<TextView>(R.id.eliminar_torneios)
        val addGames = view.findViewById<TextView>(R.id.adicionar_jogos)
        val viewUsers = view.findViewById<TextView>(R.id.users)
        val addPlayers = view.findViewById<TextView>(R.id.adicionar_jogadores)

        addTournaments.setOnClickListener {
            val fragment = AddTournamentFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        deleteTournaments.setOnClickListener {
            val fragment = DeleteTournamentFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        addGames.setOnClickListener {
            val fragment = AddGameFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        viewUsers.setOnClickListener {
            val fragment = ViewUsersFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        addPlayers.setOnClickListener {
            val fragment = AddPlayerFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }
}
