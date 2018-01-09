package Data;

import java.io.Serializable;

import static Data.Rule.Operation.NO_OP;

public class Rule<T> implements Serializable {
    private Attribute attribute; //*указатель
    private T value;
    private int id;
    private static int count = 0;
    private String tag;
    private Operation operation;
    private boolean used;

    public Rule(String tag, Attribute attribute, T value) {
        this.id = count++;

        this.tag = tag;
        this.attribute = attribute;
        this.value = value;
        this.used = false;
        this.operation = NO_OP;
    }

    public boolean isEqualToAttribute() {
        return attribute.getValue() != null && this.value.equals(attribute.getValue());
    }

    public void setValueToAttribute() {
        attribute.setValue(value);
    }

    public void reset() {
        used = false;
        attribute.reset();
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setOperation(String operation) {
        switch (operation.toUpperCase()) {
            case "AND":
                this.operation = Operation.AND;
                break;
            case "OR":
                this.operation = Operation.OR;
                break;
            case "NO_OP":
                this.operation = Operation.NO_OP;
                break;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(this.getClass()))
            return false;

        return ((Rule) obj).id == id;
    }

    public enum Operation {
        AND,
        OR,
        XOR,
        NO_OP
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(attribute.getText()).append(", ");
        stringBuilder.append("ruleIsUsed: ").append(isUsed()).append(", ");
        stringBuilder.append("atrHasValue: ").append(getAttribute().hasValue()).append(", ");
        stringBuilder.append("equalsAtr: ").append(isEqualToAttribute()).append(", ");
        stringBuilder.append(tag).append(" == ").append(value);

        if (operation != null)
            switch (operation) {
                case AND:
                    stringBuilder.append(" -- AND");
                    break;
                case OR:
                    stringBuilder.append(" -- OR");
                    break;
                case XOR:
                    stringBuilder.append(" -- XOR");
                    break;
                case NO_OP:
                    //Устанавливается для вопросов
                    break;
            }

        return stringBuilder.toString();
    }
}
