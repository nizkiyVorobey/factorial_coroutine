package com.example.factorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.factorial.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        observeViewModel()

        binding.calculate.setOnClickListener {
            viewModel.calculate(binding.editText.text.toString())
        }
    }

    private fun observeViewModel() = with(binding) {
        viewModel.state.observe(this@MainActivity) {
            progressbar.visibility = View.GONE
            calculate.isEnabled = true

            when(it) {
                is Error -> Toast.makeText(this@MainActivity, "You didn't input value", Toast.LENGTH_SHORT).show()
                is Progress -> {
                        progressbar.visibility = View.VISIBLE
                        calculate.isEnabled = false
                }
                is Factorial -> textViewFactorial.text = it.value
            }
        }
    }
}