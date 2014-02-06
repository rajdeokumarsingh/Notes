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

package com.pekall.demo.bean;

import java.util.ArrayList;
import java.util.List;

public class DemoBeanCollection {
    public List<DemoBean> lines;

    public List<DemoBean> getBeans() {
        return lines;
    }

    public void setBeans(List<DemoBean> beans) {
        this.lines = beans;
    }

    public void addBeans(DemoBean bean) {
        if (lines == null) {
            lines = new ArrayList<DemoBean>();
        }
        lines.add(bean);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (lines != null) {
            sb.append("{");
            for (DemoBean bean : lines) {
                sb.append(bean.toString()).append("\n");
            }
            sb.append("}");
        }
        return "DemoBeanCollection{" +
                "lines=" + sb.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DemoBeanCollection)) return false;

        DemoBeanCollection info = (DemoBeanCollection) o;

        //noinspection RedundantIfStatement
        if (this.hashCode() != info.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        if (lines != null) {
            for (DemoBean bean : lines) {
                result += bean.hashCode();
            }
        }
        return result;
    }
}