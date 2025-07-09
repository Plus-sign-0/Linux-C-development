package AES;

public class Mix {
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
    //输出矩阵4*4
    public static void showMatrix(String[][] matrix){
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                System.out.print(matrix[i][j]+'\t');
            }
            System.out.println('\n');
        }
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
}
