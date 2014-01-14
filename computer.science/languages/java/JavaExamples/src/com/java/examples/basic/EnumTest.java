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

class NetworkInfo {
    private State mState;

    NetworkInfo() {
        this.mState = State.DISCONNECTED;
    }

    State getState() {
        return mState;
    }

    void setState(State mState) {
        this.mState = mState;
    }

    enum State {
        CONNECTING, CONNECTED, SUSPENDED, DISCONNECTING, DISCONNECTED, UNKNOWN
    }
}

public class EnumTest {
    public static void main(String[] args) {
        NetworkInfo info = new NetworkInfo();
        System.out.println("enum equals: " + (info.getState() == NetworkInfo.State.DISCONNECTED));
        System.out.println("enum equals: " + (info.getState() == NetworkInfo.State.CONNECTED));
    }
}

class Circle {
    public static final double PI = 3.1415926;
    private double radius;

    public double area() {
        return PI * radius * radius;
    }
}
