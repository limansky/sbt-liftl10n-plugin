package su.eterra

import sbt._
import Keys._
import java.io.File
import scala.util.matching.Regex
import scala.io.Source
import scala.collection.immutable.TreeMap
import scala.collection.mutable.MultiMap
import scala.collection.mutable.HashMap
import scala.collection.mutable.Set
import scala.collection.mutable.HashSet


object L10nPlugin extends Plugin {

  override lazy val settings = Seq(commands += l10nCheckCommand)

  lazy val l10nCheckCommand = Command.command("l10ncheck")(doL10nCheck)

  case class LocItem(name: String, file: String, line: Int)

  def doL10nCheck(state: State) = {
    val logger = state.globalLogging.full
    val path = state.configuration.baseDirectory

    var locs: TreeMap[String, List[LocItem]] = TreeMap.empty
    processFiles(path, "\\.html$".r)(processFile("""(?iu)locid="([^"]+)"""".r)).foreach(it => {
      val cur = locs.getOrElse(it.name, Nil)
      locs = locs + ((it.name, it :: cur))
    })

    processFiles(path, "\\.scala$".r)(processFile("""S\.\?\??\("([^"]+)"\)""".r)).foreach(it => {
      val cur = locs.getOrElse(it.name, Nil)
      locs = locs + ((it.name, it :: cur))
    })

    val ress = new HashMap[String, Set[String]] with MultiMap[String, String]
    val transPath = new File("src/main/webapp/resources-hidden")
    val allLangs = new HashSet[String]
    processFiles(transPath, "^_resources_.*\\.html$".r)(processFile("""res name="([^"]+)"""".r)).foreach(it => {
      ress.addBinding(it.name, it.file)
      allLangs += it.file
    })

    val locFormat = """^[a-z][A-Za-z]*(\.[a-z][A-Za-z]*)+""".r

    def fileList(list: Seq[LocItem]) = list.map(l => l.file + ":" + l.line).mkString(", ")

    var errors = 0
    var warns = 0

    locs.foreach(loc => {
      ress.get(loc._1) match {
        case None => {
          logger.error(""""%s" not Found (%s)""".format(loc._1, fileList(loc._2)))
          errors += 1
        }
        case Some(langs) => {
          if (langs != allLangs) {
            logger.error(""""%s" missed localizations: (%s) (%s)""".format(loc._1, (allLangs &~ langs).mkString(" "), fileList(loc._2)))
            errors += 1
          }
        }
      }
      if (locFormat.findFirstIn(loc._1).isEmpty) {
        logger.warn("""Wrong loc ID format: "%s" (%s)""".format(loc._1, fileList(loc._2)))
        warns += 1
      }
    })

    val msg = "%d strings found. %d errors, %d warnings.".format(locs.size, errors, warns)
    if (0 != errors)
      sys.error("Errors were found. " + msg)
    else
      logger.success(msg)

    state
  }

  private def processFile(r: Regex)(f: File) = {
    val s = for {
      l <- Source.fromFile(f).getLines.zipWithIndex
      m <- r.findAllIn(l._1).matchData
    } yield LocItem(m.group(1), f.getName, l._2 + 1)
    s.toList
  }

  private def processFiles[T](file: File, r: Regex)(fun: (File => List[T])) : List[T] = {
    val (dirs, files) = file.listFiles.toList.partition(_.isDirectory)

    files.withFilter(f => r.findFirstIn(f.getName).isDefined).flatMap(fun(_)) :::
      dirs.flatMap(d => processFiles(d, r)(fun))
  }

}
