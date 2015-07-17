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
    * @param values the values for which the Theil index must be computed
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
    * @param values the values for which the Atkinson index must be computed
    * @param parameter a coefficient expressing the "inequality aversion". If 0, no aversion to inequality is considered,
    *                  and the index equals to 0.
    *                  As the coefficient value increases, the social utility gained by a total redistribution increases.
    * @return the Atkinson index associated to the given values
    */
   def atkinson(values: Iterable[Double], parameter: Double = 0.5): Double =
   {
      val m = mean(values)

      if(parameter == 1)
         1.0 - (1.0/m) * math.pow(values.product , (1.0 / values.size))
      else
         1.0 - (1.0/m) * math.pow( values.map(y => math.pow(y, 1.0 - parameter)).sum / values.size, (1.0 / (1.0 - parameter)))
   }

   /**
    * Computes the generalized entropy index for some given values.
    * https://en.wikipedia.org/wiki/Generalized_entropy_index
    * @param values the values for which the generalized entropy index must be computed
    * @param alpha the weight given to distances between incomes at different parts of the income distribution.
    * @return the generalized entropy index of the given values
    */
   def entropy(values: Iterable[Double], alpha: Double = 0.5): Double =
   {
      val m = mean(values)

      alpha match
      {
         case 0.0 => -(1.0 / values.size) * values.map(y => math.log(y / m)).sum
         case 1.0 => values.map(y => (y / m) * math.log(y / m)).sum / values.size
         case _   => values.map(y => math.pow((y / m), alpha) - 1).sum / (values.size * alpha * (alpha - 1))
      }
   }

   /**
    * Computes the coefficient of variation, which is defined as the ratio of the standard deviation to the mean.
    * https://en.wikipedia.org/wiki/Coefficient_of_variation
    * @param values the values for which the coefficient of variation must be computed
    * @return the coefficient of variation for the given values
    */
   def var_coef(values: Iterable[Double]): Double =
   {
      val m = mean(values)
      val variance = mean(values.map(y => (y-m)*(y-m)))
      val sd = math.sqrt(variance)
      sd / m
   }

   /**
    * Computes the squared coefficient of variation.
    * @param values the values for which the squared coefficient of variation must be computed
    * @return the squared coefficient of variation for the given values
    */
   def squared_var_coef(values: Iterable[Double]): Double = {
      val vc = var_coef(values)
      vc * vc
   }

   /**
    * Computes the Hoover index, also known as the Pietra index or the Schutz index
    * @param values the values for which the Hoover index must be computed
    * @return the Hoover index of the given values
    */
   def hoover(values: Iterable[Double]): Double =
   {
      val relative_earners = 1.0 / values.size
      val total = values.sum

      values.map(y => math.abs((y/total) - relative_earners)).sum / 2.0
   }

   /**
    * Computes the Kolm index
    * https://de.wikipedia.org/wiki/Kolm-Index
    * @param values the values for which the Kolm index must be computed
    * @param k the inequality aversion, must be strictly greater than 0
    * @return the Kolm index of the given values
    */
   def kolm(values: Iterable[Double], k: Double = 1): Double =
   {
      math.log(mean(values.map(y => math.exp(k*(mean(values) - y))))) / k
   }

   /**
    * Computes the Herfindahl concentration index within values according to the specified concentration measure.
    * https://en.wikipedia.org/wiki/Herfindahl_index
    *
    * - A result below 0.01 indicates a highly competitive index.
    * - A result below 0.15 indicates an unconcentrated index.
    * - A result between 0.15 to 0.25 indicates moderate concentration.
    * - A result above 0.25 indicates high concentration.
    *
    * @param values     the values for which the Herfindahl index must be computed
    * @param parameter  parameter of the concentration
    * @return the Herfindahl concentration index for the given values, a value between 1/N and 1, where N is the size of _values_
    */
   def herfindahl(values: Iterable[Double], parameter: Double = 1): Double =
   {
      // TODO: On CRAN ineq, the result is powered by (1/parameter). Why?
      values.map(y => math.pow((y / values.sum), parameter+1)).sum
   }

   /**
    * Computes the normalized Herfindahl concentration index within values according to the specified concetration measure.
    *
    * @param values     the values for which the Herfindahl index must be computed
    * @param parameter  parameter of the concentration
    * @return           the normalized Herfindahl concentration index for the given values, a value between 0 and 1
    */
   def normalized_herfindahl(values: Iterable[Double], parameter: Double = 1): Double =
   {
      values.size match
      {
         case 1 => 1
         case _ => (herfindahl(values, parameter) - (1.0/values.size)) / (1.0 - 1.0 / values.size)
      }
   }



   private def mean(values: Iterable[Double]): Double = values.sum / values.size
}
