package su.eterra.liftl10ncheck

import sbt._
import Keys._
import scala.util.matching.Regex

object LiftL10n extends Plugin {

  val locIdFormat = SettingKey[Option[Regex]]("locIdFormat")

  override lazy val settings = Seq(
    locIdFormat := None
  ) ++ Seq(checkTask)


  lazy val checkKey = TaskKey[Unit]("l10ncheck", "checks if all found loc ids are present in resourses")

  val checkTask = checkKey <<= (streams, locIdFormat, baseDirectory) map { (s: TaskStreams, format: Option[Regex], path: File) =>
    val logger = s.log
    logger.info("liftl10n 0.0.3-SNAPSHOT")
    logger.info("Format is " + format)
    logger.info("Path is " + path.getCanonicalPath)
    LocChecker.checkLoc(logger, path, format)
  }


}
