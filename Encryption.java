package AES;

// 引入必要的类
import java.util.Arrays;

// Encryption 类用于执行AES加密操作
public class Encryption {

    // 加密方法，接受明文、填充后的明文、分组数、明文长度和密钥矩阵作为参数
    public String[][] encrypt(String plaintext, String paddedPlaintext, int groupCount, int aLength, String[][] keyMatrix) {
        // 将填充后的明文转换为状态矩阵
        String[][] state = ByteSubstitution.GetMatrix(ByteSubstitution.StrToHexstr(paddedPlaintext));

        // 初始轮密钥加
        state = AddRoundKey.AddRoundKey(state, keyMatrix);
        String[][] keyMatrix_add=Key_expansion.key_expansion_1(keyMatrix,0);
        // 进行9轮迭代（共10轮，不包括初始轮密钥加）
        for (int round = 0; round < 9; round++) {
            // 字节代换
            state = ByteSubstitution.subByte(state);
            System.out.println(Arrays.toString(state));
            // 行移位
            RowTransformation.shiftRows(state);
            System.out.println(Arrays.toString(state));
            // 列混合
            state = Mix.mixColumns(state);
            System.out.println(Arrays.toString(state));
            // 轮密钥加

            state = AddRoundKey.AddRoundKey(state,keyMatrix_add);
            String[][] keyMatrix_next=Key_expansion.key_expansion_1(keyMatrix, round+1);
            keyMatrix_add=keyMatrix_next;
        }

        // 最后一轮不进行列混合
        state = ByteSubstitution.subByte(state); // 字节代换
        RowTransformation.shiftRows(state); // 行移位
        state = AddRoundKey.AddRoundKey(state, Key_expansion.key_expansion_1(keyMatrix_add, 9)); // 最后一轮轮密钥加

        // 返回加密后的密文状态矩阵
        return state;
    }

    // encryption_1 方法用于执行每一轮加密（除了第一轮）的操作
    public String[][] encryption_1(String[][] state, int roundNumber, String[][] keyMatrix) {
        //初始轮密钥加
        state = AddRoundKey.AddRoundKey(state, keyMatrix);
        // 字节代换
        state = ByteSubstitution.subByte(state);
        // 行移位
        RowTransformation.shiftRows(state);
        // 列混合
        state = Mix.mixColumns(state);
        // 轮密钥加
        state = AddRoundKey.AddRoundKey(state, Key_expansion.key_expansion_1(keyMatrix, roundNumber));
        // 返回当前轮次后的状态矩阵
        return state;
    }
}