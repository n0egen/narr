# NArr
pronounced: <b>(ˈnär, as in gnarly)</b> stands for: <b>Native Array</b><br />

&nbsp;&nbsp;&nbsp;This library (now hosted on the Apache Maven Central Repository) provides Scala.js cross projects with an abstraction over features common to scala.Array, as well as js.Array and the most relevant subset of the js.typedarray family: Int8Array, Int16Array, Int32Array, Float32Array, Float64Array.  It also includes TypeClasses and extension methods to polyfill native JavaScript Arrays with features like: indices, tabulate and fill.  Using NArray[T] ensures that a project will always use the native Array type of the platform it compiles to.  <a href="https://dragonfly-ai.github.io/narr/">Try the demo</a>!

<b>Advantages</b>:<br />
<ul>
<li>Performance!<br />&nbsp;&nbsp;&nbsp;When a Scala.js cross project uses the NArray type internally it implicitly takes advantage of each platform's most optimized data structures.  While JVM Arrays inherit the speed and compactness of C/C++ style Arrays, JavaScript Arrays are a special case of JavaScript's Object type: the only data structure it has.  Likewise, making use of native Array types eliminates all performance penalties associated with conversions and wrappers at the boundary between a Scala.js library and native code that makes use of it.</li>
<li>Native Interoperability without Boilerplate<br />&nbsp;&nbsp;When writing a cross compiled Scala.js library with accessibility from native languages, developers have to pay attention to @JSExport annotations and make sure that every method or field of type Array has an analagous js.Array exposed in the JavaScript build.  By simply replacing all references to js.Array or Array with NArray, the same methods and fields will interoperate seamlessly with code in either run time environment.</li>
</ul>

<b>Caveats</b>:<br />
&nbsp;&nbsp;&nbsp;Although the TypedArray family of data structures avoids the following issues, they pertain to the more ubiquitous js.Array.
<ul>
<li>Type Safety concerns<br />&nbsp;&nbsp;&nbsp;Because the JVM Array has a reference to the type of its elements and the JavaScript Array has no concept of type at all, careless or malicious JavaScript developers could spike a js.Array with unexpected or dangerous values before passing it to an unsuspecting Scala.js method that takes parameters of type NArray[T].  Security concerns aside, if abused, these type system distinctions could cause unwanted run time differences between the behavior of JVM and JavaScript versions of a program.  However, using js.Array[T] explicitly doesn't solve these issues, either.  These concerns argue against js.Array in general as much as they argue against NArray.
</li>
<li>Size Inconsistency and Mutability Concerns.<br />&nbsp;&nbsp;&nbsp;The JVM Array has a fixed, immutable length while the length of a JavaScript Array can change dynamically.  To mitigate all of the usual dangers involved with exposure to this kind of shared mutable state, users of NArr should treat all instances of NArray as JVM arrays.  More specifically, restrict all interaction with instances of NArray[T] to only three operations:<br />

```scala
// assume: val a:NArray[String]

a(0)              // read element
a(0) = "Gnarly!"  // write element
a.length          // read length
```
</li>
<li>

Output from `toString()` differs by platform.  For example:

```scala
println(new NArray[Int](3))
```

yields different results depending on the platform on which it runs. 
<ul>
<li>

Scala.js prints the contents of the array: `0,0,0`

</li>
<li>

On the JVM, it prints something like: `[I@176c3251`

</li>
<li>

In Scala Native, it prints something like: `scala.scalanative.runtime.IntArray@24da9104`

</li>
</ul>

</li>
</ul>

<b>When to use NArr</b>:
<table>
<tr>
    <td><b>Array Dependency</b></td>    
    <td><b>Convenience</b></td>
    <td colspan="3"><b>Performance Increase</b></td>
    <td><b>Notes</b></td>
</tr>
<tr>
    <td colspan="2"></td>
    <td><b>JS</b></td>
    <td><b>JVM</b></td>
    <td><b>Native</b></td>
    <td></td>
</tr>
<tr>
    <td>No Arrays</td>
    <td>☆☆☆</td>
    <td>☆☆☆</td>
    <td>☆☆☆</td>
    <td>☆☆☆</td>
    <td>NArr offers no utility for projects that do not use Array[T] types.</td>
</tr>
<tr>
    <td>js.Array[T]</td>
    <td>☆☆☆</td>
    <td>☆☆☆</td>
    <td>☆☆☆</td>
    <td>☆☆☆</td>
    <td>js.Array[T] will suffice</td>
