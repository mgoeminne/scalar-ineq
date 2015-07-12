import org.scalar.ineq
import org.scalatest._

class IneqTest extends FlatSpec with Matchers
{
   private val EPSILON = 0.000001

   "The Gini index of a single value" should "be 0" in {
      ineq.gini(Seq(42.0)) shouldBe (0.0) +- EPSILON
   }

   "The vector given in R doc" should "have the right Gini index" in{
      val x = Seq(541, 1463, 2445, 3438, 4437, 5401, 6392, 8304, 11904, 22261).map(_.toDouble)

      ineq.gini(x) shouldBe (0.4620911) +- EPSILON

   }

   it should "have the right corrected Gini index" in{
      val x = Seq(541, 1463, 2445, 3438, 4437, 5401, 6392, 8304, 11904, 22261).map(_.toDouble)
      ineq.gini(x, false) shouldBe (0.5134346) +- EPSILON
   }



}
