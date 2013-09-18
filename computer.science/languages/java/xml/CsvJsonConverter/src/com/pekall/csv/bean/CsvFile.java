package com.pekall.csv.bean;

import java.util.ArrayList;
import java.util.List;

public class CsvFile {
    private List<CsvLine> lines;

    public List<CsvLine> getLines() {
        return lines;
    }

    public void setLines(List<CsvLine> lines) {
        this.lines = lines;
    }

    public void addLine(CsvLine line) {
        if (lines == null) {
            lines = new ArrayList<CsvLine>();
        }
        lines.add(line);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (lines != null) {
            sb.append("{");
            for (CsvLine line : lines) {
                sb.append(line.toString() + "\n");
            }
            sb.append("}");
        }
        return "CsvFile{" +
                ", lines=" + sb.toString() +
                '}';
    }

    public String toCvs() {
        StringBuilder sb = new StringBuilder();
        if (lines == null) {
            return sb.toString();
        }
        for (CsvLine line : lines) {
            sb.append(line.toCvs() + "\n");
        }
        if (sb.length() > 0) {
            // delete last new line
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CsvFile)) return false;

        CsvFile info = (CsvFile) o;

        if (this.hashCode() != info.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        if (lines != null) {
            for (CsvLine line : lines) {
                result += line.hashCode();
            }
        }
        return result;
    }
}
