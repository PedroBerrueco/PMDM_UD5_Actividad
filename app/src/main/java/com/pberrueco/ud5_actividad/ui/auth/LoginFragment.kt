package com.pberrueco.ud5_actividad.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pberrueco.ud5_actividad.data.data_store.DataStoreRepository
import com.pberrueco.ud5_actividad.databinding.FragmentLogin2Binding
import com.pberrueco.ud5_actividad.ui.recycler.HomesActivity
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    private lateinit var _binding: FragmentLogin2Binding
    private val binding: FragmentLogin2Binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogin2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAccess.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                val userEt = binding.etUsername.text.toString()
                val passEt = binding.etPassword.text.toString()
                val obtainedUser = obtainUser()
                val obtainedPass = obtainPass()

                val valida = validador(userEt, passEt, obtainedUser, obtainedPass)

                if (valida) {
                    toRV(userEt)
                } else {
                    Toast.makeText(context, "FALLO LOGIN", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            toRegister()
        }
    }

    private fun toRV(userEt: String) {
        val logged = true;
        val intent = Intent(requireContext(), HomesActivity::class.java)
        intent.putExtra("user", userEt)
        intent.putExtra("logged", logged)

        startActivity(intent)
    }

    private fun validador(userEt: String, passEt: String, user: String, pass: String): Boolean {
        var valida = false
        if (user.isNotEmpty()  && (userEt.equals(user) && passEt.equals(pass))){
            valida = true
        }
        return valida
    }

    private fun toRegister() {
        val action =
            LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            //LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            //com.pberrueco.pmdmu3actividad.LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(action)
    }


    private suspend fun obtainUser(): String {
        val userDeferred = CompletableDeferred<String>()
        lifecycleScope.launch(Dispatchers.IO) {
            DataStoreRepository.getUser(requireContext()).collect { user ->
                userDeferred.complete(user)
            }
        }
        val userObtained = userDeferred.await()
        return userObtained
    }

    private suspend fun obtainPass(): String {
        val passDeferred = CompletableDeferred<String>()
        lifecycleScope.launch(Dispatchers.IO) {
            DataStoreRepository.getPassword(requireContext()).collect { pass ->
                passDeferred.complete(pass)
            }
        }
        val passObtained = passDeferred.await()
        return passObtained
    }


}