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

    public boolean hasValue() {
        return this.value != null;
    }

    public Attribute setType(String s) {
        if (s == "O")
            type = Type.OBJECT;
        else if (s == "Q")
            type = Type.QUESTION;
        else if (s == "R")
            type = Type.ROOT;
        else if (s == "T")
            type = Type.TEMPORARY;
        else if (s == "A")
            type = Type.ATTRIBUTE;

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
