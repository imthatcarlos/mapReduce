// Project name (artifact name in Maven)
name := "JAVA-MVC-Swing-Monopoly"

// orgnization name (e.g., the package name of the project)
organization := "com.carlos_beltran_hw1"

version := "1.0"

// Do not append Scala versions to the generated artifacts
crossPaths := false

// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false

// library dependencies. (orginization name) % (project name) % (version)
libraryDependencies ++= Seq(
  "org.apache.commons" % "commons-math3" % "3.1.1",
  "org.fluentd" % "fluent-logger" % "0.2.10",
  "org.mockito" % "mockito-core" % "1.9.5" % "test",
  "junit" % "junit" % "4.12" % "test",
  "com.novocode" % "junit-interface" % "0.11" % "test",
)

javaSource in Compile := baseDirectory.value / "src"

javaSource in Test := baseDirectory.value / "src/tests"
