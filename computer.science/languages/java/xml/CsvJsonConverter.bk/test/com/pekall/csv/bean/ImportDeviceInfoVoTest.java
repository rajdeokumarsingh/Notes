package com.pekall.csv.bean;

import junit.framework.TestCase;

public class ImportDeviceInfoVoTest extends TestCase {
    public void testIsEmptyDevice() throws Exception {
        ImportDeviceInfoVo vo = new ImportDeviceInfoVo();
        assertTrue(vo.isEmptyDevice());

        vo.setUsername("");
        assertFalse(vo.isEmptyDevice());

        vo.setUsername(null);
        assertTrue(vo.isEmptyDevice());

        vo.setOs(30);
        assertFalse(vo.isEmptyDevice());
    }
}
