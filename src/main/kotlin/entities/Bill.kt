package entities

import java.sql.Timestamp
import java.util.*

data class Bill (val buyer: Roommate, val shop: Shop, val date: Date, val purchasedItems: MutableList<Item> = mutableListOf<Item>(), var comment :String = "") {
    val billID = java.rmi.server.UID()
    var balanced: Boolean = false;
    var balanceDate: Date? = null;
    var containedDebts: MutableList<Debt> = mutableListOf<Debt>();

    fun addItem(item: Item){
        this.purchasedItems.add(item)
        containedDebts.addAll(calcItemDebts(item))
    }



    fun removeItem(item: Item): Boolean {
        if (purchasedItems.contains(item)){
            containedDebts.removeAll(calcItemDebts(item))
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
    fun setToBalanced(balanceDate: Date){
        balanced = true;
        this.balanceDate = balanceDate
    }

    fun getCostAllocation():List<Debt>{
        var roommates: MutableList<Roommate> = mutableListOf<Roommate>()
        var debtSums: MutableList<Double> = mutableListOf<Double>()
        for (debt in containedDebts){
            if (!roommates.contains(debt.debtor)){
                roommates.add(debt.debtor)
                debtSums.add(0.0)
            }
            debtSums[roommates.indexOf(debt.debtor)] += debt.amount
        }
        var costAllocations: MutableList<Debt> = mutableListOf<Debt>()
        for (i: Int in 0..roommates.count()){
            costAllocations.add(Debt(this.buyer, roommates[i], debtSums[i]))
        }
        return costAllocations
    }

    private fun calcItemDebts(item: Item):List<Debt>{
        var debts: MutableList<Debt> = mutableListOf<Debt>()
        var numberBuyers = item.priceSplit.count()
        var pricePerPerson = item.article.price / numberBuyers
        for (roommate in item.priceSplit){
            if (roommate != this.buyer){
                debts.add(Debt(this.buyer, roommate, pricePerPerson))
            }
        }
        return debts
    }
}