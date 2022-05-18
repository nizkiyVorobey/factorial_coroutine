package com.example.factorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.math.BigInteger
import kotlin.concurrent.thread
import kotlin.coroutines.suspendCoroutine

class MainViewModel : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    // // 2
//    private val myCoroutineScope = CoroutineScope(Dispatchers.Main + CoroutineName("my coroutine scope"))

    fun calculate(value: String?) {
        _state.value = Progress
        if (value.isNullOrBlank()) {
            _state.value = Error
            return
        }

        // // 2
//        myCoroutineScope.launch {
//            val number = factorial(value.toLong())
//            _state.value = Factorial(value = number)
//        }

        viewModelScope.launch {
            val number = factorial(value.toLong())
            _state.value = Factorial(value = number)
        }
    }

    private suspend fun factorial(number: Long): String {
        return withContext(Dispatchers.Default) {
            var res = BigInteger.ONE
            for (i in 1..number) {
                res = res.multiply(BigInteger.valueOf(i))
            }
            res.toString()
        }
    }


    // // 1
//    // перший варінат з callback, ми власноруч створюємо новий  thread, і щоб повернути з нього значення використовуємо it.resumeWith у suspendCoroutine
//    private suspend fun factorial(number: Long): String {
//       return suspendCoroutine {
//          thread {
//              var res = BigInteger.ONE
//              for (i in 1..number) {
//                  res = res.multiply(BigInteger.valueOf(i))
//              }
//
//              it.resumeWith(Result.success(res.toString()))
//          }
//       }
//    }

    // // 2
//    override fun onCleared() {
//        super.onCleared()
//        myCoroutineScope.cancel()
//    }
}