package task3;

public class IdGenerator{
    private Long Id = 0L;
    public Long createId(){
        return Id++;
    }
}
