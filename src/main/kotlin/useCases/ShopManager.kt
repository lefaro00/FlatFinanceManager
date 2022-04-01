package useCases

import interfaces.IShopManager
import entities.Shop
import entities.Shoptype

class ShopManager(override var shops: MutableList<Shop>): IShopManager {
    override fun addShop(name: String, shoptype: Shoptype) {
        shops.add(Shop(name, shoptype))
    }

    override fun removeShop(shopID: String): Boolean {
        val shop: Shop? = getShopByID(shopID)
        if (shop != null){
            shops.remove(shop)
            return true
        }
        return false
    }

    override fun alterShop(shopID: String, name: String, shoptype: Shoptype): Boolean {
        val shop: Shop? = getShopByID(shopID)
        if (shop != null){
            shop.name = name
            shop.shoptype = shoptype
            return true
        }
        return false
    }

    override fun getShopOverview(shoptype: Shoptype):List<String> {
        val overview = mutableListOf<String>()
        for (shop in shops){
            if (shop.shoptype == shoptype){
                overview.add(shop.getOverview().toString())
            }
        }
        return overview
    }

    override fun getShopOverview() {
        val overview = mutableListOf<String>()
        for (shop in shops) {
            overview.add(shop.getOverview().toString())
        }
    }

    override fun getShopByID(shopID: String): Shop? {
        for (shop in shops) {
            if (shop.shopID.toString() == shopID){
                return shop
            }
        }
        return null
    }
}