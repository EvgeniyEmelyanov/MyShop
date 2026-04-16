package com.example.myshop.domain.cart.model

sealed class Amount {

    data class Piece(val count: Long) : Amount() {
        init {
            require(count > 0) { "Piece count must be greater than zero" }
        }
    }


    data class Grams(val grams: Long) : Amount() {
        init {
            require(grams > 0) { "Grams count must be greater than zero" }
        }
    }
}


