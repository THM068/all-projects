package com.learn.junit

data class Money(val amount: Int, val currency: String) {
    init {
        require(amount > 0 , { "Amount should be greater than zero" })
    }
}