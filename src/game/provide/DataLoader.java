package game.provide;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DataLoader {
    private static final List<User> data = new ArrayList<>();

    public static List<User> getData() {
        data.sort(Comparator.comparingInt(left -> Integer.parseInt(left.getRounds())));
        return data;
    }

    public static boolean load() {
        if (!data.equals(new ArrayList<>()))return true;
        final List<String> lines = fileHandler();
        if (lines == null) return false;
        for (String line : lines) {
            if (line == null) continue;
            final List<String> splitted = Arrays.stream(line.split(";")).collect(Collectors.toList());
            if (splitted.size() != 2) return false;
            data.add(new User(splitted.get(0), splitted.get(1)));
        }
        return true;
    }

    private static List<String> fileHandler() {
        final List<String> returnd = new ArrayList<>();
        try {
            final File file = new File("data.csv");
            if (!file.exists()) return null;
            final Scanner in = new Scanner(file);
            in.useDelimiter("\n");
            while (in.hasNextLine()) {
                returnd.add(in.nextLine());
            }

        } catch (Exception e) {
            return returnd;
        }
        return returnd;
    }

    public static boolean save() {
        try {
            File file = new File("data.csv");
            if (!file.exists()) {
                if (!file.createNewFile()) return false;
            }
            try (FileWriter out = new FileWriter(file)) {
                for (User user : data) {
                    out.write(user.getName() + ';' + user.getRounds() + '\n');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void addUser(final User user) {
        if (data.isEmpty()) {
            data.add(user);
            return;
        }
        User usr=null;
        for (User curr : data) {
            if (curr.getName().equals(user.getName()))usr=curr;
        }
        if (usr==null)data.add(user);
        else if (Integer.parseInt(usr.getRounds())<Integer.parseInt(user.getRounds())){
            data.remove(usr);
            data.add(user);
        }
    }
    public static void delete(){
        File file = new File("data.csv");
        try{
            if (file.exists()) file.delete();
            data.removeAll(data);
        }catch (Exception ignored){
        }

    }
}
