ThisBuild / scalaVersion := "2.13.10"

ThisBuild / version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
	.enablePlugins(PlayScala)
	.settings(
		name := """WebTextEditor""",
		libraryDependencies ++= Seq(
			guice,
			"org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
			"com.typesafe.play" %% "play-slick" % "5.1.0",		//db binding
			"com.typesafe.slick" %% "slick-codegen" % "3.4.1",	//generate code from db to our project
			"com.typesafe.play" %% "play-json" % "2.9.3",
			"org.postgresql" % "postgresql" % "42.5.0",			//db driver we gonna use
			"com.typesafe.slick" %% "slick-hikaricp" % "3.4.1"	//manager for db connections associated with slick
			//"org.mindrot" % "jbcrypt" % "0.4"					//java lib to hash passwords
		)
	)