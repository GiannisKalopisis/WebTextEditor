package models

import scala.collection.mutable


object CookieManager {
	
	/**
	 * This is a map from file title -> cookieValue of file
	 */
	private val cookies = mutable.Set[String]()
	
	def getAllCookiesTitle: mutable.Set[String] = {
		cookies
	}
	
	def addCookieToList(title : String): Unit = {
		cookies += title
		printCookieList()
	}
	
	def containFile(title : String): Boolean = {
		cookies.contains(title)
	}
	
	def deleteEntryOfList(title : String): Unit = {
		cookies -= title
	}
	
	def printCookieList(): Unit = {
		for (entry <- cookies) {
			println(s"entry: $entry, ")
		}
	}
}
