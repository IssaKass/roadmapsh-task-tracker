package core;

/**
 * Author: abdallah-issakass
 */
public enum Status {
    TODO("todo"), IN_PROGRESS("in-progress"), DONE("done");

    private final String value;


    Status(String value) {
        this.value = value;
    }

    public static Status fromValue(String value) {
        for (Status status : Status.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }

    public String getValue() {
        return value;
    }
}
