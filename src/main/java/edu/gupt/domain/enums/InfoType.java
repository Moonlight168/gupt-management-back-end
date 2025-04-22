package edu.gupt.domain.enums;

import lombok.Getter;

@Getter
public enum InfoType {
    STAR(0), // 特别报道或明星新闻
    NEWS(1), // 一般新闻
    CULTURE(2), // 文化新闻
    TRAINING(3), // 培训通知
    ENROLLMENT_AND_EMPLOYMENT(4), // 招生与就业信息
    INFO(5), // 一般信息
    OTHER(6), // 其他通知
    ALL(7); // 用于表示所有类型的特殊值（如果需要的话）

    private final int value;

    InfoType(int value) {
        this.value = value;
    }

    // 根据值查找对应的枚举常量
    public static InfoType fromValue(int value) {
        for (InfoType type : InfoType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }
}
