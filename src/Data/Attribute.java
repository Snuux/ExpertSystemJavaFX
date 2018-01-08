package Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Attribute<T> implements Serializable{
    private String text;
    private T value;
    private Type type;
    private ValueType valueType;
    private ArrayList<String> variantAnswers;

    public Attribute(String text, T value, Type type) {
        this.text = text;
        this.value = value;
        this.type = type;
    }

    //truefalse
    public Attribute(String text, T value, Type type, ValueType valueType) {
        this(text, value, type);

        this.valueType = valueType;
    }

    //strings
    public Attribute(String text, T value, Type type, ValueType valueType, String ... variantAnswers) {
        this(text, value, type, valueType);

        this.valueType = valueType;

        this.variantAnswers = new ArrayList();
        this.variantAnswers.addAll(Arrays.asList(variantAnswers));
    }

    public Attribute<T> setValue(T value) {
        this.value = value;
        return this;
    }

    public String getText() {
        return text;
    }

    public Type getType() {
        return type;
    }

    public T getValue() {
        return value;
    }

    public Attribute setType(String s) {
        switch (s) {
            case "O":
                type = Type.OBJECT;
                break;
            case "Q":
                type = Type.QUESTION;
                break;
            case "R":
                type = Type.ROOT;
                break;
            case "T":
                type = Type.TEMPORARY;
                break;
            case "A":
                type = Type.ATTRIBUTE;
                break;
        }

        return this;
    }

    public ArrayList<String> getVariantAnswers() {
        return variantAnswers;
    }

    public ValueType getValueType() {
        return valueType;
    }

    @Override
    public String toString() {
        return text;
    }

    public boolean hasValue() {
        return value != null;
    }

    public enum Type {
        OBJECT,
        TEMPORARY,
        ATTRIBUTE,
        QUESTION,
        ROOT
    }

    public enum ValueType {
        TRUE_FALSE_DONT_KNOW,
        TRUE_FALSE,
        STRING,
        NUMBER
    }
}
