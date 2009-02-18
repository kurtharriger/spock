/*
 * Copyright 2009 the original author or authors.
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

package org.spockframework.smoke.mock

import org.codehaus.groovy.runtime.typehandling.GroovyCastException
import org.junit.runner.RunWith
import org.spockframework.dsl.*
import static org.spockframework.dsl.Predef.*
import org.spockframework.util.SyntaxException

@Speck
@RunWith(Sputnik)
class ValidMockCreation {
  List list1 = Mock()

  def "field typed w/ mock untyped"() {
    expect: list1 instanceof List
  }

  List list2 = Mock(ArrayList)

  def "field typed w/ mock typed"() {
    expect: list2 instanceof ArrayList
  }

  def list3 = Mock(List)

  def "field untyped w/ mock typed"() {
    expect: list3 instanceof List
  }

  def list4 = Predef.Mock(List)

  def "field w/ mock qualified"() {
    expect: list4 instanceof List
  }

  def "local typed w/ mock untyped"() {
    List list = Mock()
    expect: list instanceof List
  }

  def "local typed w/ mock typed"() {
    List list = Mock(ArrayList)
    expect: list instanceof ArrayList
  }
  
  def "local untyped w/ mock typed"() {
    def list = Mock(List)
    expect: list instanceof List
  }

  def "local w/ mock qualified"() {
    List list = Predef.Mock()
    expect: list instanceof List
  }

  def "expr typed"() {
    expect: Mock(List) instanceof List
  }

  def "expr qualified"() {
    expect: Predef.Mock(List) instanceof List
  }

  def "creation in nested expr"() {
    def list = null
    if (1) list = id(id(Mock(List)))
    expect: list instanceof List
  }

  def "creation in closure"() {
    // a closure preceded by a label is parsed as block by Groovy,
    // so we use "assert" instead of "expect:" here
    assert { it -> { it2 -> Mock(List) }() }() instanceof List
  }

  @Ignore // can't be made to work as long as MockController is instance field
  def "creation in parameterization"() {
    expect:
    mock1 instanceof List
    mock2 instanceof List

    where:
    mock1 << [Mock(List)]
    mock2 = Mock(List)
  }

  private id(arg) { arg }
}