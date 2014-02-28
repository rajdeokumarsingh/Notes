/*
 * Copyright (C) 2013 Capital Alliance Software LTD (Pekall)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.java.examples.basic;

public class ComplementNumber {

    public static void main(String[] args) {
        // printComplementNumber();

 		byte optcode = 1;
		byte one = (byte) -128;
		one |= optcode;
        System.out.printf("%x", one);
    }

    private static void printComplementNumber() {
        System.out.printf("-128 in byte: %x\n", (byte) -128);
        System.out.printf("-128 in int: %x\n", -128);
    }
}
