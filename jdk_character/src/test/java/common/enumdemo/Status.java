package enumdemo;

public enum Status {
    DefaultStatus("", -1),
    StormyStatusFirst("暴雨一", 1),
    StormyStatusSecond("暴雨二", 2);

    public final Integer value;

    public final String name;

    Status(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static Status of(Integer value) {
        for (Status status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return DefaultStatus;
    }

    public static Status of(String name) {
        for (Status status : values()) {
            if (status.name.equals(name)) {
                return status;
            }
        }
        return DefaultStatus;
    }

}
