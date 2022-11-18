package controllers

import javax.inject._
import play.api.mvc._
import models.{CookieManager, TextEditorDBModel}
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
		val usedFiles: Seq[String] = CookieManager.getAllCookiesTitle.toSeq
		val dbFiles: Future[Seq[String]] = textEditorDBModel.getAllTitles
		val unusedFiles = dbFiles.flatMap { files =>
			Future.successful(files.diff(usedFiles))
		}
		unusedFiles.map(file => Ok(views.html.viewAllFilesTitle(file, usedFiles)))
		//val unusedFiles: Seq[String] = textEditorDBModel.getAllTitles.diff(usedFiles.toSeq)
		//Ok(views.html.viewAllFilesTitle(unusedFiles., usedFiles))
	}

	def deleteCookie(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
		println(s"Delete cookie ${request.headers.toMap(REFERER).flatMap(_.split("/")).last}")
		CookieManager.deleteEntryOfList(request.headers.toMap(REFERER).flatMap(_.split("/")).last)
		Ok("DELETED COOKIE")
	}

	def fileGetRequest(title: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
		println(s"file request $title")
		textEditorDBModel.getFile(title).map(text => Ok(views.html.fileView(title, text)))
//		val text = textEditorDBModel.getFile(title)
//		Ok(views.html.fileView(title, text))
	}

	def getCookie(title: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
		println("Get Cookie")
		if (CookieManager.containFile(title)) {
			Ok("true")
		} else {
			CookieManager.addCookieToList(title)
			Ok("false")
		}
	}

	def saveText(): Action[String] = Action(parse.text).async { implicit request =>
		val json = Json.parse(request.body)
		val title = (json \ "title").as[String]
		val text = (json \ "text").as[String]

		/* Add checker for cookie */

		textEditorDBModel.update(title, text).flatMap { response =>
			if (response) {
				Future.successful(Ok("Data updated successfully"))
			} else {
				Future.successful(NotModified)
			}
		}
	}
	
}
