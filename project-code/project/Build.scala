import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "play20-social"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
      "com.google.gdata" % "gdata-analytics-2.1" % "1.41.5",
      "com.google.gdata" % "gdata-analytics-meta-2.1" % "1.41.1",
      "com.google.gdata" % "gdata-base-1.0" % "1.41.5",
      "com.google.gdata" % "gdata-appsforyourdomain-1.0" % "1.41.1",
      "com.google.gdata" % "gdata-appsforyourdomain-meta-1.0" % "1.41.1",
      "com.google.gdata" % "gdata-blogger-2.0" % "1.41.5",
      "com.google.gdata" % "gdata-blogger-meta-2.0" % "1.41.1",
      "com.google.gdata" % "gdata-books-1.0" % "1.41.5",
      "com.google.gdata" % "gdata-books-meta-1.0" % "1.41.1",
      "com.google.gdata" % "gdata-calendar-2.0" % "1.41.5",
      "com.google.gdata" % "gdata-calendar-meta-2.0" % "1.41.1",
      "com.google.gdata" % "gdata-client-1.0" % "1.40.3",
      "com.google.gdata" % "gdata-client-meta-1.0" % "1.41.1",
      "com.google.gdata" % "gdata-codesearch-2.0" % "1.41.5",
      "com.google.gdata" % "gdata-codesearch-meta-2.0" % "1.41.1",
      "com.google.gdata" % "gdata-contacts-3.0" % "1.41.5",
      "com.google.gdata" % "gdata-contacts-meta-3.0" % "1.41.1",
      "com.google.gdata" % "gdata-core-1.0" % "1.41.5",
      "com.google.gdata" % "gdata-docs-3.0" % "1.40.1",
      "com.google.gdata" % "gdata-docs-meta-3.0" % "1.41.1",
      "com.google.gdata" % "gdata-finance-2.0" % "1.41.5",
      "com.google.gdata" % "gdata-finance-meta-2.0" % "1.41.1",
      "com.google.gdata" % "gdata-gtt-2.0" % "1.41.5",
      "com.google.gdata" % "gdata-gtt-meta-2.0" % "1.41.1",
      "com.google.gdata" % "gdata-health-2.0" % "1.41.5",
      "com.google.gdata" % "gdata-health-meta-2.0" % "1.41.1",
      "com.google.gdata" % "gdata-maps-2.0" % "1.41.5",
      "com.google.gdata" % "gdata-maps-meta-2.0" % "1.41.1",
      "com.google.gdata" % "gdata-media-1.0" % "1.41.5",
      "com.google.gdata" % "gdata-photos-2.0" % "1.41.5",
      "com.google.gdata" % "gdata-photos-meta-2.0" % "1.41.1",
      "com.google.gdata" % "gdata-projecthosting-2.1" % "1.41.5",
      "com.google.gdata" % "gdata-projecthosting-meta-2.1" % "1.41.1",
      "com.google.gdata" % "gdata-sidewiki-2.0" % "1.41.5",
      "com.google.gdata" % "gdata-sidewiki-meta-2.0" % "1.41.1",
      "com.google.gdata" % "gdata-sites-2.0" % "1.41.5",
      "com.google.gdata" % "gdata-sites-meta-2.0" % "1.41.1",
      "com.google.gdata" % "gdata-spreadsheet-3.0" % "1.41.5",
      "com.google.gdata" % "gdata-spreadsheet-meta-3.0" % "1.41.1",
      "com.google.gdata" % "gdata-webmastertools-2.0" % "1.41.5",
      "com.google.gdata" % "gdata-webmastertools-meta-2.0" % "1.41.1",
      "com.google.gdata" % "gdata-youtube-2.0" % "1.41.5",
      "com.google.gdata" % "gdata-youtube-meta-2.0" % "1.41.1",
      "com.google.collections" % "google-collections" % "1.0",
      "com.restfb" % "restfb" % "1.6.11",
      "org.twitter4j" % "twitter4j-core" % "[2.2,)"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      resolvers += "googledata" at "http://mandubian-mvn.googlecode.com/svn/trunk/mandubian-mvn/repository"
    )

}
