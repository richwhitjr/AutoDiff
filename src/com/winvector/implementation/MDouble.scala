package com.winvector.implementation

/**
 * Copyright 2010 John Mount / Win-Vector LLC
 * Released under GNUv3 GPLv3 License (see http://www.gnu.org/licenses/gpl.html)
 * For details/instructions see "Automatic Differentiation with Scala" from www.win-vector.com
 */

import com.winvector.definition.NumberBase
import com.winvector.definition.Field

/**
 * our reference/adaption implementation (used for testing)
 * @author johnmount
 *
 */
final object FDouble extends Field[MDouble] {
  private val z = new MDouble(0.0)
  private val o = new MDouble(1.0)
  private val na = new MDouble(Double.NaN)
  def zero= z
  def one = o
  def nan = na
  def inject(v:Double) = {
    v match {
      case 0.0 => z
      case 1.0 => o
      case _ => new MDouble(v)
    }
  }
  override def toString = "FDouble"
  def array(n:Int) = { 
	  val a = new Array[MDouble](n)
	  for(i <- 0 until n) {
	 	  a(i) = zero
	  }
	  a
  }
  def representationNorm(v:MDouble):Double = { scala.math.abs(v.v) }
}


final class MDouble private[implementation] (private[implementation] val v:Double) extends NumberBase[MDouble] {    
  // basic arithmetic
  def + (that: MDouble) = new MDouble(v + that.v)
  def - (that: MDouble) = new MDouble(v - that.v)
  def * (that: MDouble) = new MDouble(v * that.v)
  def / (that: MDouble) = {
    if(that.v==0.0) {
       FDouble.nan
    } else {
       new MDouble(v / that.v)
    }
  }

  def project = v

  // more complicated
  def pospow(pw:Double) = {
    if(v<0.0) {
      FDouble.nan
    } else {
      if(pw==0.0) {
        FDouble.one
      } else {
        new MDouble(scala.math.pow(v,pw))
      }
    }
  }
  
  def exp = new MDouble(scala.math.exp(v))
  
  def log = {
    if(v<=0.0) {
      FDouble.nan
    } else {
    	new MDouble(scala.math.log(v))
    }
  }
  def sqrt = new MDouble(scala.math.sqrt(v))

  // utility
  def field:Field[MDouble] = FDouble
  override def toString = "%g" format v
  
  // more special fns
  def sin = new MDouble(scala.math.sin(v))
  def cos = new MDouble(scala.math.cos(v))
  def tan = new MDouble(scala.math.tan(v))
  def asin = new MDouble(scala.math.asin(v))
  def acos = new MDouble(scala.math.acos(v))
  def atan = new MDouble(scala.math.atan(v))
  def sinh = new MDouble(scala.math.sinh(v))
  def cosh = new MDouble(scala.math.cosh(v))
  def tanh = new MDouble(scala.math.tanh(v))
}

