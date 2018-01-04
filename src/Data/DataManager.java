package Data;

import Data.Serialization.FileSerialization;
import Data.Serialization.Node;
import Data.Serialization.Tree;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;

public class DataManager {
    private static TreeItem<Item> tree;
    private static ArrayList<Attribute> attributes;

    public static void test() {
        saveBase();
        //load();
    }

    public static void saveBase() {
        Attribute<Boolean> r = new Attribute("Корень дерева", null, Attribute.Type.ROOT);

        Attribute<Boolean> q1 = new Attribute("Вы хотите расслабиться при чтении книги?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
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

        TreeItem<Item> R = new TreeItem (new Item("ROOT", r, null));
        tree = R;
        //tree.getChildren().add(R);

        addRule(R, null, new Item("O1", o, "Властелин колец"));
        addRule(R, null, new Item("O2", o, "Фауст"));
        TreeItem<Item> O1 = addRule(getNodeFromNodeWithTag(R, "O1"), Item.Operation.OR, new Item("A1", a1, "Художественная"), new Item("A2", a2, "Другой"));
        TreeItem<Item> A1 = addRule(getNodeFromNodeWithTag(O1, "A1"), Item.Operation.AND, new Item("Q1", q1, true), new Item("Q2", q2, true));
        TreeItem<Item> A2 = addRule(getNodeFromNodeWithTag(O1, "A2"), Item.Operation.AND, new Item("Q3", q3, true), new Item("Q4", q4, true));

        TreeItem<Item> O2 = addRule(getNodeFromNodeWithTag(R, "O2"), Item.Operation.AND, new Item("A1", a1, "Философский"), new Item("A2", a2, "Другой"), new Item("A3", a3, "Подарочный"));
        TreeItem<Item> A3 = addRule(getNodeFromNodeWithTag(O2, "A3"), Item.Operation.AND, new Item("Q5", q5, true), new Item("Q6", q6, true));

        TreeItem<Item> A11 = addRule(getNodeFromNodeWithTag(O2, "A1"), Item.Operation.AND, new Item("Q1", q1, true), new Item("Q2", q2, true), new Item("Q3", q3, true));
        TreeItem<Item> A22 = addRule(getNodeFromNodeWithTag(O2, "A2"), Item.Operation.AND, new Item("Q3", q3, true), new Item("Q4", q4, true));

        System.out.println(getQuestionsCountInTreeItem(O1));
        System.out.println(getQuestionsCountInTreeItem(O2));

        System.out.println(getQuestion(R.getChildren().get(0).getChildren().get(0).getChildren().get(0)).getValue());

        FileSerialization.<ArrayList>serialize(attributes, "save/Attributes");

        Tree treeSer = new Tree(new Node(new Item("ROOT", r, null)));
        FileSerialization.copyTree(R, treeSer.getRoot());
        FileSerialization.treeSerialize(treeSer.getRoot(), "save/TreeSer");


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
        tree = new TreeItem(new Item("ROOT", r, null));

        FileSerialization.copyTree(treeDeser.getRoot(), tree);
    }

    /**
     * Add rule into source node.
     *
     * @return result node.
     */
    private static TreeItem addRule(TreeItem result, Item.Operation operation, Item ... atr) {
        ((Item) result.getValue()).setOperation(operation);

        //Adding attributes into result node. Ex: A1 -> Q1 && Q2 && Q3
        //Node resultNode = new Node(result);
        for (Item i : atr)
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
            String itemTag = ((Item) ((TreeItem) i).getValue()).getTag();
            if (itemTag.equals(tag)) {
                return (TreeItem) i;
            }
        }

        return null;
    }

    public static TreeItem<Item> getTree() {
        return tree;
    }

    public static ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public static TreeItem getQuestion(TreeItem sourceNode) {
        Item sourceItem = (Item) sourceNode.getValue();

        /*if (!sourceItem.isAsked()) { //Если source правило ещё не приминили
            for (Object i : sourceNode.getChildren()) {
                TreeItem ti = (TreeItem) i;
                Item item = (Item) ti.getValue();

                getQuestion(ti);

                if (!item.isAsked()) { //Если правило-сын ещё не приминили
                    if (item.getAttribute().getType() == Attribute.Type.QUESTION) {
                        item.setAsked(true);
                        return ti;
                    }
                    else {
                        getQuestion(ti);
                        return ti;
                    }
                    //getQuestion(ti);
                    //return null;
                } else { //Если правило-сын ещё не приминили

                }
            }

        } else {
            //Если source правило применили
        }*/

        return sourceNode;
    }

    static int tmpCount = 0;
    public static int getQuestionsCountInTreeItem(TreeItem sourceNode) {
        tmpCount = 0;

        getQuestionsCountInTreeItemRec(sourceNode);

        return tmpCount;
    }

    private static void getQuestionsCountInTreeItemRec(TreeItem sourceNode) {
        for (Object i : sourceNode.getChildren()) {
            TreeItem ti = (TreeItem) i;
            Item item = (Item) ti.getValue();

            if (item.getAttribute().getType() == Attribute.Type.QUESTION &&  //Если тип == вопрос
                item.getAttribute().getValue() == null)
                tmpCount++;

            if (!ti.getChildren().isEmpty() && !item.getAttribute().hasValue()) {
                getQuestionsCountInTreeItemRec(ti);
            }
        }
    }
}
