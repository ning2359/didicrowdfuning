package com.didi.entrty.utils;

public class Score {
    private String discipline;
    private double fraction;

    public Score() {

    }

    public Score(String discipline, double fraction) {
        this.discipline = discipline;
        this.fraction = fraction;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public double getFraction() {
        return fraction;
    }

    public void setFraction(double fraction) {
        this.fraction = fraction;
    }

    @Override
    public String toString() {
        return "Score{" +
                "discipline='" + discipline + '\'' +
                ", fraction=" + fraction +
                '}';
    }
}
