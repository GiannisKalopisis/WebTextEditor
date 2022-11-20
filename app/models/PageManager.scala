package models

import scala.collection.mutable


object PageManager {
	
	private val pages = mutable.Set[String]()
	
	def addPageToList(title : String): Unit = {
		pages += title
	}
	
	def containPage(title : String): Boolean = {
		pages.contains(title)
	}
	
	def deleteEntryOfList(title : String): Unit = {
		pages -= title
	}
	
	def printPageList(): Unit = {
		for (entry <- pages) {
			println(s"entry: $entry, ")
		}
	}
}
