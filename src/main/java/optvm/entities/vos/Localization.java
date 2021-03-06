package optvm.entities.vos;

import java.io.Serializable;

public class Localization implements Serializable {

    private int timeZone;
    private String country;

    public Localization() {
    }

    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
