package scp
package scback
package example

import scp.utils._
import ch.epfl.data.sc._
import pardis._
import deep.scalalib._
import deep.scalalib.collection._
import _root_.scp.ir2.SimpleANF

import scala.collection.mutable.ArrayBuffer

/**
  * Created by lptk on 08/11/16.
  */
object SquidFrontSCBack extends App {
  /*_*/
  
  object Frontend extends SimpleANF
  
  object SC extends ir.Base with ScalaCoreOps { type `ignore java.lang.String.<init>` } // TODO put in accessible helpers
  object Backend extends AutoboundPardisIR(SC)
  Backend.ab = AutoBinder(SC, Backend)  // this is going to generate a big binding structure; put it in a separate file so it's not always recomputed and recompiled!
  
  import Frontend.Predef._
  
  val pgrm = ir"val a = ArrayBuffer(1,2,3); a append 1; a.size"
  
  println(pgrm)
  println(pgrm.run)
  
  println(pgrm reinterpretIn Backend)
  
}
