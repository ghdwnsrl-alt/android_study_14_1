package com.arom.assignment1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.arom.assignment1.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    companion object{
        const val TAG :String="로그"
    }
    lateinit var myTextViewModel: MyTextViewModel







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myTextViewModel=ViewModelProvider(this).get(MyTextViewModel::class.java)
        val sharedPreferences = this.getSharedPreferences("search_prefs", Context.MODE_PRIVATE)
        binding.edittext.setText(sharedPreferences.getString("last_search_query", ""))



        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    myTextViewModel.currenValue.collect{
                        binding.edittext.setText(sharedPreferences.getString("last_search_query", ""))



                    }
                }

            }

        }
        binding.savebtn.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        val userInput = binding.edittext.text.toString()
        when (v) {
            binding.savebtn -> {
                saveLastSearchQuery(userInput)
                myTextViewModel.updateValue(actionType = ActionType.SAVE, userInput)

            }
        }
    }
    fun saveLastSearchQuery(query: String) {
        val sharedPreferences = this.getSharedPreferences("search_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("last_search_query", query)
        editor.apply()
    }
}