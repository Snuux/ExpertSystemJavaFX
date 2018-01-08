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
//        if (attribute.getValue() == null)
//            return false;
//        return ;
    }

    public void setValueToAttribute() {
        attribute.setValue(value);
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public T getValue() {
        return value;
    }

    public Operation getOperation() {
        return operation;
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

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(this))
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
                    //ТУТ ЗАДАЁТСЯ ВОПРОС?
                    break;
            }

        return stringBuilder.toString();
    }
}
