package model.enums;

import model.exceptions.NotFoundException;


public enum LogTypes {
    FLIGHTS("flights"), SALES("sales");
    private final String fileName;
    LogTypes(String fileName) {
        this.fileName = fileName;
    }
    public String getFileName() {
        return fileName;
    }
    public static LogTypes getLogTypesByFileName(String fileName) {
        if (fileName.equals(LogTypes.FLIGHTS.getFileName())) {
            return LogTypes.FLIGHTS;
        } else if (fileName.equals((LogTypes.SALES.getFileName()))) {
            return LogTypes.SALES;
        } else {
            throw new NotFoundException("File not fund: " + fileName);
        }
    }
}
