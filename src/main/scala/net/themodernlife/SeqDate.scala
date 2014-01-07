package net.themodernlife

import org.joda.time.{DateTimeZone, DateTime}
import org.joda.time.format.{DateTimeFormat, ISODateTimeFormat}
import org.rogach.scallop.ScallopConf


trait StepType
case object Minutes extends StepType
case object Hours extends StepType
case object Days extends StepType
case object Months extends StepType
case object Years extends StepType

class Conf(args: List[String]) extends ScallopConf(args) {
  val inputFormat  = opt[String]("in-format", 'i') map(DateTimeFormat.forPattern(_)) orElse Some(ISODateTimeFormat.date)
  val outputFormat = opt[String]("out-format", 'o') map(DateTimeFormat.forPattern(_)) orElse Some(ISODateTimeFormat.date)
  val timeZone     = opt[String]("time-zone", 'z') map(DateTimeZone.forID(_)) orElse Some(DateTimeZone.UTC)
  val startDate    = trailArg[String]("start-date").flatMap(sd => inputFormat.map(DateTime.parse(sd, _)))
  val endDate      = trailArg[String]("end-date").flatMap(ed => inputFormat.map(DateTime.parse(ed, _)))
  val stepAmount   = trailArg[Int]("step", default = Some(1))
  val stepType     = trailArg[String]("interval", default = Some("days")) map {
    case "minutes" => Minutes
    case "hours"   => Hours
    case "days"    => Days
    case "months"  => Months
    case "years"   => Years
  }
}

object SeqDate extends App {
  val conf = new Conf(args.toList)

  def loop(cur: DateTime, end: DateTime, step: Int, stepType: StepType, data: List[DateTime] = Nil): List[DateTime] = {
    if (!cur.isBefore(end)) {
      data.reverse
    } else {
      val next = stepType match {
        case Minutes => cur.plusMinutes(step)
        case Hours   => cur.plusHours(step)
        case Days    => cur.plusDays(step)
        case Months  => cur.plusMonths(step)
        case Years   => cur.plusYears(step)
      }
      loop(next, end, step, stepType, next :: data)
    }
  }

  val dates = loop(conf.startDate(), conf.endDate(), conf.stepAmount(), conf.stepType())

  dates.foreach { d =>
    conf.outputFormat().printTo(System.out, d)
    println
  }
}
