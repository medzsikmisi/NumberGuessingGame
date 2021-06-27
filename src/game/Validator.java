package game;

import java.io.File;

public class Validator {
    public static boolean validate(){
        final File img=new File("/img/cubes.png");
        final File big=new File("/img/cubes_big.png");
        return img.exists()&&big.exists();
    }
}
