package useCases

import androidx.compose.ui.window.rememberCursorPositionProvider
import entities.Bill
import entities.Debt
import entities.Item
import entities.Roommate
import java.util.*

class BillManager(private var bills: MutableList<Bill>, private val shopManager: ShopManager): interfaces.IBillManager{

    override fun getBillOverview(): List<String> {
        val billOverview = mutableListOf<String>()
        for (bill in bills){
            billOverview.add(bill.getOverview().toString())
        }
        return billOverview
    }

    override fun getBillOverview(shopId: String): List<String> {
        val billOverview = mutableListOf<String>()
        for (bill in bills){
            if (bill.shop.shopID.toString() == shopId) {
                billOverview.add(bill.getOverview().toString())
            }
        }
        return billOverview
    }

    override fun addBill(shopId: String, date: Date, buyer: Roommate, comment: String):Boolean {
        val shop = shopManager.getShopByID(shopId)
        if (shop != null){
            bills.add(Bill(buyer, shop, date, comment= comment))
            return true
        }
        return false
    }

    override fun addBill(
        shopId: String,
        date: Date,
        buyer: Roommate,
        purchasedItems: MutableList<Item>,
        comment: String
    ): Boolean {
        val shop = shopManager.getShopByID(shopId)
        if (shop != null){
            bills.add(Bill(buyer, shop , date, purchasedItems, comment ))
            return true
        }
        return false
    }

    override fun removeBill(billID: String): Boolean {
        for(bill: Bill in bills ){
            if(bill.billID.toString() == billID){
                bills.remove(bill)
                return true
            }
        }
        return false
    }

    override fun addItemToBill(billId: String, item: Item): Boolean {
        val bill: Bill? = getBill(billId)
        if(bill != null){
            bill.addItem(item)
            return true
        }
        return false
    }

    override fun addItemToBill(billId: String, articleId: String, priceSplit: MutableList<Roommate>): Boolean {
        val bill = getBill(billId)
        if (bill != null) {
            val article = bill.shop.getArticleById(articleId)
            if (article != null) {
                val newItem = Item(article, priceSplit)
                bill.addItem(newItem)
                return true
            }
        }
        return false
    }

    override fun removeItemFromBill(billId: String, item: Item): Boolean {
        val bill = getBill(billId)
        if (bill != null) {
            bill.removeItem(item)
            return true
        }
        return false
    }

    override fun getBill(billID: String): Bill? {
        for (bill in bills) {
            if(bill.billID.toString() == billID){
                return bill
            }
        }
        return null
    }

    override fun getOverallBalence(): List<Debt> {
        var debts: MutableList<Debt> = mutableListOf<Debt>()
        for (bill in bills){
            if (!bill.balanced){
                debts.addAll(bill.getCostAllocation())
            }
        }
        return sumUpDebts(debts)
    }

    private fun sumUpDebts(debts: List<Debt>): List<Debt> {
        var creditors: MutableList<Roommate> = mutableListOf<Roommate>()
        var debtors: MutableList<Roommate> = mutableListOf<Roommate>()
        for (debt in debts){
            if(!creditors.contains(debt.creditor)){
                creditors.add(debt.creditor)
            }
            if(!debtors.contains(debt.debtor)){
                debtors.add(debt.debtor)
            }
        }
        var summedUpDebts: MutableList<Debt> = mutableListOf()
        for (creditor in creditors) {
            for (debtor in debtors) {
                var summedUpDeptFromDebtor: Double = 0.0
                for (debt in debts){
                    if (debt.creditor == creditor && debt.debtor == debtor){
                        summedUpDeptFromDebtor += debt.amount
                    }
                }
                summedUpDebts.add(Debt(creditor, debtor, summedUpDeptFromDebtor))
            }
        }
        return summedUpDebts;
    }


}