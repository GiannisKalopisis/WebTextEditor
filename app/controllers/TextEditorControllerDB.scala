package controllers

import javax.inject._
import play.api.mvc._
import models.{PageManager, TextEditorDBModel}
import play.api.libs.json._

import scala.concurrent.Future

// DB imports
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class TextEditorControllerDB @Inject()(protected val dbConfigProvider: DatabaseConfigProvider,
									   val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext)
	extends BaseController with HasDatabaseConfigProvider[JdbcProfile] {
	
	private val textEditorDBModel = new TextEditorDBModel(db)	// db provided by Slick

	/**
	 * Create an Action to render an HTML page.
	 *
	 * The configuration in the `routes` file means that this method
	 * will be called when the application receives a `GET` request with
	 * a path of `/`.
	 */


	/**
	 * request is a function that is the request to the result that we want
	 *
	 * It has additional information, including the body
	 */

	def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
		Ok(views.html.index())
	}

	def viewAllFilesTitle(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
		val dbFiles: Future[Seq[String]] = textEditorDBModel.getAllTitles
		dbFiles.map(file => Ok(views.html.viewAllFilesTitle(file)))
	}

	def deletePage(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
		PageManager.deleteEntryOfList(request.headers.toMap(REFERER).flatMap(_.split("/")).last)
		Ok("PAGE DELETED")
	}

	def fileGetRequest(title: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
		textEditorDBModel.getFile(title).map { text =>
			if (text.nonEmpty) {
				Ok(views.html.fileView(title, text))
			} else {
				Ok(views.html.errorFilePage(title))
			}
		}
	}

	def getPage(title: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
		if (PageManager.containPage(title)) {
			Ok("true")
		} else {
			PageManager.addPageToList(title)
			Ok("false")
		}
	}

	def saveText(): Action[String] = Action(parse.text).async { implicit request =>
		val json = Json.parse(request.body)
		val title = (json \ "title").as[String]
		val text = (json \ "text").as[String]

		textEditorDBModel.update(title, text).flatMap { response =>
			if (response) {
				Future.successful(Ok("Data updated successfully"))
			} else {
				Future.successful(NotModified)
			}
		}
	}
	
	def fileCreationPage(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
		Ok(views.html.createFile())
	}
	
	def createFile(): Action[AnyContent] = Action.async { implicit request =>
		val postVal = request.body.asFormUrlEncoded
		postVal.map { args =>
			val newTitle = args("newTitle").head
			if (newTitle.isEmpty) Future.successful(Ok(views.html.errorAtFileCreation()))
			val newText = args("newText").head
			textEditorDBModel.addFile(newTitle, newText).flatMap { response =>
				if (response) {
					Future.successful(Redirect(routes.TextEditorControllerDB.viewAllFilesTitle()))
				} else {
					Future.successful(Ok(views.html.errorAtFileCreation()))
				}
			}
		}.getOrElse(Future.successful(Ok(views.html.errorAtFileCreation())))
	}
	
	def deleteFile(): Action[AnyContent] = Action.async { implicit request =>
		val postVal = request.body.asFormUrlEncoded
		postVal.map { args =>
			val deletedFile = args("deleteFile").head
			if (deletedFile.isEmpty) Future.successful(Ok(views.html.errorAtFileDeletion("")))
			val response = textEditorDBModel.deleteFile(deletedFile)
			response.flatMap { num =>
				if (num) {
					Future.successful(Ok(views.html.deleteFile(deletedFile)))
				} else {
					Future.successful(Ok(views.html.errorAtFileDeletion(" " + deletedFile)))
				}
			}
		}.getOrElse(Future.successful(Ok(views.html.errorAtFileDeletion(""))))
	}
}
