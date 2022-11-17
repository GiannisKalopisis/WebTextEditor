package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends Tables {
  val profile = slick.jdbc.PostgresProfile
}

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Files.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Files
   *  @param fileId Database column file_id SqlType(serial), AutoInc, PrimaryKey
   *  @param title Database column title SqlType(varchar), Length(200,true)
   *  @param text Database column text SqlType(text) */
  case class FilesRow(fileId: Int, title: String, text: String)
  /** GetResult implicit for fetching FilesRow objects using plain SQL queries */
  implicit def GetResultFilesRow(implicit e0: GR[Int], e1: GR[String]): GR[FilesRow] = GR{
    prs => import prs._
    FilesRow.tupled((<<[Int], <<[String], <<[String]))
  }
  /** Table description of table files. Objects of this class serve as prototypes for rows in queries. */
  class Files(_tableTag: Tag) extends profile.api.Table[FilesRow](_tableTag, "files") {
    def * = (fileId, title, text).<>(FilesRow.tupled, FilesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(fileId), Rep.Some(title), Rep.Some(text))).shaped.<>({r=>import r._; _1.map(_=> FilesRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column file_id SqlType(serial), AutoInc, PrimaryKey */
    val fileId: Rep[Int] = column[Int]("file_id", O.AutoInc, O.PrimaryKey)
    /** Database column title SqlType(varchar), Length(200,true) */
    val title: Rep[String] = column[String]("title", O.Length(200,varying=true))
    /** Database column text SqlType(text) */
    val text: Rep[String] = column[String]("text")
  }
  /** Collection-like TableQuery object for table Files */
  lazy val Files = new TableQuery(tag => new Files(tag))
}
