package Java_Compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class Create_file {
    String text;
    public Create_file(String text) {
        this.text = text;
    }

    public void connect() {
        String Path = "D:\\idea java\\untitled\\src\\Java_Compiler\\file.java";
        File file = new File(Path);

        //检查文件是否存在
        if(file.exists()) {
            if(file.delete()){
                System.out.println("原文件命名重复，已被删除");
            }
            else
            {
                System.out.println("删除失败！！");
                throw new RuntimeException();
            }
        }
        //创建java文件
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(Path, StandardCharsets.UTF_8))){
            bw.write(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
