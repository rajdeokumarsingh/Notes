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

package com.ormlite.example;

import com.j256.ormlite.field.DatabaseField;

public class Order {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "name")
    String orderName;

    @DatabaseField(columnName = "number")
    String orderNumber;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String name) {
        this.orderName = name;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String number) {
        this.orderNumber = number;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderName='" + orderName + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                '}';
    }
}
