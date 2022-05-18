package com.example.factorial

sealed class State()

object Error: State() // оскільки ці два класи не мають данихґ. то можна використатти singleton
object Progress: State()
class Factorial(val value: String):State()