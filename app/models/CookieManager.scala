package models

import play.api.mvc.{Cookie, Cookies}

import scala.collection.mutable



object CookieManager {
	
	/**
	 * This is a map from file title -> cookieValue of file
	 */
	private val cookies = mutable.Map[String, String]()
	
	def addCookieToMap(title : String, cookieValue : String): Unit = {
		cookies.addOne(title, cookieValue)
	}
	
	def containFile(title : String): Boolean = {
		cookies.contains(title)
	}
	
	def equalCookieValue(title : String, cookieValue : String): Boolean = {
		cookies.get(title).contains(cookieValue)
	}
	
	def deleteEntryOfMap(title : String): Unit = {
		cookies.remove(title)
	}
	
	def printCookieMap(): Unit = {
		for (entry <- cookies) {
			println(s"entry: $entry, ")
		}
	}
}
