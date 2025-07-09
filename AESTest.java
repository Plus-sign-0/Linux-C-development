package AES;

import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;



/*
加密测试数据：
1
2b7e151628aed2a6abf7158809cf4f3c
heisahandsomeboy
*/

/*
解密测试数据：
2
2b7e151628aed2a6abf7158809cf4f3c
db1b8ee47a2158c69b5e99f6bcbbac8c

2
2b7e151628aed2a6abf7158809cf4f3c
654e352069ee2f4d09a70ed3ef7b1984
*/

public class AESTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 用户输入操作类型，1为加密，2为解密
        System.out.println("请输入要执行的操作，加密输入1，解密输入2：");
        int operation = scanner.nextInt();
        scanner.nextLine(); // 消耗换行符

        // 用户输入密钥
        System.out.println("输入16字节的密钥：");
        String keybord = scanner.nextLine();

        // 用户输入明文或密文
        String input = "";
        if (operation == 1) {
            // 加密操作
            System.out.println("输入明文：");
            input = scanner.next();
        } else if (operation == 2) {
            // 解密操作
            System.out.println("输入密文：");
            input = scanner.next();
        }

        // 创建加密和解密对象
        Encryption encryption = new Encryption();
        Decryption decryption = new Decryption();

        // 执行加密或解密操作
        if (operation == 1) {
            // 加密
            String paddedInput = AESTest.padding(input); // 对明文进行PKCS#7填充
            String[][] key=ByteSubstitution.GetMatrix(ByteSubstitution.StrToHexstr(keybord));
            String[][] encrypted = encryption.encrypt(input, paddedInput, 1, paddedInput.length(), Key_expansion.key_expansion_1(key,0));
            System.out.println("加密后的密文：");
            AESTest.printMatrix(encrypted); // 打印加密后的密文矩阵
        } else if (operation == 2) {
            // 解密
            String[][] key=ByteSubstitution.GetMatrix(ByteSubstitution.StrToHexstr(keybord));
            String[][] decrypted = decryption.decrypt(input, Key_expansion.key_expansion_1(key,0));
            System.out.println("解密后的明文：");
            String plaintext = AESTest.matrixToString(decrypted);
            System.out.println(ByteSubstitution.Hexstrtostr(plaintext, 32));// 将十六进制字符串转换为字符
        }

        //密钥扩展测试
        if (operation == 3) {

            //String s = ByteSubstitution.StrToHexstr(arr_str);
            System.out.println(keybord);
            String[][] ans=new String[4][4];
            ans=Key_expansion.Get(keybord);
            String[][] ans_key=new String[4][4];
            ans_key=Key_expansion.key_expansion_1(ans,0);

            for(int i=0;i<4;i++)
            {
                for(int j=0;j<4;j++)
                {
                    System.out.print(ans[i][j]+" ");
                }
                System.out.println();
            }

            for(int i=0;i<4;i++)
            {
                for(int j=0;j<4;j++)
                {
                    System.out.print(ans_key[i][j]+" ");
                }
                System.out.println();
            }

        }

        //列混合测试
        /*
        2b7e151628aed2a6abf7158809cf4f3c
        87 F2 4D 97
        6E 4C 90 EC
        46 E7 4A C3
        A6 8C D8 95
         */
        if(operation == 4){
            String[][] mix=new String[4][4];
            for(int i=0;i<4;i++)
                for(int j=0;j<4;j++)
                {
                    mix[i][j]=scanner.next();
                }
            mix = Mix.mixColumns(mix);
            printMatrix(mix);
        }

        //测试一轮操作是否成功
        /*
        0
        2b7e151628aed2a6abf7158809cf4f3c
        heisahandsomeboy
         */


        if(operation == 0)
        {
            System.out.println("输入明文：");
            Scanner in = new Scanner(System.in);
            String a= in.nextLine();

            String arr_a=padding(a);//填充a字符串
            String[][] key=ByteSubstitution.GetMatrix(ByteSubstitution.StrToHexstr(keybord));//密钥转4*4数组
            String[][] state = ByteSubstitution.GetMatrix(ByteSubstitution.StrToHexstr(arr_a));//arr_a转4*4数组
            String[][] encryption_a=encryption.encryption_1(state,0,key);//加密
            System.out.println("加密后的密文矩阵：");
            AESTest.printMatrix(encryption_a);

            String[][] decryption_a=decryption.decryption_1(encryption_a,0,key);
            System.out.println("加密后的密文矩阵：");
            AESTest.printMatrix(decryption_a);
            String plaintext = AESTest.matrixToString(decryption_a);
            System.out.println(ByteSubstitution.Hexstrtostr(plaintext, 32));
        }

    }

    //填充方法
    private static String padding(String str){
        int num = str.length()%16;
        if(num!=0){
            for(int i=0;i<16-num;i++)
                str+=(char)('a'+i);
        }
        return str;
    }
    // 将矩阵转换为字符串

    public static String matrixToString(String[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (String[] row : matrix) {
            for (String cell : row) {
                sb.append(cell);
            }
        }
        return sb.toString();
    }

    // 打印矩阵
    public static void printMatrix(String[][] matrix) {
        for (String[] row : matrix) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}