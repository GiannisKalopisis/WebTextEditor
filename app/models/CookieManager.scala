package models

import play.api.mvc.{Cookie, Cookies}

import scala.collection.mutable



object CookieManager {
	
	/**
	 * This is a map from file title -> cookie of file
	 */
	private val cookies = mutable.Map[String, String]()
	
	def addCookieToMap(title : String, cookieValue : String): Unit = {
		cookies.addOne(title, cookieValue)
	}
	
	def containFile(title : String): Boolean = {
		printCookieMap()
		cookies.contains(title)
	}
	
	def checkCookieEquality(title : String, cookieValue : String): Boolean = {
//		println(s"checkCookieEquality ${cookies.get(title)}")
		cookies.get(title).contains(cookieValue)
	}
	
	def deleteEntryOfMap(title : String): Unit = {
		cookies.remove(title)
	}
	
	private def printCookieMap(): Unit = {
		println("============================================")
		for (entry <- cookies) {
			println(s"entry: $entry")
		}
	}
}
