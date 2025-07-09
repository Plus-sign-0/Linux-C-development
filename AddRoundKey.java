package AES;

public class AddRoundKey {

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
