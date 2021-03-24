public class Bucket {
    private long stamp; //timestamp-ul unei temperaturi observate
    private double temp; //temperatura observata

    public Bucket(long stamp, double temp) { //setare bucket
        this.stamp = stamp;
        this.temp = temp;
    }

    public double getTemp() {
        return temp;
    }

    public long getStamp() {
        return stamp;
    }

    public void copy(Bucket x) { //copiere bucket
        this.stamp = x.stamp;
        this.temp = x.temp;
    }
}
