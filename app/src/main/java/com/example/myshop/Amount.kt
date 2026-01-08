package com.example.myshop

sealed class Amount {
    data class Pieces(val count: Int) : Amount()
    data class Grams(val grams: Int) : Amount()
}