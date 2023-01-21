package fr.ensimag.ima.pseudocode;

/**
 * Operand representing a register indirection with register offset and index offset , e.g. 42(R3, R1).
 *
 * @author Ensimag
 * @date 01/01/2023
 */
public class RegisterOffOffset extends DAddr {
    public int getOffset() {
        return offset;
    }
    public Register getRegister() {
        return register;
    }
    public Register getOffsetRegister() {
        return register;
    }
    private final int offset;
    private final Register register;
    private final Register offsetRegister;
    public RegisterOffOffset(int offset, Register register, Register offsetRegister) {
        super();
        this.offset = offset;
        this.register = register;
        this.offsetRegister = offsetRegister;
    }
    @Override
    public String toString() {
        return offset + "(" + register + ", " + offsetRegister + ")";
    }
}
