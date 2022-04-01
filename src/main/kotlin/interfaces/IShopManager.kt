package interfaces

import entities.Shop
import entities.Shoptype

interface IShopManager {
    var shops: MutableList<Shop>
    fun addShop(name: String, shoptype: Shoptype)
    fun removeShop(shopID: String): Boolean
    fun alterShop(shopID: String, name: String, shoptype: Shoptype): Boolean
    fun getShopOverview(shoptype: Shoptype): List<String>
    fun getShopOverview()
    fun getShopByID(shopID: String): Shop?
}