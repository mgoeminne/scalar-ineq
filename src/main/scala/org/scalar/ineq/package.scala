package org.scalar


package object ineq
{
   /**
    * Computes the Gini index of given values.
    * https://en.wikipedia.org/wiki/Gini_coefficient
    * @param values the values for which the Gini index must be computed
    * @param unbiased true if the data values are unbiased, and should therefore not be corrected; false otherwise
    * @return the Gini index associated to the given values
    */
   def gini(values: Iterable[Double], unbiased: Boolean = true): Double =
   {
      val n = values.size.toDouble
      val ox = values.toSeq.sorted

      val oxi = ox.zipWithIndex.map(e => (e._1, e._2 + 1))

      val num = 2 * oxi.map(e => e._1 * e._2).sum
      val den = n * ox.sum

      val g = (num / den) - ((n+1) / n)

      if(!unbiased) g * n / (n-1)
      else g
   }

   /**
    * Computes the Theil index for some given values.
    * https://en.wikipedia.org/wiki/Theil_index
    * @param values the values for which the Gini index must be computed
    * @return the Theil index associated to the given values
    */
   def theil(values: Iterable[Double]): Double =
   {
      val mean = values.sum / values.size
      values.map(y => y/mean * Math.log(y/mean)).sum / values.size
   }

   /**
    * Computes the normalized Theil index.
    * @param values the values for which the Gini index must be computed
    * @return the normalized Theil index associated to the given values, a value between 0 and 1
    */
   def normalized_theil(values: Iterable[Double]): Double = theil(values) / math.log(values.size)



   /**
    * Computes the Atkinson's index for some given values.
    * https://en.wikipedia.org/wiki/Atkinson_index
    * @param values the values for which the Gini index must be computed
    * @param parameter a coefficient expressing the "inequality aversion". If 0, no aversion to inequality is considered,
    *                  and the index equals to 0.
    *                  As the coefficient value increases, the social utility gained by a total redistribution increases.
    * @return the Atkinson index associated to the given values
    */
   def atkinson(values: Iterable[Double], parameter: Double = 0.5): Double =
   {
      val mean = values.sum / values.size

      if(parameter == 1)
         1.0 - (1.0/mean) * math.pow(values.product , (1.0 / values.size))
      else
         1.0 - (1.0/mean) * math.pow( values.map(y => math.pow(y, 1.0 - parameter)).sum / values.size, (1.0 / (1.0 - parameter)))
   }

   /**
    * Computes the generalized entropy index for some given values.
    * https://en.wikipedia.org/wiki/Generalized_entropy_index
    * @param values the values for which the Gini index must be computed
    * @param alpha the weight given to distances between incomes at different parts of the income distribution.
    * @return the generalized entropy index of the given values
    */
   def entropy(values: Iterable[Double], alpha: Double = 0.5): Double =
   {
      val mean = values.sum / values.size

      alpha match
      {
         case 0.0 => -(1.0 / values.size) * values.map(y => math.log(y / mean)).sum
         case 1.0 => values.map(y => (y / mean) * math.log(y / mean)).sum / values.size
         case _   => values.map(y => math.pow((y / mean), alpha) - 1).sum / (values.size * alpha * (alpha - 1))
      }
   }
}
