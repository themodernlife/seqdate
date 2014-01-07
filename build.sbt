

name := "seqdate"

version := "0.1"

organization := "net.themodernlife"

scalaVersion := "2.10.2"

description := "Outputs a series of dates"





scalacOptions ++= Seq("-encoding", "utf-8", "-deprecation", "-unchecked", "-feature")






libraryDependencies += "joda-time"          % "joda-time"          % "2.1"

libraryDependencies += "org.joda"           % "joda-convert"       % "1.2"


libraryDependencies += "org.rogach" %% "scallop" % "0.9.4"

