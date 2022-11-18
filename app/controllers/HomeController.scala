package controllers

import javax.inject._
import play.api.mvc._
import models.{CookieManager, FileModelInMemory}
import play.api.libs.json._

import scala.collection.mutable

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
	
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
	
	def viewAllFilesTitle(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
//		val usedFiles: mutable.Set[String] = CookieManager.getAllCookiesTitle
//		val unusedFiles: Seq[String] = FileModelInMemory.getAllTitles.diff(usedFiles.toSeq)
		val unusedFiles: Seq[String] = FileModelInMemory.getAllTitles
		Ok(views.html.viewAllFilesTitle(unusedFiles))
	}
	
	def deleteCookie(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
		println(s"Delete cookie ${request.headers.toMap(REFERER).flatMap(_.split("/")).last}")
		CookieManager.deleteEntryOfList(request.headers.toMap(REFERER).flatMap(_.split("/")).last)
		Ok("DELETED COOKIE")
	}
	
	// old
//	def fileGetRequest(title: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
//		println(s"file request ${title}")
//		if (CookieManager.containFile(title)) {
//			Ok(views.html.pageUsed(title))
//		} else {
//			val valueOfCookie = Random.alphanumeric.take(10).mkString
//			val text = FileModelInMemory.getFile(title)
//			CookieManager.addCookieToMap(title, valueOfCookie)
//			Ok(views.html.fileView(title, text)).withCookies(Cookie(title, valueOfCookie))
//		}
//	}
	
	def fileGetRequest(title: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
		println(s"file request $title")
		val text = FileModelInMemory.getFile(title)
		Ok(views.html.fileView(title, text))
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
	
	def saveText(): Action[String] = Action(parse.text) { implicit request =>
		val json = Json.parse(request.body)
		val title = (json \ "title").as[String]
		val text = (json \ "text").as[String]
		
		/* Add checker for cookie */
		
		if (FileModelInMemory.update(title, text)) {
			Ok("Data updated successfully")
		} else {
			NotModified
		}
	}
	
}
