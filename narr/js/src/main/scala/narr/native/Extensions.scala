/*
 * Copyright 2023 dragonfly.ai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package narr.native

import narr.{TypedArrayOps, NativeTypedArray}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSBracketAccess
import scala.scalajs.js.typedarray.*
import scala.language.implicitConversions
import scala.reflect.ClassTag

// to imbue js.Array with scala.Array semantics


object Extensions {

  extension[T] (nArray:NArray[T]) {
    inline def length: Int = nArray.asInstanceOf[NArr[T]].length
    @JSBracketAccess inline def apply(i: Int): T = nArray.asInstanceOf[NArr[T]].apply(i)
    @JSBracketAccess inline def update(index: Int, value: T): Unit = nArray.asInstanceOf[NArr[T]].update(index, value)

  }

}