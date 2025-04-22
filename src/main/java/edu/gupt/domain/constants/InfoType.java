package edu.gupt.domain.constants;

public class InfoType {
    public static final int STAR = 1;
    public static final int NEWS = 2;
    public static final int CULTURE = 4;
    public static final int TRAINING = 8;
    public static final int ENROLLMENT_AND_EMPLOYMENT = 16;
    public static final int OPEN_INFO = 6;
    public static final int OTHER = 7;

    public static int getInfoType(int typeId) {
        return switch (typeId) {
            case 1 -> InfoType.STAR;
            case 2 -> InfoType.NEWS;
            case 3 -> InfoType.CULTURE;
            case 4 -> InfoType.TRAINING;
            case 5 -> InfoType.ENROLLMENT_AND_EMPLOYMENT;
            case 6 -> InfoType.OPEN_INFO;
            case 7 -> InfoType.OTHER;
            case 8 -> 8;
            default -> -1;
        };
    }

    public static int POTypeToVOType(int typeId) {
        return switch (typeId) {
            case InfoType.STAR -> 1;
            case InfoType.NEWS -> 2;
            case InfoType.CULTURE -> 3;
            case InfoType.TRAINING -> 4;
            case InfoType.ENROLLMENT_AND_EMPLOYMENT -> 5;
            case InfoType.OPEN_INFO -> 6;
            case InfoType.OTHER -> 7;
            default -> -1;
        };
    }
}
