package Data;

import Data.Serialization.FileSerialization;
import Data.Serialization.Tree;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;

public class DataManager {
    private static TreeItem<Rule> tree;
    private static ArrayList<Attribute> attributes;

    public static void test() {
        saveBase();
        //load();
    }

    public static void saveBase() {
        Attribute<Boolean> r = new Attribute("Корень дерева", null, Attribute.Type.ROOT);

        Attribute<Integer> q1 = new Attribute("Вы хотите расслабиться при чтении книги?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q2 = new Attribute("В книге должен быть динамичный сюжет?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE_DONT_KNOW);
        Attribute<Integer> q3 = new Attribute("Вам больше нравится оригинальный авторский слог?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE_DONT_KNOW);
        Attribute<Integer> q4 = new Attribute("В данный момент вы учите иностранный язык?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE_DONT_KNOW);
        Attribute<Integer> q5 = new Attribute("Вы хотите выставить книгу на всеобщее обозрение?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE_DONT_KNOW);
        Attribute<Integer> q6 = new Attribute("Вам хочется потратить больше денег, чтобы сделать подарок приятнее?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE_DONT_KNOW);

        Attribute<String> a1 = new Attribute("Жанр", null, Attribute.Type.ATTRIBUTE);
        Attribute<String> a2 = new Attribute("Язык оригинала", null, Attribute.Type.ATTRIBUTE);
        Attribute<String> a3 = new Attribute("Тип обложки", null, Attribute.Type.ATTRIBUTE);

        Attribute<String> o = new Attribute("Объект", null, Attribute.Type.OBJECT);

        attributes = new ArrayList();
        attributes.add(r);
        attributes.add(q1);
        attributes.add(q2);
        attributes.add(q3);
        attributes.add(q4);
        attributes.add(a1);
        attributes.add(a2);
        attributes.add(o);

        TreeItem<Rule> R = new TreeItem(new Rule("ROOT", r, null));
        tree = R;
        //tree.getChildren().add(R);

        addRule(R, null, new Rule("O1", o, "Властелин колец"));
        addRule(R, null, new Rule("O2", o, "Фауст"));
        TreeItem<Rule> O1 = addRule(getNodeFromNodeWithTag(R, "O1"), Rule.Operation.AND, new Rule("A1", a1, "Художественная"), new Rule("A2", a2, "Другой"));
        TreeItem<Rule> A1 = addRule(getNodeFromNodeWithTag(O1, "A1"), Rule.Operation.AND, new Rule("Q1", q1, 1), new Rule("Q2", q2, 1));
        TreeItem<Rule> A2 = addRule(getNodeFromNodeWithTag(O1, "A2"), Rule.Operation.AND, new Rule("Q3", q3, 1), new Rule("Q4", q4, 1));

        TreeItem<Rule> O2 = addRule(getNodeFromNodeWithTag(R, "O2"), Rule.Operation.AND, new Rule("A1", a1, "Философский"), new Rule("A2", a2, "Другой"), new Rule("A3", a3, "Подарочный"));
        TreeItem<Rule> A3 = addRule(getNodeFromNodeWithTag(O2, "A3"), Rule.Operation.AND, new Rule("Q5", q5, 1), new Rule("Q6", q6, 1));

        TreeItem<Rule> A11 = addRule(getNodeFromNodeWithTag(O2, "A1"), Rule.Operation.AND, new Rule("Q1", q1, 1), new Rule("Q2", q2, 1), new Rule("Q3", q3, 1));
        TreeItem<Rule> A22 = addRule(getNodeFromNodeWithTag(O2, "A2"), Rule.Operation.AND, new Rule("Q3", q3, 1), new Rule("Q4", q4, 1));

        //System.out.println(getQuestionsCountInTreeItem(O1));
        //System.out.println(getQuestionsCountInTreeItem(O2));

        //System.out.println(getQuestion(R.getChildren().get(0).getChildren().get(0).getChildren().get(0)).getValue());

        FileSerialization.<ArrayList>serialize(attributes, "save/Attributes");

        //Tree treeSer = new Tree(new Node(new Rule("ROOT", r, null)));
        //FileSerialization.copyTree(R, treeSer.getRoot());
        //FileSerialization.treeSerialize(treeSer.getRoot(), "save/TreeSer");


    }

    public static void load() {
        attributes = new ArrayList();
        attributes = FileSerialization.deserialize("save/Attributes");

        Tree treeDeser = FileSerialization.treeDeserialize("TreeSer");

        Attribute r = null;
        for (Attribute a : attributes) {
            if (a.getType() == Attribute.Type.ROOT) {
                r = a;
            }
        }
        tree = new TreeItem(new Rule("ROOT", r, null));

        FileSerialization.copyTree(treeDeser.getRoot(), tree);
    }

    /**
     * Add rule into source node.
     *
     * @return result node.
     */
    private static TreeItem addRule(TreeItem result, Rule.Operation operation, Rule... atr) {
        ((Rule) result.getValue()).setOperation(operation);

        //Adding attributes into result node. Ex: A1 -> Q1 && Q2 && Q3
        //Node resultNode = new Node(result);
        for (Rule i : atr)
            result.getChildren().add(new TreeItem(i));

        return result;
    }

    /**
     * Return node with specific text from node.
     *
     * @return result node.
     */
    private static TreeItem getNodeFromNodeWithTag(TreeItem source, String tag) {
        for (Object i : source.getChildren())
        {
            String itemTag = ((Rule) ((TreeItem) i).getValue()).getTag();
            if (itemTag.equals(tag)) {
                return (TreeItem) i;
            }
        }

        return null;
    }

    public static TreeItem<Rule> getTree() {
        return tree;
    }

    public static ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public static TreeItem getQuestion(TreeItem node) {
        Rule sourceRule = (Rule) node.getValue();

        //Tree example:
        //          A
        //         / \
        //        B   C
        //          / | \
        //         D  E  I
        //           / \
        //          H   F
        //

        int count = 0;
        while (count++ < 4)
            processAllRules(node);

        //If H && F is used then process E rule
        if (sourceRule.getAttribute().getType() == Attribute.Type.QUESTION) {// && sourceRule.getAttribute().isExist() != Attribute.IsExist.EXIST) { //Если узел Q
            return node;
        } else if (sourceRule.getAttribute().getType() == Attribute.Type.ATTRIBUTE ||
                sourceRule.getAttribute().getType() == Attribute.Type.TEMPORARY ||
                sourceRule.getAttribute().getType() == Attribute.Type.OBJECT) { //Если узел A || O || T

            for (Object i : node.getChildren()) {
                TreeItem ti = (TreeItem) i;
                Rule rule = (Rule) ti.getValue();

                if (!rule.isUsed() && sourceRule.getAttribute().isExist() != Attribute.IsExist.EXIST)
                    return getQuestion(ti);
            }
        }

        if (sourceRule.getAttribute().getType() == Attribute.Type.OBJECT && sourceRule.isUsed()) {
            return node;
        }

        return null;
    }

    private static void processAllRules(TreeItem node) {
        Rule rule = (Rule) node.getValue();

        if (rule.getOperation() == Rule.Operation.AND && checkEvenOneChildrenUsed(node) && !rule.isUsed()) {
            for (Object i : node.getChildren()) {
                TreeItem ti = (TreeItem) i;
                Rule irule = (Rule) ti.getValue();

                //if one of children is false -> all rules is false
                if (irule.getAttribute().isExist() == Attribute.IsExist.EXIST) {
                    if (!irule.getValue().equals(irule.getAttribute().getValue())) {
                        rule.setUsed(true);
                        rule.getAttribute().setValue(null);
                    }
                }
            }

            if (checkAllChildrenUsed(node) && !rule.isUsed()) {
                rule.setUsed(true);
                rule.getAttribute().setValue(rule.getValue());
            }
        }

        for (Object i : node.getChildren()) {
            TreeItem ti = (TreeItem) i;

            processAllRules(ti);
        }
    }

    private static boolean checkEvenOneChildrenUsed(TreeItem node) {
        boolean flag = false;

        for (Object i : node.getChildren()) {
            TreeItem ti = (TreeItem) i;
            Rule rule = (Rule) ti.getValue();

            if (rule.isUsed()) {
                flag = true;
                return flag;
            }
        }

        return flag;
    }

    private static boolean checkAllChildrenUsed(TreeItem node) {
        boolean flag = true;

        for (Object i : node.getChildren()) {
            TreeItem ti = (TreeItem) i;
            Rule rule = (Rule) ti.getValue();

            if (!rule.isUsed()) {
                flag = false;
                return flag;
            }
        }

        return flag;
    }

    public static TreeItem getPreferObject(TreeItem root) {
        int min = Integer.MAX_VALUE;
        TreeItem minTree = null;

        for (Object i : root.getChildren()) {
            TreeItem ti = (TreeItem) i;
            Rule rule = (Rule) ti.getValue();

            int cur = getQuestionsCountInTreeItem(ti);

            if (min > cur && rule.getAttribute().isExist() != Attribute.IsExist.EXIST) {
                min = cur;
                minTree = ti;
            }
        }

        return minTree;
    }

    static int tmpCount = 0;

    private static int getQuestionsCountInTreeItem(TreeItem node) {
        tmpCount = 0;

        getQuestionsCountInTreeItemRec(node);

        return tmpCount;
    }

    private static void getQuestionsCountInTreeItemRec(TreeItem node) {
        for (Object i : node.getChildren()) {
            TreeItem ti = (TreeItem) i;
            Rule rule = (Rule) ti.getValue();

            if (rule.getAttribute().getType() == Attribute.Type.QUESTION &&  //Если тип == вопрос
                    rule.getAttribute().getValue() == null)
                tmpCount++;

            if (!ti.getChildren().isEmpty() && rule.getAttribute().isExist() != Attribute.IsExist.EXIST) {
                getQuestionsCountInTreeItemRec(ti);
            }
        }
    }
}
