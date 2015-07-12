package org.scalar


package object ineq
{
   /**
    * Computes the Gini index of values
    * @param values the values for which the Gini index must be computed
    * @param unbiased true if the data values are unbiased, and should therefore not be corrected; false otherwise
    * @return the Gini index associated to the given values
    */
   def gini(values: Iterable[Double], unbiased: Boolean = true): Double =
   {
      val n = values.size.toDouble
      val ox = values.toSeq.sorted

      val oxi = ox.zipWithIndex.map(e => (e._1, e._2 + 1))
      println(oxi)

      val num = 2 * oxi.map(e => e._1 * e._2).sum
      println("num : " + num)
      val den = n * ox.sum
      println("denum : " + den)

      val g = (num / den) - ((n+1) / n)

      if(!unbiased) g * n / (n-1)
      else g
   }

   def theil(values: Iterable[Double]): Double =
   {

   }
}
