package io.nova.core.codes;

public enum DataType {
    BYTE(0x1400),
    UNSIGNED_BYTE(0x1401),
    SHORT(0x1402),
    UNSIGNED_SHORT(0x1403),
    INT(0x1404, 1),
    UNSIGNED_INT(0x1405),
    FLOAT(0x1406, 4),
    _2_BYTES(0x1407),
    _3_BYTES(0x1408),
    _4_BYTES(0x1409),
    DOUBLE(0x140A);

    private final int value;
    private int bytes;

    DataType(int value) {
        this.value = value;
    }

    DataType(int value, int bytes) {
        this.value = value;
        this.bytes = bytes;
    }

    public int getValue() {
        return value;
    }

    public int getByteSize() {
        return bytes;
    }
}