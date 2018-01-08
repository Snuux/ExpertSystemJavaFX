package Data;

import Data.Serialization.FileSerialization;
import Data.Serialization.Tree;
import UI.Controller.MainController;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;

public class DataManager {
    private static TreeItem<Rule> tree;
    private static ArrayList<Attribute> attributes;

    public static void test() {
        saveBase();
        //load();
    }

    private static void addAllAttributes(Attribute... atrs) {
        for (Attribute a : atrs) {
            attributes.add(a);
        }
    }

    public static void saveBase() {
        Attribute<Boolean> r = new Attribute<>("Корень дерева", null, Attribute.Type.ROOT);

        Attribute<String>  a1   = new Attribute<>("Жанр", null, Attribute.Type.ATTRIBUTE);
        Attribute<Integer> q11  = new Attribute<>("В книге должен быть динамичный сюжет?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q12  = new Attribute<>("Вы хотите расслабиться при чтении книги?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q21  = new Attribute<>("Вы хотите приобрести богатство для души?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q22  = new Attribute<>("Вам интересен вопрос, зачем мы живем?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q31  = new Attribute<>("Хотите ли вы приобрести новые знания?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q32  = new Attribute<>("Вам необходима книга для учебы/работы?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q41  = new Attribute<>("Чувствуете ли вы, что вам необходимо поменяться (в лучшую сторону)?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q42  = new Attribute<>("У вас есть проблемы в бытовой жизни или в бизнесе?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<String>  a2   = new Attribute<>("Язык оригинала", null, Attribute.Type.ATTRIBUTE);
        Attribute<Integer> q51  = new Attribute<>("Вам больше нравится оригинальный авторский слог?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q52  = new Attribute<>("Вы ярый патриот?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q61  = new Attribute<>("В данный момент вы учите иностранный язык?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q62  = new Attribute<>("Вы интересуетесь зарубежной культурой?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<String>  a3   = new Attribute<>("Тип обложки", null, Attribute.Type.ATTRIBUTE);
        Attribute<Integer> q71  = new Attribute<>("Вы храние книги далеко в ящике?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q72  = new Attribute<>("Хотите ли вы сэкономить на внешнем виде книги?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q81  = new Attribute<>("Вы хотите выставить книгу на всеобщее обозрение?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q82  = new Attribute<>("Вам хочется потратить больше денег, чтобы сделать подарок приятнее?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<String>  a4   = new Attribute<>("Наличие международных премий", null, Attribute.Type.ATTRIBUTE);
        Attribute<Integer> q91  = new Attribute<>("Вы хорошо относитесь к профессиональной критике?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q92  = new Attribute<>("Для вас важно, чтобы книга имела определенный статус среди людей?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q101 = new Attribute<>("Для вас оценка посторонних значима?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q102 = new Attribute<>("Вы хорошо относитесь к премиям и считаете их объективными?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<String>  a5   = new Attribute<>("Год публикации", null, Attribute.Type.ATTRIBUTE);
        Attribute<Integer> q111 = new Attribute<>("Вы больше хотите читать про современность?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q112 = new Attribute<>("Вас интересуют текущие тренды?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q121 = new Attribute<>("Вам больше нравится классические произведения?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q122 = new Attribute<>("Хотите ли вы повысить свой уровень грамотности?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<String>  a6   = new Attribute<>("Кол-во томов", null, Attribute.Type.ATTRIBUTE);
        Attribute<Integer> q131 = new Attribute<>("У вас много свободного времени?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q132 = new Attribute<>("Вы хотите на долго погружаться в действие книги?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q141 = new Attribute<>("Вы занятой человек?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q142 = new Attribute<>("Вам больше нравится сжатое повествование?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<String>  a7   = new Attribute<>("Издательство", null, Attribute.Type.ATTRIBUTE);
        Attribute<Integer> q151 = new Attribute<>("Должна ли книга быть бестеллером?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q152 = new Attribute<>("Вам важен опыт жизни людей зарубежом?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q161 = new Attribute<>("Являетесь ли вы русофилом?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q162 = new Attribute<>("Вам интересно читать про своих соотечественников?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<String>  a8   = new Attribute<>("Возрастная аудитория", null, Attribute.Type.ATTRIBUTE);
        Attribute<Integer> q171 = new Attribute<>("Вы знаете точный возраст читателя книги?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q172 = new Attribute<>("Книга должна содержать сложные определения?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q181 = new Attribute<>("Книга может иметь элементы сказочности?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q182 = new Attribute<>("В книге должно побеждать добро?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q191 = new Attribute<>("Вам нравятся ужасы в книгах?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q192 = new Attribute<>("Вы хотите, чтобы книга содержала провакационные темы?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<String>  a9   = new Attribute<>("Форма изложения", null, Attribute.Type.ATTRIBUTE);
        Attribute<Integer> q201 = new Attribute<>("Книга должна быть на какую-нибудь профессиональную тему?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q202 = new Attribute<>("Умеете ли вы читать между строк?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q211 = new Attribute<>("Вы пишете стихи?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);
        Attribute<Integer> q212 = new Attribute<>("Вам нравится рэп?", null, Attribute.Type.QUESTION, Attribute.ValueType.TRUE_FALSE);


        Attribute<String> o = new Attribute<>("Объект", null, Attribute.Type.OBJECT);

        attributes = new ArrayList<>();
        addAllAttributes(r, q11, q12, q21, q22, q31, q32, q41, q42, q51, q52, q61, q62, q71, q72, q81, q82, q91, q92,
                q101, q102, q111, q112, q121, q122, q131, q132, q141, q142, q151, q152, q161, q162, q171, q172,
                q181, q182, q191, q192, q201, q202, q211, q212, o, a1, a2, a3, a4, a5, a6, a7, a8, a9);

        TreeItem<Rule> R = new TreeItem<>(new Rule<>("ROOT", r, null));
        tree = R;

        addRule(R, null, new Rule<>("O1", o, "Властелин колец	"));
        addRule(R, null, new Rule<>("O2", o, "Гарри Поттер"));
        addRule(R, null, new Rule<>("O3", o, "Тихий Дон"));
        addRule(R, null, new Rule<>("O4", o, "Евгений Онегин"));
        addRule(R, null, new Rule<>("O5", o, "Искусство войны"));
        //addRule(R, null, new Rule("O6", o, "Фауст"));
        //addRule(R, null, new Rule("O7", o, "Как доводить дела до цонца"));
        //addRule(R, null, new Rule("O8", o, "Как завоевывть друзей"));
        //addRule(R, null, new Rule("O9", o, "Донцова"));
        //addRule(R, null, new Rule("O10", o, "Сумерки"));
        //addRule(R, null, new Rule("O11", o, "Чистый код"));
        //addRule(R, null, new Rule("O12", o, "Лекции по высшей математике"));

        TreeItem<Rule> O1 = addRule(getNodeFromNodeWithTag(R, "O1"), Rule.Operation.AND,
                new Rule<>("A1", a1, "Художественная"),
                new Rule<>("A2", a2, "Другой"),
                new Rule<>("A3", a3, "Подарочная"),
                new Rule<>("A4", a4, "Есть"),
                new Rule<>("A5", a5, "Классическая"),
                new Rule<>("A6", a6, "Многотомник"),
                new Rule<>("A7", a7, "Зарубежное"),
                new Rule<>("A8", a8, "Для всех"),
                new Rule<>("A9", a9, "Проза"));

        addRule(getNodeFromNodeWithTag(O1, "A1"), Rule.Operation.AND, new Rule<>("Q11", q11, 1), new Rule<>("Q12", q12, 1));
        addRule(getNodeFromNodeWithTag(O1, "A2"), Rule.Operation.AND, new Rule<>("Q61", q61, 1), new Rule<>("Q62", q62, 1));
        addRule(getNodeFromNodeWithTag(O1, "A3"), Rule.Operation.AND, new Rule<>("Q81", q81, 1), new Rule<>("Q82", q82, 1));
        addRule(getNodeFromNodeWithTag(O1, "A4"), Rule.Operation.AND, new Rule<>("Q91", q91, 1), new Rule<>("Q92", q92, 1));
        addRule(getNodeFromNodeWithTag(O1, "A5"), Rule.Operation.AND, new Rule<>("Q121", q121, 1), new Rule<>("Q122", q122, 1));
        addRule(getNodeFromNodeWithTag(O1, "A6"), Rule.Operation.AND, new Rule<>("Q141", q141, 1), new Rule<>("Q142", q142, 1));
        addRule(getNodeFromNodeWithTag(O1, "A7"), Rule.Operation.AND, new Rule<>("Q151", q151, 1), new Rule<>("Q152", q152, 1));
        addRule(getNodeFromNodeWithTag(O1, "A8"), Rule.Operation.AND, new Rule<>("Q171", q171, 1), new Rule<>("Q172", q172, 1));
        addRule(getNodeFromNodeWithTag(O1, "A9"), Rule.Operation.AND, new Rule<>("Q201", q201, 1), new Rule<>("Q202", q202, 1));

        TreeItem<Rule> O2 = addRule(getNodeFromNodeWithTag(R, "O2"), Rule.Operation.AND,
                new Rule<>("A1", a1, "Художественная"),
                new Rule<>("A2", a2, "Другой"),
                new Rule<>("A3", a3, "Подарочная"),
                new Rule<>("A4", a4, "Есть"),
                new Rule<>("A5", a5, "Современная"),
                new Rule<>("A6", a6, "Многотомник"),
                new Rule<>("A7", a7, "Зарубежное"),
                new Rule<>("A8", a8, "Для детей"),
                new Rule<>("A9", a9, "Проза"));

        addRule(getNodeFromNodeWithTag(O2, "A1"), Rule.Operation.AND, new Rule<>("Q11", q11, 1), new Rule<>("Q12", q12, 1));
        addRule(getNodeFromNodeWithTag(O2, "A2"), Rule.Operation.AND, new Rule<>("Q61", q61, 1), new Rule<>("Q62", q62, 1));
        addRule(getNodeFromNodeWithTag(O2, "A3"), Rule.Operation.AND, new Rule<>("Q81", q81, 1), new Rule<>("Q82", q82, 1));
        addRule(getNodeFromNodeWithTag(O2, "A4"), Rule.Operation.AND, new Rule<>("Q91", q91, 1), new Rule<>("Q92", q92, 1));
        addRule(getNodeFromNodeWithTag(O2, "A5"), Rule.Operation.AND, new Rule<>("Q111", q111, 1), new Rule<>("Q112", q112, 1));
        addRule(getNodeFromNodeWithTag(O2, "A6"), Rule.Operation.AND, new Rule<>("Q141", q141, 1), new Rule<>("Q142", q142, 1));
        addRule(getNodeFromNodeWithTag(O2, "A7"), Rule.Operation.AND, new Rule<>("Q151", q151, 1), new Rule<>("Q152", q152, 1));
        addRule(getNodeFromNodeWithTag(O2, "A8"), Rule.Operation.AND, new Rule<>("Q181", q181, 1), new Rule<>("Q182", q182, 1));
        addRule(getNodeFromNodeWithTag(O2, "A9"), Rule.Operation.AND, new Rule<>("Q201", q201, 1), new Rule<>("Q202", q202, 1));

        TreeItem<Rule> O3 = addRule(getNodeFromNodeWithTag(R, "O3"), Rule.Operation.AND,
                new Rule<>("A1", a1, "Художественная"),
                new Rule<>("A2", a2, "Русский"),
                new Rule<>("A3", a3, "Обычная"),
                new Rule<>("A4", a4, "Нет"),
                new Rule<>("A5", a5, "Классическая"),
                new Rule<>("A6", a6, "Многотомник"),
                new Rule<>("A7", a7, "Российское"),
                new Rule<>("A8", a8, "Для взрослых"),
                new Rule<>("A9", a9, "Проза"));

        addRule(getNodeFromNodeWithTag(O3, "A1"), Rule.Operation.AND, new Rule<>("Q11", q11, 1), new Rule<>("Q12", q12, 1));
        addRule(getNodeFromNodeWithTag(O3, "A2"), Rule.Operation.AND, new Rule<>("Q51", q51, 1), new Rule<>("Q52", q52, 1));
        addRule(getNodeFromNodeWithTag(O3, "A3"), Rule.Operation.AND, new Rule<>("Q71", q71, 1), new Rule<>("Q72", q72, 1));
        addRule(getNodeFromNodeWithTag(O3, "A4"), Rule.Operation.AND, new Rule<>("Q101", q101, 1), new Rule<>("Q102", q102, 1));
        addRule(getNodeFromNodeWithTag(O3, "A5"), Rule.Operation.AND, new Rule<>("Q121", q121, 1), new Rule<>("Q122", q122, 1));
        addRule(getNodeFromNodeWithTag(O3, "A6"), Rule.Operation.AND, new Rule<>("Q141", q141, 1), new Rule<>("Q142", q142, 1));
        addRule(getNodeFromNodeWithTag(O3, "A7"), Rule.Operation.AND, new Rule<>("Q161", q161, 1), new Rule<>("Q162", q162, 1));
        addRule(getNodeFromNodeWithTag(O3, "A8"), Rule.Operation.AND, new Rule<>("Q191", q191, 1), new Rule<>("Q192", q192, 1));
        addRule(getNodeFromNodeWithTag(O3, "A9"), Rule.Operation.AND, new Rule<>("Q201", q201, 1), new Rule<>("Q202", q202, 1));

        TreeItem<Rule> O4 = addRule(getNodeFromNodeWithTag(R, "O4"), Rule.Operation.AND,
                new Rule<>("A1", a1, "Художественная"),
                new Rule<>("A2", a2, "Русский"),
                new Rule<>("A3", a3, "Обычная"),
                new Rule<>("A4", a4, "Нет"),
                new Rule<>("A5", a5, "Классическая"),
                new Rule<>("A6", a6, "Однотомник"),
                new Rule<>("A7", a7, "Российское"),
                new Rule<>("A8", a8, "Для взрослых"),
                new Rule<>("A9", a9, "Поэзия"));

        addRule(getNodeFromNodeWithTag(O4, "A1"), Rule.Operation.AND, new Rule<>("Q11", q11, 1), new Rule<>("Q12", q12, 1));
        addRule(getNodeFromNodeWithTag(O4, "A2"), Rule.Operation.AND, new Rule<>("Q51", q51, 1), new Rule<>("Q52", q52, 1));
        addRule(getNodeFromNodeWithTag(O4, "A3"), Rule.Operation.AND, new Rule<>("Q71", q71, 1), new Rule<>("Q72", q72, 1));
        addRule(getNodeFromNodeWithTag(O4, "A4"), Rule.Operation.AND, new Rule<>("Q101", q101, 1), new Rule<>("Q102", q102, 1));
        addRule(getNodeFromNodeWithTag(O4, "A5"), Rule.Operation.AND, new Rule<>("Q121", q121, 1), new Rule<>("Q122", q122, 1));
        addRule(getNodeFromNodeWithTag(O4, "A6"), Rule.Operation.AND, new Rule<>("Q131", q131, 1), new Rule<>("Q132", q132, 1));
        addRule(getNodeFromNodeWithTag(O4, "A7"), Rule.Operation.AND, new Rule<>("Q161", q161, 1), new Rule<>("Q162", q162, 1));
        addRule(getNodeFromNodeWithTag(O4, "A8"), Rule.Operation.AND, new Rule<>("Q191", q191, 1), new Rule<>("Q192", q192, 1));
        addRule(getNodeFromNodeWithTag(O4, "A9"), Rule.Operation.AND, new Rule<>("Q211", q211, 1), new Rule<>("Q212", q212, 1));

        TreeItem<Rule> O5 = addRule(getNodeFromNodeWithTag(R, "O5"), Rule.Operation.AND,
                new Rule<>("A1", a1, "Философская"),
                new Rule<>("A2", a2, "Другой"),
                new Rule<>("A3", a3, "Подарочная"),
                new Rule<>("A4", a4, "Нет"),
                new Rule<>("A5", a5, "Классическая"),
                new Rule<>("A6", a6, "Однотомник"),
                new Rule<>("A7", a7, "Зарубежное"),
                new Rule<>("A8", a8, "Для взрослых"),
                new Rule<>("A9", a9, "Проза"));

        addRule(getNodeFromNodeWithTag(O5, "A1"), Rule.Operation.AND, new Rule<>("Q21", q21, 1), new Rule<>("Q22", q22, 1));
        addRule(getNodeFromNodeWithTag(O5, "A2"), Rule.Operation.AND, new Rule<>("Q61", q61, 1), new Rule<>("Q62", q62, 1));
        addRule(getNodeFromNodeWithTag(O5, "A3"), Rule.Operation.AND, new Rule<>("Q81", q81, 1), new Rule<>("Q82", q82, 1));
        addRule(getNodeFromNodeWithTag(O5, "A4"), Rule.Operation.AND, new Rule<>("Q101", q101, 1), new Rule<>("Q102", q102, 1));
        addRule(getNodeFromNodeWithTag(O5, "A5"), Rule.Operation.AND, new Rule<>("Q121", q121, 1), new Rule<>("Q122", q122, 1));
        addRule(getNodeFromNodeWithTag(O5, "A6"), Rule.Operation.AND, new Rule<>("Q131", q131, 1), new Rule<>("Q132", q132, 1));
        addRule(getNodeFromNodeWithTag(O5, "A7"), Rule.Operation.AND, new Rule<>("Q151", q151, 1), new Rule<>("Q152", q152, 1));
        addRule(getNodeFromNodeWithTag(O5, "A8"), Rule.Operation.AND, new Rule<>("Q191", q191, 1), new Rule<>("Q192", q192, 1));
        addRule(getNodeFromNodeWithTag(O5, "A9"), Rule.Operation.AND, new Rule<>("Q201", q201, 1), new Rule<>("Q202", q202, 1));

        //FileSerialization.<ArrayList>serialize(attributes, "save/Attributes");
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
    private static TreeItem<Rule> addRule(TreeItem<Rule> result, Rule.Operation operation, Rule... atr) {
        result.getValue().setOperation(operation);

        //Adding attributes into result node. Ex: A1 -> Q1 && Q2 && Q3
        //Node resultNode = new Node(result);
        for (Rule i : atr)
            result.getChildren().add(new TreeItem<>(i));

        return result;
    }

    /**
     * Return node with specific text from node.
     *
     * @return result node.
     */
    private static TreeItem<Rule> getNodeFromNodeWithTag(TreeItem<Rule> source, String tag) {
        for (TreeItem<Rule> i : source.getChildren()) {
            String itemTag = i.getValue().getTag();
            if (itemTag.equals(tag))
                return i;
        }
        return null;
    }

    public static TreeItem<Rule> getTree() {
        return tree;
    }

    public static ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public static TreeItem<Rule> getQuestion(TreeItem<Rule> node) {
        //получаем правило
        Rule sourceRule = node.getValue();

        //обрабатываем всё дерево
        processAllRules(tree);

        //-----Tree example-----//
        //          A           //
        //         / \          //
        //        B   C         //
        //          / | \       //
        //         D  E  I      //
        //           / \        //
        //          H   F       //
        //////////////////////////
        //Проверяем правило, если оно ещё не отработало, а аттрибут не является вопросом, то смотрим на
        //и если они не отработали, то запускаем функцию рекурсивно для них
        if (!sourceRule.isUsed() && sourceRule.getAttribute().getType() != Attribute.Type.QUESTION) {
            for (TreeItem<Rule> innerNode : node.getChildren()) {
                Rule rule = innerNode.getValue();
                if (!rule.isUsed()) {
                    return getQuestion(innerNode);
                }
            }
        }//Если правило устанавливает значение вопроса и не было использовано, то возвращаем вопрос
        else if (!sourceRule.isUsed() && sourceRule.getAttribute().getType() == Attribute.Type.QUESTION) {
            return node;
        }

        if (sourceRule.getAttribute().getType() == Attribute.Type.OBJECT && sourceRule.isUsed()) {
            if (sourceRule.isEqualToAttribute()) {
                return node;
            } else if (getPreferObject(tree) != null) {
                return getQuestion(getPreferObject(tree));
            }
        }

//        return null;
        return new TreeItem<>(new Rule<>("EMPTY", null, null));
    }

    private static void processAllRules(TreeItem<Rule> node) {
        //Проходим по всем дочерним правилам, отрабатываем те, что возможно
        for (TreeItem<Rule> innerNode : node.getChildren()) {
            Rule innerRule = innerNode.getValue();
            if (!innerRule.isUsed())
                processAllRules(innerNode);
        }

        //Получаем текущее правило
        Rule rule = node.getValue();

        //Обрабатываем случай операции && - AND
        //Если хоть один дочерний элемент известен и не соответствует правилу,
        //то текущее правило тоже не работает
        if (rule.getOperation() == Rule.Operation.AND && checkEvenOneChildrenUsed(node) && !rule.isUsed()) {
            for(TreeItem<Rule> innerItem : node.getChildren()) {
                Rule innerRule = innerItem.getValue();
                if (innerRule.isUsed() && !innerRule.isEqualToAttribute()) {
                    rule.setUsed(true);
                    break;
                }
            }
        }

        //Обрабатываем случай операции && - OR
        //Если хоть один дочерний элемент известен и соответствует правилу,
        //то текущее правило должно отработать
        if (rule.getOperation() == Rule.Operation.OR && checkEvenOneChildrenUsed(node) && !rule.isUsed()) {
            for(TreeItem<Rule> innerItem : node.getChildren()) {
                Rule innerRule = innerItem.getValue();
                if (innerRule.isUsed() && innerRule.isEqualToAttribute()) {
                    rule.setUsed(true);
                    rule.setValueToAttribute();
                    break;
                }
            }
        }

        //Если на вопрос был дан ответ в другой ветке, то отмечаем правило, как отработанное
        if (rule.getAttribute().getType() == Attribute.Type.QUESTION && !rule.isUsed() && rule.getAttribute().hasValue()) {
            rule.setUsed(true);
        }

        //Даём текущему правилу отработать, если:
        // 1. Все дочерние правила отработали
        // 2. Предыдущая проверка не нашла атрибут, который не соответствует правилу
        // 3. У текущего атрибута нет значения
        if (!rule.isUsed() && rule.getOperation() == Rule.Operation.AND && checkAllChildrenUsed(node)) {
            rule.setUsed(true);
            rule.setValueToAttribute();
        }
    }

    /**
     * Функция, проверяющая отработало ли хоть одно дочернее правило
     * @param node - узел, для которого проводится проверка
     * @return true - если хоть одно дочернее правило отработало
     * false - если не отработало ни одно правило
     */
    private static boolean checkEvenOneChildrenUsed(TreeItem<Rule> node) {
        for (TreeItem<Rule> innerItem : node.getChildren())
            if (innerItem.getValue().isUsed())
                return true;
        return false;
    }

    /**
     * Функция, проверяющая отработали ли все дочерние правила
     * @param node - узел, для которого проводится проверка
     * @return true - если отработали все дочерние правила
     * false - если хоть одно дочернее правило не отработало
     */
    private static boolean checkAllChildrenUsed(TreeItem<Rule> node) {
        for(TreeItem<Rule> innerItem : node.getChildren())
            if (!innerItem.getValue().isUsed())
                return false;
        return true;
    }

    /**
     * Функция, возвращающая объект, в ветке которого осталось минимум неотвеченных вопросов
     * @param root - корневой узел, в котором осуществляется поиск объектов
     * @return дерево с минимальным количеством неотвеченных вопросов
     */
    public static TreeItem<Rule> getPreferObject(TreeItem<Rule> root) {
        int min = Integer.MAX_VALUE;
        TreeItem<Rule> minTree = null;

        for (TreeItem<Rule> i : root.getChildren()) {
            Rule rule = i.getValue();

            if (!rule.isUsed()) {
                int current = getQuestionsCountInTreeItem(i);

                if (min > current) {
                    min = current;
                    minTree = i;
                }
            }
        }

        return minTree;
    }

    private static int getQuestionsCountInTreeItem(TreeItem<Rule> node) {
        int tmpCount = 0;
        for (TreeItem<Rule> i : node.getChildren()) {
            Rule rule = i.getValue();

            if (rule.getAttribute().getType() == Attribute.Type.QUESTION && rule.getAttribute().getValue() == null)
                tmpCount++;

            if (!i.getChildren().isEmpty() && !rule.getAttribute().hasValue()) {
                getQuestionsCountInTreeItem(i);
            }
        }
        return tmpCount;
    }
}
