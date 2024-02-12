package com.pberrueco.ud5_actividad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pberrueco.ud5_actividad.databinding.ActivityHomesBinding
import com.pberrueco.ud5_actividad.network.APIManager
import com.pberrueco.ud5_actividad.network.model.AllGamesResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomesActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityHomesBinding
    private val binding: ActivityHomesBinding get() = _binding

    private val adapter = GameAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvListgames.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvListgames.adapter = adapter

        getAllGamesFromAPI()
    }

    private fun getAllGamesFromAPI() {
        lifecycleScope.launch(Dispatchers.IO){
            val response = APIManager.service.getAllGames()

            if(response.isSuccessful){
                withContext(Dispatchers.Main){
                    val body = response.body()
                    if(body != null){
                        adapter.submitList(body)
                    }else{
                        Toast.makeText(this@HomesActivity, "Sin datos de la API", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                withContext(Dispatchers.Main){
                    Toast.makeText(this@HomesActivity, "Algo fue mal", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}