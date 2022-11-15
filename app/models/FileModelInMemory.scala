package models

import scala.collection.mutable

object FileModelInMemory {
	
	private val files = mutable.Map[String, String](
		"java" -> "Dummy text for file 1",
		"c++" -> "Dummy text for file 2",
		"python" -> "Dummy text for file 3",
		"scala" -> "Dummy text for file 4"
	)
	
	private def fileExists(title : String): Boolean = {
		if (files.contains(title)) true else false
	}
	
	def getAllTitles: Seq[String] = {
		files.keySet.toSeq
	}
	
	def getFile(title: String): String = {
		files(title)
	}
	
	def update(title: String, text: String): Boolean = {
		if (fileExists(title)) {
			files(title) = text
			return true
		}
		false
	}
	
	def addFile(title: String, text: String): Unit = {
		files.addOne(title, text)
	}
}
