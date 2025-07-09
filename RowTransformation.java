package AES;

public class RowTransformation {
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
    //输出矩阵4*4
    public static void showMatrix(String[][] matrix){
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                System.out.print(matrix[i][j]+'\t');
            }
            System.out.println('\n');
        }
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
}
