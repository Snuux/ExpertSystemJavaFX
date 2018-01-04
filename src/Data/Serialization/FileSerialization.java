package Data.Serialization;

import Data.Item;
import javafx.scene.control.TreeItem;

import java.io.*;
import java.util.ArrayList;

public class FileSerialization<T> {

    static private int count = 0;

    private static void recSer(Node node, String filename) throws IOException {
        for (Object i : node.getChildren()) {
            Node s = (Node) i;

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename + String.format ("%03d", count++)));
            oos.writeObject(s);

            if (!s.getChildren().isEmpty())
                recSer(s, filename);
        }
    }

    public static void treeSerialize(Node node, String filename) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename + String.format ("%03d", count++)));
            oos.writeObject(node);

            recSer(node, filename);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static ArrayList<String> getAllFilesFromDirectoryWithName(String directory, String name) {
        ArrayList<String> results = new ArrayList<String>();
        File[] files = new File(directory).listFiles();

        for (File file : files) {
            if (file.isFile() && file.getName().contains(name)) {
                results.add(file.getName());
            }
        }

        return results;
    }

    public static Tree treeDeserialize(String filename) {
        Node node = null;
        ObjectInputStream ois;
        int descCount = count - 1; //файлы с конца

        try {
            ArrayList<String> allFiles = getAllFilesFromDirectoryWithName("save", filename);

            for (int i = 0; i < allFiles.size(); i++) {
                String str = allFiles.get(i);
                ois = new ObjectInputStream(new FileInputStream("save/" + str));
                Node n = (Node) ois.readObject();
                if (((Item)n.getData()).getTag().equals("ROOT"))
                    node = n;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Tree treeDeser = new Tree(node);
        return treeDeser;
    }




    public static <T> T deserialize(String filename) {
        T object = null;

        try (ObjectInputStream ois
                     = new ObjectInputStream(new FileInputStream(filename))) {

            object = (T) ois.readObject();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return object;
    }

    public static <T> void serialize(T object, String path) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(path))) {

            oos.writeObject(object);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void copy(Node root, TreeItem iRoot)
    {
        for (Object i : root.getChildren())
        {
            Node str = (Node) i;
            TreeItem<Node> treeItem = new TreeItem(str);
            iRoot.getChildren().add(treeItem);
            if (!((Node) i).getChildren().isEmpty()){
                copy((Node) i, treeItem);
            }
        }
    }

    public static <T1, T2> T2 copyTree(T1 t1, T2 t2)
    {
        if (t1 instanceof Node && t2 instanceof TreeItem)        {
            for (Object i : ((Node) t1).getChildren())
            {
                Node str = (Node) i;
                TreeItem<Node> treeItem = new TreeItem((Item)str.getData());
                ((TreeItem) t2).getChildren().add(treeItem);
                if (!((Node) i).getChildren().isEmpty()){
                    copyTree((Node) i, treeItem);
                }
            }
        }
        else if (t1 instanceof TreeItem && t2 instanceof Node){
            for (Object i : ((TreeItem) t1).getChildren())
            {
                TreeItem str = (TreeItem) i;
                Node<Item> node = new Node(str.getValue());
                ((Node) t2).getChildren().add(node);
                if (!((TreeItem) i).getChildren().isEmpty()){
                    copyTree(i, node);
                }
            }
        }

        return t2;
    }
}
