package AES;

import java.util.Arrays;

// Decryption 类用于执行AES解密操作
public class Decryption {

    // 打印矩阵
    public static void printMatrix(String[][] matrix) {
        for (String[] row : matrix) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
    // 解密方法，接受密文、轮密钥矩阵和密钥扩展矩阵作为参数
    public String[][] decrypt(String ciphertext, String[][] keyMatrix) {
        // 将密文转换为状态矩阵
        String[][] state = ByteSubstitution.GetMatrix(ciphertext);

        String[][] key1=Key_expansion.key_expansion_1(keyMatrix, 0);
        String[][] key2=Key_expansion.key_expansion_1(key1, 1);
        String[][] key3=Key_expansion.key_expansion_1(key2, 2);
        String[][] key4=Key_expansion.key_expansion_1(key3, 3);
        String[][] key5=Key_expansion.key_expansion_1(key4, 4);
        String[][] key6=Key_expansion.key_expansion_1(key5, 5);
        String[][] key7=Key_expansion.key_expansion_1(key6, 6);
        String[][] key8=Key_expansion.key_expansion_1(key7, 7);
        String[][] key9=Key_expansion.key_expansion_1(key8, 8);
        String[][] key10=Key_expansion.key_expansion_1(key9, 9);


        // 初始轮密钥加
        state = AddRoundKey.AddRoundKey(state, key10);

        //第一轮迭代
        // 逆行移位
        RowTransformation.inverse_ShiftRows(state);
        System.out.println(Arrays.toString(state));
        //逆字节代换
        state = ByteSubstitution.inverse_subByte(state);
        System.out.println(Arrays.toString(state));
        // 轮密钥加
        state = AddRoundKey.AddRoundKey(state,key9);
        // 逆列混合
        state = Mix.inverse_mixColumns(state);
        System.out.println(Arrays.toString(state));
        // 返回当前轮次后的状态矩阵


        //第二轮迭代
        // 逆行移位
        RowTransformation.inverse_ShiftRows(state);
        System.out.println(Arrays.toString(state));
        //逆字节代换
        state = ByteSubstitution.inverse_subByte(state);
        System.out.println(Arrays.toString(state));
        // 轮密钥加
        state = AddRoundKey.AddRoundKey(state,key8);
        // 逆列混合
        state = Mix.inverse_mixColumns(state);
        System.out.println(Arrays.toString(state));
        // 返回当前轮次后的状态矩阵

        //第三轮迭代
        // 逆行移位
        RowTransformation.inverse_ShiftRows(state);
        System.out.println(Arrays.toString(state));
        //逆字节代换
        state = ByteSubstitution.inverse_subByte(state);
        System.out.println(Arrays.toString(state));
        // 轮密钥加
        state = AddRoundKey.AddRoundKey(state,key7);
        // 逆列混合
        state = Mix.inverse_mixColumns(state);
        System.out.println(Arrays.toString(state));
        // 返回当前轮次后的状态矩阵

        //第四轮迭代
        // 逆行移位
        RowTransformation.inverse_ShiftRows(state);
        System.out.println(Arrays.toString(state));
        //逆字节代换
        state = ByteSubstitution.inverse_subByte(state);
        System.out.println(Arrays.toString(state));
        // 轮密钥加
        state = AddRoundKey.AddRoundKey(state,key6);
        // 逆列混合
        state = Mix.inverse_mixColumns(state);
        System.out.println(Arrays.toString(state));
        // 返回当前轮次后的状态矩阵

        //第五轮迭代
        // 逆行移位
        RowTransformation.inverse_ShiftRows(state);
        System.out.println(Arrays.toString(state));
        //逆字节代换
        state = ByteSubstitution.inverse_subByte(state);
        System.out.println(Arrays.toString(state));
        // 轮密钥加
        state = AddRoundKey.AddRoundKey(state,key5);
        // 逆列混合
        state = Mix.inverse_mixColumns(state);
        System.out.println(Arrays.toString(state));
        // 返回当前轮次后的状态矩阵

        //第六轮迭代
        // 逆行移位
        RowTransformation.inverse_ShiftRows(state);
        System.out.println(Arrays.toString(state));
        //逆字节代换
        state = ByteSubstitution.inverse_subByte(state);
        System.out.println(Arrays.toString(state));
        // 轮密钥加
        state = AddRoundKey.AddRoundKey(state,key4);
        // 逆列混合
        state = Mix.inverse_mixColumns(state);
        System.out.println(Arrays.toString(state));
        // 返回当前轮次后的状态矩阵

        //第七轮迭代
        // 逆行移位
        RowTransformation.inverse_ShiftRows(state);
        System.out.println(Arrays.toString(state));
        //逆字节代换
        state = ByteSubstitution.inverse_subByte(state);
        System.out.println(Arrays.toString(state));
        // 轮密钥加
        state = AddRoundKey.AddRoundKey(state,key3);
        // 逆列混合
        state = Mix.inverse_mixColumns(state);
        System.out.println(Arrays.toString(state));
        // 返回当前轮次后的状态矩阵

        //第八轮迭代
        // 逆行移位
        RowTransformation.inverse_ShiftRows(state);
        System.out.println(Arrays.toString(state));
        //逆字节代换
        state = ByteSubstitution.inverse_subByte(state);
        System.out.println(Arrays.toString(state));
        // 轮密钥加
        state = AddRoundKey.AddRoundKey(state,key2);
        // 逆列混合
        state = Mix.inverse_mixColumns(state);
        System.out.println(Arrays.toString(state));
        // 返回当前轮次后的状态矩阵

        //第九轮迭代
        // 逆行移位
        RowTransformation.inverse_ShiftRows(state);
        System.out.println(Arrays.toString(state));
        //逆字节代换
        state = ByteSubstitution.inverse_subByte(state);
        System.out.println(Arrays.toString(state));
        // 轮密钥加
        state = AddRoundKey.AddRoundKey(state,key1);
        // 逆列混合
        state = Mix.inverse_mixColumns(state);
        System.out.println(Arrays.toString(state));
        // 返回当前轮次后的状态矩阵

        // 最后一轮不进行列混合
        RowTransformation.inverse_ShiftRows(state); // 逆行移位
        state = ByteSubstitution.inverse_subByte(state); // 逆字节代换
        state = AddRoundKey.AddRoundKey(state,keyMatrix); // 最后一轮轮密钥加

        // 返回解密后的明文状态矩阵
        return state;
    }

    // decryption_1 方法用于执行每一轮解密（除了第一轮）的操作
    public String[][] decryption_1(String[][] state, int roundNumber, String[][] keyMatrix) {
        // 轮密钥加
        state = AddRoundKey.AddRoundKey(state, Key_expansion.key_expansion_1(keyMatrix, roundNumber));
        // 逆列混合
        state = Mix.inverse_mixColumns(state);
        // 逆行移位
        RowTransformation.inverse_ShiftRows(state);
        // 逆字节代换
        state = ByteSubstitution.inverse_subByte(state);
        // 初始轮密钥加
        state = AddRoundKey.AddRoundKey(state, Key_expansion.key_expansion_1(keyMatrix, roundNumber));


        // 返回当前轮次后的状态矩阵
        return state;
    }
}