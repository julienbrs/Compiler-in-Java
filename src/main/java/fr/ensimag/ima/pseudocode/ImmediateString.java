package fr.ensimag.ima.pseudocode;

/**
 * Immediate operand representing a string.
 * 
 * @author Ensimag
 * @date 01/01/2023
 */
public class ImmediateString extends Operand {
    private String value;

    public ImmediateString(String value) {
        super();
        this.value = value;
    }

    @Override
    public String toString() {
        System.out.println(value.replace("\\\"", "\"\"").replace("\\\\", "\\"));
        return "\"" + value.replace("\\\"", "\"\"").replace("\\\\", "\\") + "\"";
    }
}
