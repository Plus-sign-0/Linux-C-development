package AES.AES;

import java.util.Scanner;

//import java.util.*;
public class LS_Test_AES{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.println("输入明文：");
        String a= in.nextLine();
        String a_padding=hanshu.Padding(a);

        //String b_key="2b7e151628aed2a6abf7158809cf4f3c";

        System.out.println("输入密钥：");
        String b= in.nextLine();
        String b_key=hanshu.StrToHexstr(b);
        System.out.println("密钥转为十六进制为：");
        System.out.println(b_key);
        /*System.out.println("密文为：");
        showMatrix(GetMatrix(b_key));*/
        String[][] key_expansion_matrix=new String[4][44];
        key_expansion_matrix=hanshu.key_expansion(b_key);

        //System.out.println("明文组数：");
        int group=a_padding.length()/16;
        //System.out.println("组数为："+group);


        System.out.println("明文填充后为:"+a_padding);
        String a_padding_hex=hanshu.StrToHexstr(a_padding);
        System.out.println("转化为十六进制为："+ a_padding_hex);
        String str="";
        //加密
        String[][] stateMatrix=new String[4][4];
        String[][] matrix=new String[4][4];
        String[][] byte_matrix=new String[4][4];
        String[][] shift_Matrix=new String[4][4];
        String[][] mix_shift_Matrix=new String[4][4];
        String[][] addkey_stateMatrix= new String[4][4];
        for(int i=0;i<group;i++){
            stateMatrix= hanshu.GetMatrix(a_padding_hex.substring(i*32,i*32+32));
           /* System.out.println("第"+i+"组状态矩阵为：");
            showMatrix(stateMatrix);*/
            for(int k=0;k<4;k++){
                for(int j=0;j<4;j++){
                    addkey_stateMatrix[k][j]=key_expansion_matrix[k][j];
                }
            }
            int line=4;
            
            matrix=hanshu.AddRoundKey(stateMatrix,addkey_stateMatrix);
            /*System.out.println("轮密钥加为：");
            showMatrix(matrix);*/
            for(int m=1;m<10;m++){
                //字节代换
                byte_matrix=hanshu.subByte(matrix);
                //行位移
                shift_Matrix=hanshu.shiftRows(byte_matrix);

                //列混合
                 mix_shift_Matrix=hanshu.mixColumns(shift_Matrix);
                 /*System.out.println("列混合后为：");
                 showMatrix(mix_shift_Matrix);*/

                //轮密钥加
                for(int k=0;k<4;k++){
                    for(int j=0;j<4;j++){
                        addkey_stateMatrix[k][j]=key_expansion_matrix[k][line+j];
                    }
                }
                /*System.out.println("第"+m+"轮密钥为：");
                showMatrix(addkey_stateMatrix);*/
                line+=4;
                matrix=hanshu.AddRoundKey(mix_shift_Matrix,addkey_stateMatrix);
                /*System.out.println("第"+m+"轮加密为：");
                showMatrix(matrix);*/
            }

            //第十轮
            //字节代换
            byte_matrix=hanshu.subByte(matrix);
            //行位移
            shift_Matrix=hanshu.shiftRows(byte_matrix);
             //轮密钥加
             for(int k=0;k<4;k++){
                for(int j=0;j<4;j++){
                    addkey_stateMatrix[k][j]=key_expansion_matrix[k][line+j];
                }
            }
            /*System.out.println("第十轮密钥为：");
            showMatrix(addkey_stateMatrix);*/
            line+=4;
            matrix=hanshu.AddRoundKey(shift_Matrix,addkey_stateMatrix);
            /*System.out.println("第10轮加密为:");
            showMatrix(matrix);*/
            str+=hanshu.Matrix_To_Hexstr(matrix);
        }
        System.out.println("加密后为密文:"+str);

        //解密
        String str_1="";
        for(int i=0;i<group;i++){
            stateMatrix= hanshu.GetMatrix(str.substring(i*32,i*32+32));
            /*System.out.println("第"+i+"组密钥轮状态矩阵为：");
            showMatrix(stateMatrix);*/
            int line=40;
            for(int k=0;k<4;k++){
                for(int j=0;j<4;j++){
                    addkey_stateMatrix[k][j]=key_expansion_matrix[k][line+j];
                }
            }
            /*System.out.println("第0轮密钥为:");
            showMatrix(addkey_stateMatrix);*/

            matrix=hanshu.AddRoundKey(stateMatrix,addkey_stateMatrix);
            /*System.out.println("轮密钥加为：");
            showMatrix(matrix);*/
            for(int m=1;m<10;m++){
                //逆行位移
                shift_Matrix=hanshu.inverse_ShiftRows(matrix);
                //逆字节代换
                byte_matrix=hanshu.inverse_subByte(shift_Matrix);
                /*System.out.println("第"+m+"轮解密为(逆字节代换):");
                showMatrix(byte_matrix);*/
                //轮密钥加
                line-=4;
                for(int k=0;k<4;k++){
                    for(int j=0;j<4;j++){
                        addkey_stateMatrix[k][j]=key_expansion_matrix[k][line+j];
                    }
                }
                /*System.out.println("第"+m+"轮密钥为：");
                showMatrix(addkey_stateMatrix);*/
                String[][] addmatrix=hanshu.AddRoundKey(shift_Matrix,addkey_stateMatrix);
                //逆列混合
                matrix=hanshu.inverse_mixColumns(addmatrix);
            }

            //第十轮
            line-=4;
            //System.out.println("第10轮解密为:");
            //逆行位移
            shift_Matrix=hanshu.inverse_ShiftRows(matrix);
            //逆字节代换
            byte_matrix=hanshu.inverse_subByte(shift_Matrix);
            /*System.out.println("第十轮解密为(逆字节代换):");
                showMatrix(byte_matrix);*/
             //轮密钥加
             for(int k=0;k<4;k++){
                for(int j=0;j<4;j++){
                    addkey_stateMatrix[k][j]=key_expansion_matrix[k][line+j];
                }
            }
            /*System.out.println("第十轮密钥为：");
            showMatrix(addkey_stateMatrix);*/
            matrix=hanshu.AddRoundKey(shift_Matrix,addkey_stateMatrix);
            /*System.out.println("状态矩阵为:");
            showMatrix(matrix);*/
            str_1+=hanshu.Matrix_To_Hexstr(matrix);
        }
        System.out.println("解密后为:"+hanshu.Hexstrtostr(str_1));


        in.close();
    }
}