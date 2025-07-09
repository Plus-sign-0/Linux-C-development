package AES;
import java.util.*;

public class Transform{
    public static void main(String[] args){
        System.out.println("输入明文：");
        Scanner in = new Scanner(System.in);
        String a= in.nextLine();
        String c=Padding(a);
        int group=c.length()/16;
        int a_length=StrToHexstr(c).length()-(16-a.length()%16);
        System.out.println(a_length);
        System.out.println("填充后为:"+c);
        System.out.println("转化为十六进制为："+ StrToHexstr(c));
        String[][] stateMatrix= GetMatrix(StrToHexstr(c));
        System.out.println("状态矩阵为：");
        showMatrix(stateMatrix);

        System.out.println("字节代换后的矩阵为: ");
        showMatrix(subByte(stateMatrix));

        String[][] sub_stateMatrix=inverse_subByte(stateMatrix);
        System.out.println("逆字节代换后：");
        showMatrix(sub_stateMatrix);

        System.out.println("行移位后：");
        String[][] shift_Matrix=shiftRows(sub_stateMatrix);
        showMatrix(shift_Matrix);
        System.out.println("逆行移位后：");
        showMatrix(inverse_ShiftRows(shift_Matrix));
        System.out.println("列混合变换后：");
        String[][] mix_shift_Matrix=mixColumns(shift_Matrix);
        showMatrix(mix_shift_Matrix);
        System.out.println("逆列混合变换后：");
        showMatrix(inverse_mixColumns(mix_shift_Matrix));
        System.out.println("解密明文为："+Hexstrtostr(StrToHexstr(c),a_length));
        System.out.println("组数为："+group);
        in.close();
    }
    //十六进制字符串转为十六进制数
    private static String StrToHexstr(String str) {
        char[] strChar = str.toCharArray();
        String result = "";
        for (int i = 0; i < strChar.length; i++) {
            result += Integer.toHexString(strChar[i]);
        }
        return result;
    }
    //十六进制数转为十六进制字符串
    private static String Hexstrtostr(String str,int length){
        StringBuilder result  = new StringBuilder();
        for(int i=0; i<length-1; i+=2){
            String hex = str.substring(i,(i+2));
            int byteValue = Integer.parseInt(hex, 16);
            result.append((char)byteValue);
        }
        return result.toString();
    }

    //十六进制转为二进制
    private static String HexstrtoBinarystr(String str){
        String result="";
        for(int i=0; i<str.length(); i++){
            String temp="";
            String hex= str.substring(i,i+1);
            int byteValue = Integer.parseInt(hex, 16);
            temp=Integer.toBinaryString(byteValue);
            int length=temp.length();
            if(length<4){
                for(int j=0;j<4-length;j++){
                    temp="0"+temp;
                }
            }
            result+=temp;
        }
        return result;
    }

    //二进制转为十六进制
    private static String BinarystrtoHexstr(String str){
        String result="";
        for(int i=0; i<str.length(); i+=4){
            String hex= str.substring(i,i+4);
            int byteValue = Integer.parseInt(hex, 2);
            result += Integer.toHexString(byteValue);
        }
        return result;
    }

    //填充
    private static String Padding(String str){
        int num = str.length()%16;
        if(num!=0){
            for(int i=0;i<16-num;i++)
                str+=(char)('a'+i);
        }
        return str;
    }

