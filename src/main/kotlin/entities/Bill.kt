package entities

import java.util.*

data class Bill (val buyer: Roommate, val shop: Shop, val date: Date, val purchasedItems: MutableList<Item> = mutableListOf<Item>(), var comment :String = "") {
    val billID = java.rmi.server.UID()

    fun addItem(item: Item){
        this.purchasedItems.add(item)
    }
    fun removeItem(item: Item): Boolean {
        if (purchasedItems.contains(item)){
            purchasedItems.remove(item)
            return true
        }
        return false
    }

    fun getOverview(): List<String> {
        return listOf(
            "buyer: ${buyer.name}", "shop: ${shop.name}", "date: $date", "comment: $comment"
        )
    }

}