package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models.{CookieManager, FileModelInMemory}

import scala.util.Random

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
		Ok(views.html.viewAllFilesTitle(FileModelInMemory.getAllTitles))
	}
	
	def deleteCookie(): Action[AnyContent] = Action{ implicit request: Request[AnyContent] =>
		val referer : String = request.headers.toMap(REFERER).flatMap(_.split("/")).last
		CookieManager.deleteEntryOfMap(referer)
		Ok("DELETED COOKIE")
	}
	
	def fileGetRequest(title : String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
		if (CookieManager.containFile(title)) {
			Ok(views.html.pageUsed(title))
		} else {
			val valueOfCookie = Random.alphanumeric.take(10).mkString
			val text = FileModelInMemory.getFile(title)
			CookieManager.addCookieToMap(title, valueOfCookie)
			Ok(views.html.fileView(title, text)).withCookies(Cookie(title, valueOfCookie))
		}
	}
	
}