</tr>
<tr>
    <td>Array[T]</td>
    <td>☆☆☆</td>
    <td>★☆☆</td>
    <td>☆☆☆</td>
    <td>☆☆☆</td>
    <td>Array[T] might not perform as optimally as the native js.Array[T] in JavaScript environments.</td>
</tr>
<tr>
    <td>js.Array[T] and Array[T]</td>
    <td>★★★</td>
    <td>★★★</td>
    <td>☆☆☆</td>
    <td>☆☆☆</td>
    <td>Seamless optimized interop with native code on both platforms without any conversions or wrappers.</td>
</tr>
<tr>
    <td>Int8Array or Array[Byte]</td>
    <td>★★★</td>
    <td>★★★</td>
    <td>☆☆☆</td>
    <td>☆☆☆</td>
    <td>Seamless optimized interop with native code on both platforms without any conversions or wrappers.</td>
</tr>
<tr>
    <td>Int16Array or Array[Short]</td>
    <td>★★★</td>
    <td>★★★</td>
    <td>☆☆☆</td>
    <td>☆☆☆</td>
    <td>Seamless optimized interop with native code on both platforms without any conversions or wrappers.</td>
</tr>
<tr>
    <td>Int32Array or Array[Int]</td>
    <td>★★★</td>
    <td>★★★</td>
    <td>☆☆☆</td>
    <td>☆☆☆</td>
    <td>Seamless optimized interop with native code on both platforms without any conversions or wrappers.</td>
</tr>
<tr>
    <td>Float32Array or Array[Float]</td>
    <td>★★★</td>
    <td>★★★</td>
    <td>☆☆☆</td>
    <td>☆☆☆</td>
    <td>Seamless optimized interop with native code on both platforms without any conversions or wrappers.</td>
</tr>
<tr>
    <td>Float64Array or Array[Double]</td>
    <td>★★★</td>
    <td>★★★</td>
    <td>☆☆☆</td>
    <td>☆☆☆</td>
    <td>Seamless optimized interop with native code on both platforms without any conversions or wrappers.</td>
</tr>
<tr>
    <td>Other JavaScript Typed Arrays</td>
    <td>☆☆☆</td>
    <td>☆☆☆</td>
    <td>☆☆☆</td>
    <td>☆☆☆</td>
    <td>Good JVM analogues do not exist.  NArr can't help, but what can?</td>
</tr>
</table>

NArr has no impact on JVM or Native performance, but it can dramatically speed up JavaScript by making use of natively optimized data structures and eliminating conversions that tend to have O(n) run time complexities.  It also adds convenience methods for js.Array[T] such as fill and tabulate, but mainly eliminates the need for specially crafted and maintained @JSExport methods and fields for JavaScript interop.

To use this library with SBT:

```scala
libraryDependencies += "ai.dragonfly" %%% "NArr" % "<LATEST_VERSION>"
```

How to use NArr:

```scala
import NArray

// constructor call
val a1:NArray[String] = new NArray[String](5)  // in JavaScript this resolves to js.Array[String]

// literal
val a2:NArray[Int] = NArray[Int](2, 4, 8, 16)  // in JavaScript this resolves to Int32Array

// fill
val a3:NArray[Double] = NArray.fill[Double](10)(42)  // in JavaScript this resolves to Float64Array

// tabulate
val a4:NArray[Double] = NArray.tabulate[Double](42)(
  (i:Int) => i * Math.random()
)

// multi dimensional
val a2d:NArray[NArray[Double]] = NArray[NArray[Double]]( 
  NArray.tabulate[Double](5)( (i:Int) => i * Math.random() ),
  NArray.tabulate[Double](5)( (i:Int) => i * Math.random() ),
  NArray.tabulate[Double](5)( (i:Int) => i * Math.random() ),
  NArray.tabulate[Double](5)( (i:Int) => i * Math.random() )
) // in JavaScript this resolves to js.Array[Float64Array]

// enjoy Scala semantics
a2d.foreach((d:Double) => println(d))

// conveniently expose Scala.js libraries to native JavaScript and Java developers without any need for wrappers or conversions.
@JSExportTopLevel("copy")
def copy(a0:NArray[Double]): NArray[Double] = NArray.tabulate[Double](a0.length)(
  (i:Int) => a0(i)
)

```
<a href="https://dragonfly-ai.github.io/narr/">Try the demo</a>!
<hr />
Projects that rely on NArr:

https://github.com/dragonfly-ai/bitfrost

https://github.com/dragonfly-ai/vector

https://github.com/dragonfly-ai/matrix

https://github.com/dragonfly-ai/color

https://github.com/dragonfly-ai/spatial

https://github.com/dragonfly-ai/img