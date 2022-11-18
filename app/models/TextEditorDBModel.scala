package models

import models.Tables._
import slick.jdbc.PostgresProfile.api._     // replace with your preferable DB

import scala.concurrent.{ExecutionContext, Future}


class TextEditorDBModel(db: Database)(implicit ec: ExecutionContext) {
	
	private def fileExistsInDB(title: String): Future[Boolean] = {
		val matches = db.run(Files.filter(filesRow => filesRow.title === title).result)
		matches.map(filesRows => filesRows.nonEmpty)
	}

	def getAllTitles: Future[Seq[String]] = {
		db.run((for {file <- Files} yield {file.title}).result)
	}

	def getFile(title: String): Future[String] = {
		db.run(Files.filter(filesRow => filesRow.title === title).map(_.text).result.head)
	}

	def update(title: String, text: String): Future[Boolean] = {
		fileExistsInDB(title).flatMap { fileExists =>
			if (fileExists) {
				db.run(
					(for {
						file <- Files if file.title === title
					} yield {
						file.text
					}).update(text)
				).flatMap { updatedFiles =>
					if (updatedFiles == 1) {
						Future.successful(true)
					} else {
						Future.successful(false)
					}
				}
			} else {
				Future.successful(false)
			}
		}
	}

	def addFile(title: String, text: String): Future[Boolean] = {
		db.run(Files.filter(filesRow => filesRow.title === title).result)
			.flatMap { filesRow =>
				if (filesRow.nonEmpty) {
					Future.successful(false)
				} else {
					db.run(Files += FilesRow(-1, title, text))
					  .map(addCount => addCount > 0) // if it adds a file, it returns true
				}
			}
	}
}
