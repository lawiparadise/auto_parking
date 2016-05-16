//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package gpio;

public enum PinState {
    LOW("0", 0, "low"),
    HIGH("1", 1, "high");

    private final String code;
    private final int state;
    private final String label;

    private PinState(String code, int state, String label) {
        this.code = code;
        this.state = state;
        this.label = label;
    }

    public String getCode() {
        return this.code;
    }

    public boolean toBool() {
        return this.state != 0;
    }

    public int toInt() {
        return this.state;
    }

    public String toString() {
        char first = Character.toUpperCase(this.label.charAt(0));
        String string = first + this.label.substring(1);
        return string;
    }
}
