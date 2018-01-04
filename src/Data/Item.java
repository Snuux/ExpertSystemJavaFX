package Data;

import java.io.Serializable;

public class Item<T> implements Serializable{
    private Attribute attribute; //*указатель
    private T value;
    private int id;
    private static int count = 0;
    private String tag;
    private Operation operation;
    private boolean asked;

    public Item(String tag, Attribute attribute, T value) {
        this.id = count++;

        this.tag = tag;
        this.attribute = attribute;
        this.value = value;
        this.asked = false;
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

    public boolean isAsked() {
        return asked;
    }

    public void setAsked(boolean asked) {
        this.asked = asked;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(this))
            return false;

        return ((Item) obj).id == id;
    }

    public enum Operation {
        AND,
        OR,
        XOR,
        NO_OP
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tag).append(" ").append(attribute.getText()).append(" == ").append(value);

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