    //得到状态矩阵
    public static String [][] GetMatrix(String str){
        String [][] matrix= new String [4][4];
        int id=0;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                matrix[j][i]=str.substring(id*2,id*2+2);
                id++;
            }
        }
        return matrix;
    }

    //输出矩阵4*4
    public static void showMatrix(String[][] matrix){
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                System.out.print(matrix[i][j]+'\t');
            }
            System.out.println('\n');
        }
    }

    // 字节代换运算
    public static String[][] subByte(String[][] matrix){
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                int x = Integer.parseInt(matrix[j][i].substring(0,1),16);
                int y = Integer.parseInt(matrix[j][i].substring(1,2),16);
                matrix[j][i]=Integer.toHexString(S[x][y]);
                if(matrix[j][i].length()==1){
                    matrix[j][i]="0"+matrix[j][i];
                }
            }
        }
        return matrix;
    }

    // 逆字节代换运算
    public static String[][] inverse_subByte(String[][] matrix){
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                int x = Integer.parseInt(matrix[j][i].substring(0,1),16);
                int y = Integer.parseInt(matrix[j][i].substring(1,2),16);
                matrix[j][i]=Integer.toHexString(INV_S[x][y]);
                if(matrix[j][i].length()==1){
                    matrix[j][i]="0"+matrix[j][i];
                }
            }
        }
        return matrix;
    }
    // 逆S盒
    private static final int[][] INV_S = new int[][]{
            {0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb},
            {0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb},
            {0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e},
            {0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25},
            {0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92},
            {0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84},
            {0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06},
            {0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b},
            {0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73},
            {0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e},
            {0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b},
            {0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4},
            {0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f},
            {0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef},
            {0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61},
            {0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d}
    };

    // S盒
    private static final int[][] S = new int[][]{
            {0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76},
            {0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0},
            {0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15},
            {0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75},
            {0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84},
            {0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf},
            {0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8},
            {0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2},
            {0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73},
            {0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb},
            {0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79},
            {0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08},
            {0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a},
            {0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e},
            {0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf},
            {0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16}
    };

    //行移位
    public static String[][] shiftRows(String[][] matrix){
        String tmp = matrix[1][0];
        for (int j=0;j<3;j++){
            matrix[1][j] = matrix[1][j+1];
        }
        matrix[1][3] = tmp;

        tmp = matrix[2][0];
        matrix[2][0] = matrix[2][2];
        matrix[2][2] = tmp;
        tmp = matrix[2][1];
        matrix[2][1] = matrix[2][3];
        matrix[2][3] = tmp;

        tmp = matrix[3][3];
        for (int j=3;j>0;j--){
            matrix[3][j] = matrix[3][j-1];
        }
        matrix[3][0] = tmp;

        return matrix;
    }

    // 逆行移位变换
    public static String[][] inverse_ShiftRows(String[][] matrix){
        String tmp = matrix[1][3];
        for (int j=3;j>0;j--){
            matrix[1][j] = matrix[1][j-1];
        }
        matrix[1][0] = tmp;

        tmp = matrix[2][0];
        matrix[2][0] = matrix[2][2];
        matrix[2][2] = tmp;
        tmp = matrix[2][1];
        matrix[2][1] = matrix[2][3];
        matrix[2][3] = tmp;

        tmp = matrix[3][0];
        for (int j=0;j<3;j++){
            matrix[3][j] = matrix[3][j+1];
        }
        matrix[3][3] = tmp;
        return matrix;
    }
    public static final int[][] A=new int[][]{
            {0x02, 0x03, 0x01, 0x01},
            {0x01, 0x02, 0x03, 0x01},
            {0x01, 0x01, 0x02, 0x03},
            {0x03, 0x01, 0x01, 0x02}
    };

    private static final int[][] inverse_A = new int[][]{
            {0x0e, 0x0b, 0x0d, 0x09},
            {0x09, 0x0e, 0x0b, 0x0d},
            {0x0d, 0x09, 0x0e, 0x0b},
            {0x0b, 0x0d, 0x09, 0x0e}
    };

    //按位异或
    public static String XOR(String a,String b){
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<8;i++){
            if((a.charAt(i) =='1' && b.charAt(i)=='1') || (a.charAt(i)=='0' &&b.charAt(i)=='0')){
                builder.append("0");
            }else {
                builder.append("1");
            }
        }
        return new String(builder);
    }

    //模二乘法
    public static String Xtime(String a){
        String res;
        if(a.charAt(0)=='1'){
            res=a.substring(1,8)+"0";
            res= XOR(res, "00011011");
        }
        else{
            res=a.substring(1,8)+"0";
        }
        return res;
    }
    //列混合
    public static String[][] mixColumns(String[][] matrix){
        String[][] res = new String[4][4];
        int i,j,k;
        for(i=0;i<4;i++){
            for(j=0;j<4;j++){
                String[] tmp = new String[4];
                for(k=0;k<4;k++){
                    if(A[j][k]==0x01){
                        tmp[k]=HexstrtoBinarystr(matrix[k][i]);
                    }
                    if(A[j][k]==0x02){
                        tmp[k]=Xtime(HexstrtoBinarystr(matrix[k][i]));
                    }
                    if(A[j][k]==0x03){
                        tmp[k]=Xtime(HexstrtoBinarystr(matrix[k][i]));
                        tmp[k]=XOR(tmp[k],HexstrtoBinarystr(matrix[k][i]));
                    }
                }
                res[j][i]=BinarystrtoHexstr( XOR(XOR( XOR(tmp[0],tmp[1]), tmp[2]) ,tmp[3]));
            }
        }
        return res;
    }
    //逆列混合
    public static String[][] inverse_mixColumns(String[][] matrix){
        String[][] res = new String[4][4];
        int i,j,k;
        for(i=0;i<4;i++){
            for(j=0;j<4;j++){
                String[] tmp = new String[4];
                for(k=0;k<4;k++){
                    String str = HexstrtoBinarystr(matrix[k][i]);

                    tmp[k] = HexstrtoBinarystr(matrix[k][i]);
                    for(int m=0;m<3;m++){
                        tmp[k]=Xtime(tmp[k]);
                    }

                    if(inverse_A[j][k]==0x09){
                        tmp[k]=XOR(tmp[k],str);
                    }
                    if(inverse_A[j][k]==0x0b){
                        tmp[k]=XOR(tmp[k],XOR(Xtime(str), str));
                    }
                    if(inverse_A[j][k]==0x0d){
                        tmp[k]=XOR(tmp[k], XOR(Xtime(Xtime(str)), str));
                    }
                    if(inverse_A[j][k]==0x0e){
                        tmp[k]=XOR(tmp[k], XOR(Xtime(Xtime(str)), Xtime(str)));
                    }
                }
                res[j][i]=BinarystrtoHexstr( XOR(XOR( XOR(tmp[0],tmp[1]), tmp[2]) ,tmp[3]));
            }
        }
        return res;
    }
    // 轮密钥加变换
    public static String[][] AddRoundKey(String[][] matrix,String[][] key_Matrix){
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                matrix[i][j]=BinarystrtoHexstr(XOR(HexstrtoBinarystr(matrix[i][j]),HexstrtoBinarystr(key_Matrix[i][j])));
            }
        }
        return matrix;
    }

}
