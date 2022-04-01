package interfaces

import entities.Bill
import entities.Item
import entities.Roommate
import java.util.*

interface IBillManager {
    fun getBillOverview():List<String>
    fun getBillOverview(shopId: String):List<String>
    fun addBill(shopId: String, date: Date, buyer: Roommate, comment: String = "") :Boolean
    fun addBill(shopId: String, date: Date, buyer: Roommate, purchasedItems: MutableList<Item>, comment: String = "") :Boolean
    fun removeBill(billID: String):Boolean
    fun addItemToBill(billId: String, item:Item): Boolean
    fun addItemToBill(billId: String, articleId: String, priceSplit: MutableList<Roommate>): Boolean
    fun removeItemFromBill(billId: String, item: Item): Boolean
    fun getBill(billID: String):Bill?
}