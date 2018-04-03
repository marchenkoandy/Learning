package com.company.lesson5;

public enum ComplicatedEnum {
    January("Январь", "Січень", 31, Season.Winter),
    February("Февраль", "Лютий", 28, Season.Winter),
    March("Март", "Березень", 31, Season.Spring),
    April("Апрель", "Квітень", 30, Season.Spring),
    May("Май", "Травень", 31, Season.Spring),
    June("Июнь", "Червень", 31, Season.Summer),
    July("Июль", "Липень", 30, Season.Summer),
    August("Август", "Серпень", 31, Season.Summer),
    September("Сентябрь", "Вересень", 30, Season.Autumn),
    October("Октябрь", "Жовтень", 31, Season.Autumn),
    November("Ноябрь", "Листопад", 30, Season.Autumn),
    December("Декабрь", "Грудень", 31, Season.Winter);

    private final String nameRu;
    private final String nameUa;
    private final int days;
    private final Season season;

    ComplicatedEnum(String nameRu, String nameUa, int days, Season season) {
        this.nameRu = nameRu;
        this.nameUa = nameUa;
        this.days = days;
        this.season = season;
    }

    @Override
    public String toString() {
        return nameRu;
    }

    private enum Season {
        Winter,
        Spring,
        Summer,
        Autumn
    }
}
