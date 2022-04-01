package entities

import java.rmi.server.UID

class Shop(var name: String, var shoptype: Shoptype, var assortment: MutableList<Article> = mutableListOf<Article>()) {
    val shopID = UID();
    fun getArticleById(articleID: String):Article?{
        for(article in assortment){
            if (article.articleID.toString() == articleID){
                return article
            }
        }
        return null
    }
    fun getOverview(): List<String>{
        return listOf("name: $name", "shoptype: ${shoptype.name}")
    }
}
