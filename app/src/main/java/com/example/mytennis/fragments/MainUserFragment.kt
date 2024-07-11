package com.example.mytennis.fragments

import TournamentListFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mytennis.R

class MainUserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_user, container, false)

        val infoButton = view.findViewById<Button>(R.id.info_button)
        infoButton.setOnClickListener {
            val tournamentListFragment = TournamentListFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, tournamentListFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }
}
