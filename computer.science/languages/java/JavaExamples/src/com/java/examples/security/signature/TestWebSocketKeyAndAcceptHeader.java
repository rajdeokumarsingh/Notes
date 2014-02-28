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

package com.java.examples.security.signature;

import com.dd.plist.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestWebSocketKeyAndAcceptHeader {
    public static void main(String[] args) {
        // Sec-WebSocket-Key header in client request
        String seckey = "9zmv8eQH5JT6eIN2ZlV+pA==";

        String acc = seckey + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
        MessageDigest sh1;
        try {
            sh1 = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        // Sec-WebSocket-Accept header in server response
        System.out.println(Base64.encodeBytes(sh1.digest(acc.getBytes())));
    }
}
