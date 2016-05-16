//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package gpio;

import gpio.CmdExecutor;
import gpio.OdroPin;
import gpio.PinMode;
import gpio.PinState;

public class GPIOPin {
    private OdroPin pin;
    private PinMode mode;
    private PinState state;

    public GPIOPin(OdroPin pin, PinMode mode) {
        this(pin, mode, PinState.LOW);
    }

    public GPIOPin(OdroPin pin, PinMode mode, PinState state) {
        this.pin = pin;
        this.mode = mode;
        this.export();
        this.direction();
    }

    public void low() {
        this.state = PinState.LOW;
        this.value();
    }

    public void high() {
        this.state = PinState.HIGH;
        this.value();
    }

    public void toggle() {
        if(this.state == PinState.HIGH) {
            this.state = PinState.LOW;
        } else {
            this.state = PinState.HIGH;
        }

        this.value();
    }

    public PinState read() {
        this.readValue();
        return this.state;
    }

    public void shutdown() {
        this.state = PinState.LOW;
        this.mode = PinMode.IN;
        this.value();
        this.direction();
        this.unexport();
    }

    public OdroPin getPin() {
        return this.pin;
    }

    public PinMode getMode() {
        return this.mode;
    }

    private void export() {
        String[] export = new String[]{"sudo", "/bin/sh", "-c", "echo " + this.pin.getOdroidCode() + " > /sys/class/gpio/export"};
        CmdExecutor.execute(export);
    }

    private void unexport() {
        String[] export = new String[]{"sudo", "/bin/sh", "-c", "echo " + this.pin.getOdroidCode() + " > /sys/class/gpio/unexport"};
        CmdExecutor.execute(export);
    }

    private void direction() {
        String[] directory = new String[]{"sudo", "/bin/sh", "-c", "echo " + this.mode.getCode() + " > /sys/class/gpio/gpio" + this.pin.getOdroidCode() + "/direction"};
        CmdExecutor.execute(directory);
    }

    private void value() {
        String[] high = new String[]{"sudo", "/bin/sh", "-c", "echo " + this.state.getCode() + " > /sys/class/gpio/gpio" + this.pin.getOdroidCode() + "/value"};
        CmdExecutor.execute(high);
    }

    private void readValue() {
        String out = CmdExecutor.execute("cat /sys/class/gpio/gpio" + this.pin.getOdroidCode() + "/value ");
        System.out.println(out.split("\n")[0]);
        if(out.split("\n")[0].equals(PinState.HIGH.getCode())) {
            this.state = PinState.HIGH;
        } else {
            this.state = PinState.LOW;
        }

    }
}
