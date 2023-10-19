package task3.Data;
public class FlownKilometers{
    private int value;
    public FlownKilometers(final int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(final int value) {
        this.value = value;
    }
    public void addKilometers(final int flownKilometers){
        this.value += flownKilometers;
    }
}
