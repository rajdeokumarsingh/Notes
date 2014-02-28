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

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import junit.framework.TestCase;

import java.sql.SQLException;
import java.util.List;

// todo :http://ormlite.com/javadoc/ormlite-core/doc-files/ormlite_2.html#Using

public class AccountTest extends TestCase {

    ConnectionSource connectionSource;
    Dao<Account, String> accountDao;

    public void setUp() throws Exception {
        super.setUp();

        // this uses h2 by default but change to match your database
        String databaseUrl = "jdbc:h2:mem:account";
        // create a connection source to our database
        connectionSource = new JdbcConnectionSource(databaseUrl);

        // instantiate the dao
        accountDao = DaoManager.createDao(connectionSource, Account.class);

        // if you need to create the 'accounts' table make this call
        // TableUtils.createTable(connectionSource, Account.class);
        TableUtils.createTableIfNotExists(connectionSource, Account.class);

//        System.out.println(TableUtils.getCreateTableStatements(
//                connectionSource, Account.class));
    }

    public void tearDown() throws Exception {
        TableUtils.clearTable(connectionSource, Account.class);
        connectionSource.close();
    }

    public void testInsertData() throws SQLException {
        // create an instance of Account
        Account account = new Account();
        account.setName("Jim Coakley");
        account.setPassword("123456");

        Account account1 = new Account();
        account1.setName("Ray Jiang");
        account1.setPassword("asdfgh");

        // persist the account object to the database
        accountDao.create(account);
        accountDao.create(account1);

        // retrieve the account from the database by its id field (name)
        Account account2 = accountDao.queryForId("Jim Coakley");
        assertEquals(account2.getPassword(), "123456");

        List<Account> list = accountDao.queryForEq("password", "asdfgh");
        assertEquals(list.size(), 1);
        for (Account account5 : list) {
            assertEquals(account5.getName(), "Ray Jiang");
        }

        assertEquals(accountDao.countOf(), 2);
    }
}
