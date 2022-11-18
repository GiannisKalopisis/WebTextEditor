package models

object CodeGen extends App {
	slick.codegen.SourceCodeGenerator.run(
		"slick.jdbc.PostgresProfile",
		"org.postgresql.Driver",
		"jdbc:postgresql://localhost:5432/texteditordb?user=texteditormanager&password=root",
		"/home/gianniskalopisis/Desktop/Raw/WebTextEditor/app/",
		"models", None, None, true, false
	)
	// None-1 == user
	// None-2 == password
}
