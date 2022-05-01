package main.domain;

import java.io.Serializable;

public class Word implements Serializable, Keyable<String> {
    private String czech;
    private String english;
    private String german;

    public Word(String czech, String english, String german) {
        this.czech = czech;
        this.english = english;
        this.german = german;
    }

    public void setCzech(String czech) {
        this.czech = czech;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getGerman() {
        return german;
    }

    public void setGerman(String german) {
        this.german = german;
    }

    @Override
    public String getKey() {
        return czech;
    }

    @Override
    public String toString() {
        return "{" +
                "czech='" + czech + '\'' +
                ", english='" + english + '\'' +
                ", german='" + german + '\'' +
                '}';
    }
}
