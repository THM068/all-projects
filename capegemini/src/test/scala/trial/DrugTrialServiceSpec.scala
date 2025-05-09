package trial

import com.capegemini.trial.{ControlGroup, DrugTrialService, Placebo}
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

 class DrugTrialServiceTest extends AnyFunSuite with BeforeAndAfter with Matchers {
   val drugTrialService = new DrugTrialService()


  test("selectGroup return Placebo group for a name with odd number of characters when ignoring the vowels")  {
    val name = "Anderson"

    val result = drugTrialService.selectGroup(name)

    result shouldBe Placebo
  }

 test("return control group for name that does not contain any wowels")  {
    val name = "Glynn"

    val result = drugTrialService.selectGroup(name)

    result shouldBe ControlGroup
  }
}

trait Test {
  val drugTrialService = new DrugTrialService()
}



//Drug trials
//  A pharmaceuticals company is conducting trials on a new drug. They select trial drug, placebo, and control group participants based on the following rules applied to their last name.
//- Ignoring vowels, if their name consists of an odd number of characters, they receive the placebo.
//- If their name contains no vowels, then they are part of the control group receiving no medication at all.
//  - Everyone else receives the trial drug.
//
//Create a method to determine if the participant receives the trial drug, placebo or is part of the control group.
//
//
//Anderson
//Glynn
//Helens

