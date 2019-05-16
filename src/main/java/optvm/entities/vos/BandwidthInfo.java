package optvm.entities.vos;

import java.io.Serializable;

public class BandwidthInfo implements Serializable {

    private long speed;

    public BandwidthInfo() {
    }

    public BandwidthInfo(long speed) {
        this.speed = speed;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }
}
