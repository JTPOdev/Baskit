package com.example.splashscreenbaskit.data

data class BasketItem (val name: String, val quantity: Int, val price: Double)

class Basket {
    private val items = mutableListOf<BasketItem>()

    fun addItem(item: BasketItem){
        items.add(item)
    }

    fun getItems(): List<BasketItem> {
        return items
    }
}