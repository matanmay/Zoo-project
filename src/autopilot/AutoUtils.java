package autopilot;

import java.io.FileOutputStream;
import java.io.IOException;

public final class AutoUtils {

    public static void makeFile(String fileName,String data) {

        //get data bytes
        byte[] bytes = data.getBytes();

        //make file output stream
        try(FileOutputStream out
                    = new FileOutputStream(fileName)){
            //write to disk
            out.write(bytes);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
