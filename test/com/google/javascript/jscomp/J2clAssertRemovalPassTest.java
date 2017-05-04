/*
 * Copyright 2017 The Closure Compiler Authors.
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
package com.google.javascript.jscomp;

public class J2clAssertRemovalPassTest extends CompilerTestCase {

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    return new J2clAssertRemovalPass(compiler);
  }

  @Override
  protected CompilerOptions getOptions() {
    CompilerOptions options = super.getOptions();
    options.setJ2clPass(CompilerOptions.J2clPassMode.ON);
    return options;
  }

  public void testRemoveAssert() {
    test(
        LINE_JOINER.join("Asserts.$assert(true);", "Asserts.$assert(goo());"),
        LINE_JOINER.join("void 0;", "void 0;"));
  }

  public void testRemoveAssertWithMessage() {
    test("Asserts.$assertWithMessage(true, goo());", "void 0;");
  }

  public void testNotRemoveAssert() {
    testSame(LINE_JOINER.join("Asserts.assert(true);", "$assert(goo());"));
  }
}