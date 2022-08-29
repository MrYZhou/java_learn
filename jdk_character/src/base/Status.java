package base;

public enum Status {
    DefaultStatus("",-1),
    StormyStatusFirst("暴雨一",1),
    StormyStatusSecond("暴雨二",2);
    private final String name;
    private final Integer value;

    Status(String name,Integer value){
        this.name = name;
        this.value= value;
    };
    public static Status of(Integer value){
        for (Status status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return DefaultStatus;
    }

}
