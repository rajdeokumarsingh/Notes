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

import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import junit.framework.TestCase;

import java.sql.SQLException;
import java.util.List;

public class OrderTest extends TestCase {

    ConnectionSource connectionSource;
    Dao<Order, Integer> orderDao;

    public void setUp() throws Exception {
        super.setUp();

        // this uses h2 by default but change to match your database
        String databaseUrl = "jdbc:h2:mem:account";

        // create a connection source to our database
        connectionSource = new JdbcConnectionSource(databaseUrl);

        // instantiate the dao
        orderDao = DaoManager.createDao(connectionSource, Order.class);

        // if you need to create the 'order' table make this call
        // TableUtils.createTable(connectionSource, Order.class);
        TableUtils.createTableIfNotExists(connectionSource, Order.class);
    }

    public void tearDown() throws Exception {
        TableUtils.clearTable(connectionSource, Order.class);
        connectionSource.close();
    }

    public void testGenerateId() throws SQLException {
        Order order1 = new Order();
        order1.setOrderName("pekall mdm");
        order1.setOrderNumber("pka0001");
        orderDao.create(order1);

        Order order2 = new Order();
        order2.setOrderName("pekall cloud");
        order2.setOrderNumber("pka0002");
        orderDao.create(order2);

        Order order3 = new Order();
        order3.setOrderName("pekall boss");
        order3.setOrderNumber("pka0003");
        orderDao.create(order3);

        assertEquals(orderDao.countOf(), 3);

        // iteration of all elements
        for (Order order : orderDao) {
            System.out.println("order: " + order.toString());
        }

        CloseableWrappedIterable<Order> wrappedIterable =
                orderDao.getWrappedIterable();
        try {
            for (Order order : wrappedIterable) {
                System.out.println("order: " + order.toString());
            }
        } finally {
            wrappedIterable.close();
        }
    }

}
