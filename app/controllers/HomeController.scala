package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models.{FileModelInMemory}

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
	
	def viewAllFilesTitle(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
		val files = FileModelInMemory.getAllTitles
		Ok(views.html.viewAllFilesTitle(files))
	}
	
	def getText(title : String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
		val text = FileModelInMemory.getFile(title)
		Ok(views.html.fileView(title, text))
	}
	
	/**
	 * No need for parameters (they aren't in URL), because its a POST method.
	 *
	 * We will take them from body
	 */
	//	def printFilePost: Action[AnyContent] = Action { request =>
	//		val postVals = request.body.asFormUrlEncoded
	//		postVals.map { args =>
	//			val id = args("id").head
	//			val name = args("name").head
	//			val text = args("text").head
	//			Ok(s"File '$name', with id '$id' and text '$text'")
	//		}.getOrElse(Ok(""))
	//	}
	
	def printTestFile(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
		//		val file = TestFile(1, "file1", "This is dummy text")
		Ok(views.html.index())
	}
	
	def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
		Ok(views.html.index())
	}
	
}
