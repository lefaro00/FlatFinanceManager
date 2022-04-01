package useCases

import entities.*
import org.junit.Test

internal class BillManagerTest{
    private val testBillManager: BillManager = BillManager(
        mutableListOf<Bill>(), ShopManager(
            mutableListOf<Shop>(
                Shop(name= "REWE", shoptype = Shoptype(
                    "Supermarkt", "lorem ipsum"),
                    mutableListOf<Article>(Article("Milch", 0.91))))))

    /*@Test
    fun testAddBill(){
        val shopID = BillManager.getSh
        val expected = true
        testBillManager.addBill()
    }*/


}