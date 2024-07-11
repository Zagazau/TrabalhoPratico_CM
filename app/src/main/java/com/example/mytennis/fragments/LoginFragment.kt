package com.example.mytennis.fragments

import User
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
import com.example.api.core.TokenStorage
import com.example.api.interfaces.IAuthService
import com.example.api.models.responses.auth.LoginResponse
import com.example.mytennis.R
import requests.auth.LoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var authService: IAuthService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.signin, container, false)

        authService = HttpClient(IAuthService::class.java).api

        val emailEditText = view.findViewById<EditText>(R.id.email)
        val passwordEditText = view.findViewById<EditText>(R.id.password)
        val submitButton = view.findViewById<Button>(R.id.submit_button)

        submitButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            val loginRequest = LoginRequest(
                email = email,
                password = password
            )

            Log.d("LoginFragment", "LoginRequest: $loginRequest")

            authService.login(loginRequest).enqueue(object :
                Callback<RequestResult.Success<LoginResponse>> {
                override fun onResponse(
                    call: Call<RequestResult.Success<LoginResponse>>,
                    response: Response<RequestResult.Success<LoginResponse>>
                ) {
                    Log.d("LoginFragment", "onResponse called")
                    Log.d("LoginFragment", "Response code: ${response.code()}")
                    Log.d("LoginFragment", "Response body: ${response.body()}")
                    Log.d("LoginFragment", "Response error body: ${response.errorBody()?.string()}")

                    if (response.isSuccessful) {
                        val result = response.body()?.data
                        result?.let {
                            Log.d("LoginFragment", "Received token: ${it.token}")
                            Log.d("LoginFragment", "Received userId: ${it.userId}")
                            Log.d("LoginFragment", "Received userName: ${it.userName}")

                            if (it.token == null || it.userId == null) {
                                Log.e("LoginFragment", "Received null token or userId")
                                Toast.makeText(requireContext(), "Invalid login response", Toast.LENGTH_SHORT).show()
                                return
                            }

                            val token = it.token
                            TokenStorage.getInstance().setToken(token)
                            Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                            Log.d("LoginFragment", "Login successful")

                            authService.getUserDetails(it.userId).enqueue(object :
                                Callback<RequestResult.Success<User>> {
                                override fun onResponse(
                                    call: Call<RequestResult.Success<User>>,
                                    response: Response<RequestResult.Success<User>>
                                ) {
                                    if (response.isSuccessful) {
                                        val user = response.body()?.data
                                        user?.let { userDetails ->
                                            Log.d("LoginFragment", "Received user details: $userDetails")
                                            if (userDetails.role.id == "a7fac8bc-0455-41d3-81c7-9fd57d1a5c80") {
                                                val adminMenuFragment = AdminMenuFragment()
                                                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                                                transaction.replace(R.id.fragment_container, adminMenuFragment)
                                                transaction.addToBackStack(null)
                                                transaction.commit()
                                            } else {
                                                val mainUserFragment = MainUserFragment()
                                                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                                                transaction.replace(R.id.fragment_container, mainUserFragment)
                                                transaction.addToBackStack(null)
                                                transaction.commit()
                                            }
                                        }
                                    } else {
                                        val errorBody = response.errorBody()?.string()
                                        Log.e("LoginFragment", "Failed to get user details: $errorBody")
                                        Toast.makeText(requireContext(), "Failed to get user details: $errorBody", Toast.LENGTH_LONG).show()
                                    }
                                }

                                override fun onFailure(call: Call<RequestResult.Success<User>>, t: Throwable) {
                                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                                    Log.e("LoginFragment", "onFailure: ${t.message}")
                                }
                            })
                        } ?: run {
                            Log.e("LoginFragment", "Login response data is null")
                            Toast.makeText(requireContext(), "Login failed: Response data is null", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("LoginFragment", "Login failed: $errorBody")
                        Toast.makeText(requireContext(), "Login failed: $errorBody", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<RequestResult.Success<LoginResponse>>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    Log.e("LoginFragment", "onFailure: ${t.message}")
                }
            })
        }

        return view
    }
}
