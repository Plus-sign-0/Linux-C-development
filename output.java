package AES;

public class output {
    //十六进制字符串转为十六进制数
     static String StrToHexstr(String str) {
        char[] strChar = str.toCharArray();
        String result = "";
        for (int i = 0; i < strChar.length; i++) {
            result += Integer.toHexString(strChar[i]);
        }
        return result;
    }
    //十六进制数转为十六进制字符串
    protected static String Hexstrtostr(String str){
        StringBuilder result  = new StringBuilder();
        for(int i=0; i<str.length(); i+=2){
            String hex = str.substring(i,(i+2));
            int byteValue = Integer.parseInt(hex, 16);
            result.append((char)byteValue);
        }
        return result.toString();
    }
}
