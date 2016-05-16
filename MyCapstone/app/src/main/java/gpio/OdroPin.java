//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package gpio;

public enum OdroPin {
    GPIO_00(74, "SDA1", -1),
    GPIO_01(75, "SCL1", -1),
    GPIO_02(83, "#83", 7),
    GPIO_03(88, "#88", 0),
    GPIO_04(116, "#116", 2),
    GPIO_05(115, "#115", 3),
    GPIO_06(107, "MOSI", 12),
    GPIO_07(106, "MISO", 13),
    GPIO_08(105, "SCLK", 14),
    GPIO_09(76, "SDA2", -1),
    GPIO_10(101, "#101", 21),
    GPIO_11(100, "#100", 22),
    GPIO_12(108, "#108", 23),
    GPIO_13(97, "#97", 24),
    GPIO_14(113, "TXD1", -1),
    GPIO_15(114, "RXD1", -1),
    GPIO_16(87, "#87", 1),
    GPIO_17(104, "#104", 4),
    GPIO_18(102, "#102", 5),
    GPIO_19(103, "#103", 6),
    GPIO_20(117, "CE0", 10),
    GPIO_21(118, "#118", 11),
    GPIO_22(77, "SCL2", -1),
    GPIO_23(99, "#99", 26),
    GPIO_24(98, "#98", 27);

    private final int code;
    private final String label;
    private final int wiring;

    private OdroPin(int code, String label, int wiring) {
        this.code = code;
        this.label = label;
        this.wiring = wiring;
    }

    public int getOdroidCode() {
        return this.code;
    }

    public String getLabel() {
        return this.label;
    }

    public int getWiringPin() {
        return this.wiring;
    }
}
