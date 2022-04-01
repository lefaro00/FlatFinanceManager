package entities

import java.rmi.server.UID

data class Article(var name: String, var price: Double){
    val articleID = UID()
}