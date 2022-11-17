package models

object CodeGen extends App {
	slick.codegen.SourceCodeGenerator.run(
		"slick.jdbc.PostgresProfile",
		"org.postgresql.Driver",
		"jdbc:postgresql://localhost/texteditordb?user=texteditormanager&password=password",
		"app/",
		"models", None, None, ignoreInvalidDefaults = true, outputToMultipleFiles = false
	)
	// None-1 == user
	// None-2 == password
}
