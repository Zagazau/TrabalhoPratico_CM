package com.example.mytennis.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.api.core.HttpClient
import com.example.api.core.RequestResult
import com.example.api.core.TokenStorage
import com.example.api.interfaces.IAuthService
import com.example.api.models.responses.auth.RegisterResponse
import com.example.mytennis.R
import requests.auth.RegisterRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupFragment : Fragment() {

    private lateinit var authService: IAuthService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.signup, container, false)

        authService = HttpClient(IAuthService::class.java).api

        val firstNameEditText = view.findViewById<EditText>(R.id.first_name)
        val lastNameEditText = view.findViewById<EditText>(R.id.last_name)
        val usernameEditText = view.findViewById<EditText>(R.id.username)
        val emailEditText = view.findViewById<EditText>(R.id.email)
        val passwordEditText = view.findViewById<EditText>(R.id.password)
        val signupButton = view.findViewById<Button>(R.id.signup_button)
        val signInLink = view.findViewById<TextView>(R.id.sign_in_link)

        signupButton.setOnClickListener {
            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            val userRoleId = "5c3c1e78-eeff-416f-aeda-7d6578c95061"

            val registerRequest = RegisterRequest(
                name = "$firstName $lastName",
                email = email,
                password = password,
                role_id = userRoleId
            )

            Log.d("SignupFragment", "RegisterRequest: $registerRequest")

            authService.register(registerRequest).enqueue(object :
                Callback<RequestResult.Success<RegisterResponse>> {
                override fun onResponse(
                    call: Call<RequestResult.Success<RegisterResponse>>,
                    response: Response<RequestResult.Success<RegisterResponse>>
                ) {
                    Log.d("SignupFragment", "onResponse called")
                    Log.d("SignupFragment", "Response code: ${response.code()}")
                    Log.d("SignupFragment", "Response body: ${response.body()}")
                    Log.d("SignupFragment", "Response error body: ${response.errorBody()?.string()}")

                    if (response.isSuccessful) {
                        val result = response.body()?.data
                        result?.let {
                            val token = "your_generated_token_here"
                            TokenStorage.getInstance().setToken(token)
                            Toast.makeText(requireContext(), "Signup successful", Toast.LENGTH_SHORT).show()
                            Log.d("SignupFragment", "Signup successful")

                            // Navegar para o MainUserFragment
                            val mainUserFragment = MainUserFragment()
                            val transaction = requireActivity().supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.fragment_container, mainUserFragment)
                            transaction.addToBackStack(null)
                            transaction.commit()
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("SignupFragment", "Signup failed: $errorBody")
                        Toast.makeText(requireContext(), "Signup failed: $errorBody", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<RequestResult.Success<RegisterResponse>>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    Log.e("SignupFragment", "onFailure: ${t.message}")
                }
            })
        }

        // Adicionar OnClickListener para sign_in_link
        signInLink.setOnClickListener {
            val loginFragment = LoginFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, loginFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }
}
