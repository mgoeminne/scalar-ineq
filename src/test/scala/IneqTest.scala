import org.scalar.ineq
import org.scalatest._

import scala.util.Random

class IneqTest extends FlatSpec with Matchers
{
   private val EPSILON = 0.000001
   val CRAN_VECTOR = Seq(541, 1463, 2445, 3438, 4437, 5401, 6392, 8304, 11904, 22261).map(_.toDouble)

   "Gini index of a single value" should "be 0" in {
      ineq.gini(Seq(42.0)) shouldBe (0.0) +- EPSILON
   }

   "The vector given in R doc" should "have the right Gini index" in{

      ineq.gini(CRAN_VECTOR) shouldBe (0.4620911) +- EPSILON

   }

   it should "have the right corrected Gini index" in{
      val x = Seq(541, 1463, 2445, 3438, 4437, 5401, 6392, 8304, 11904, 22261).map(_.toDouble)
      ineq.gini(x, false) shouldBe (0.5134346) +- EPSILON
   }

   it should "have the right Theil index" in {
      ineq.theil(CRAN_VECTOR) shouldBe (0.3623412) +- EPSILON
   }

   it should "have the right Atkinson index" in {
      ineq.atkinson(CRAN_VECTOR) shouldBe (0.1796591) +- EPSILON
      ineq.atkinson(CRAN_VECTOR, 1) shouldBe (0.3518251) +- EPSILON
      ineq.atkinson(CRAN_VECTOR, 0.4) shouldBe (0.1440938) +- EPSILON
   }

   it should "have the right generalized entropy index" in {
      ineq.entropy(CRAN_VECTOR, 0.5) shouldBe (0.3770931) +- EPSILON
      ineq.entropy(CRAN_VECTOR, 1) shouldBe (0.3623412) +- EPSILON
      ineq.entropy(CRAN_VECTOR, 0) shouldBe (0.4335946) +- EPSILON
      ineq.entropy(CRAN_VECTOR, 0.723) shouldBe (0.3663712) +- EPSILON
   }

   it should "have the right coefficient of variation" in {
      ineq.var_coef(CRAN_VECTOR) shouldBe (0.9169582) +- EPSILON
   }

   it should "have the right squared coefficient of variation" in {
      ineq.squared_var_coef(CRAN_VECTOR) shouldBe (0.8408123) +- EPSILON
   }

   it should "have the right Hoover index" in {
      ineq.hoover(CRAN_VECTOR) shouldBe (0.3378067) +- EPSILON
   }

   it should "have the right Kolm index" in {
      ineq.kolm(CRAN_VECTOR,1) shouldBe Double.PositiveInfinity
   }

   "Atkinson index, with a parameter of 0" should "be 0" in {
      ineq.atkinson(CRAN_VECTOR, 0.0) shouldBe (0.0) +- EPSILON
      ineq.atkinson(Seq.fill(50)(Random.nextDouble()), 0.0) shouldBe (0.0) +- EPSILON
   }

   "Normalized Theil index" should "be between 0 and 1" in {
      assert( ineq.normalized_theil(CRAN_VECTOR) >= 0)
      assert( ineq.normalized_theil(CRAN_VECTOR) <= 1)
   }






}
