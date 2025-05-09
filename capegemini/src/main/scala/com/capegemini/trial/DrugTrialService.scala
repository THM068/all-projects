package com.capegemini.trial

class DrugTrialService() {

  def selectGroup(name: String): Group = {
    name match {
      case containNoVowels() => ControlGroup
      case hasOddNumLetter() => Placebo
      case _ => TrialDrug
    }
  }

}

sealed trait  Group

case object TrialDrug extends Group
case object Placebo extends Group
case object ControlGroup extends Group

object hasOddNumLetter {
  def unapply(name: String): Boolean = {
    val listOfVowels = List('a','e','i','o','u')
    val nameWithOutVowels = name.filterNot( ch => listOfVowels.contains(ch.isLetter) )

    nameWithOutVowels.size % 2 != 0
  }
}

object containNoVowels {
  def unapply(name: String): Boolean = {
    val listOfVowels = List('a','e','i','o','u')

    name.forall( ch => !listOfVowels.contains(ch.toLower.toLower) )
  }
}

//name match {
//  case hasOddNumLetter() => Placebo
//    casse hasNoVowels() => ControlGroup
//  case _ => trialDrug
//}