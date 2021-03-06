package sfusion
package compiler

import java.io.File

import squid.utils._
import squid.ir._
import squid.lang._

/**
  * Created by lptk on 08/02/17.
  */
class TestCompiler extends Compiler {
  
  val debug = false
  //val debug = true
  
  override def dumpPhase(name: String, mkCode: => String, time: Long) = {
    
    val p = curPrinter.get
    p.println(s"\n// === $name ===\n")
    if (debug) println(s"\n// === $name ===\n")
    val t0 = System.nanoTime()
    if (debug) println("--- PRINTING")
    val str = mkCode
    val t1 = System.nanoTime()
    val M = 1000*1000
    p.println(s"// Transfo time: ${time/M}ms  Stringifying time: ${(t1-t0)/M}ms\n")
    if (str == SAME) p.println("// Same as above.")
    else p.println(str)
    if (debug) println(s"$str\n--- DONE")
    
  }
  
  var curPrinter = Option.empty[java.io.PrintWriter]
  override def wrapOptim[A](id: String)(code: => A) = {
    //println(id)
    import File.{separator => SEP}
    val file = new File(s"example_gen${SEP}test${SEP}sfusion${SEP}$id.scala")
    val p = new java.io.PrintWriter(file)
    curPrinter = Some(p)
    p.println("// Automatically-generated code")
    try super.wrapOptim(id)(code) finally {
      p.close()
      curPrinter = None
    }
  }
  
  
}

