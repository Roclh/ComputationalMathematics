package math;


import math.excpetions.WrongMatrixException;
import services.Matrix;

import java.util.ArrayList;
import java.util.Arrays;

public class Gaussian {

    //Метод объединяет все действия, необходимые для решения
    public static ArrayList<Double> solve(Matrix matrix) throws WrongMatrixException {
        if(!checkMainLine(matrix)){
            if(isCorrectable(matrix)){
                Matrix correctMatrix = correctMatrix(matrix);
                return doMath(correctMatrix);
            }else{
                throw new WrongMatrixException("Так как матрица имеет столбцы или строки полностью состоящие из нулей,\n" +
                        "определитель матрицы всегда будет равен 0.");
            }
        }else{
            return doMath(matrix);
        }
    }

    private static ArrayList<Double> doMath(Matrix correctMatrix) {
        Matrix triangleMatrix = getTriangleMatrix(correctMatrix);
        Double[] results = results(triangleMatrix);
        return new ArrayList<Double>(Arrays.asList(results));
    }

    private static boolean isCorrectable(Matrix matrix){
        for(int i=0; i<matrix.height; i++){
            int counter = 0;
            for(int j=0; j< matrix.length; j++){
                if(matrix.getArray()[i][j]==0) counter++;
            }
            if(counter >= matrix.length-1)return false;
        }
        for(int j=0; j<matrix.length; j++){
            int counter = 0;
            for(int i=0; i<matrix.height; i++){
                if(matrix.getArray()[i][j]==0) counter++;
            }
            if(counter >= matrix.height)return false;
        }
        return true;
    }

    private static boolean checkMainLine(Matrix matrix){
        for(int i=0; i<matrix.height; i++){
            if(matrix.getArray()[i][i]==0d){
                return false;
            }
        }
        return true;
    }

    private static Matrix correctMatrix(Matrix matrix){
        Matrix copy = new Matrix(matrix);
        while(!checkMainLine(copy)){
            for(int i=0; i<copy.height; i++){
                if(copy.getArray()[i][i]==0){
                    for(int j=0; j<copy.length; j++){
                        double buffer = copy.getArray()[i][j];
                        if(i == copy.height-1){
                            copy.getArray()[i][j] = copy.getArray()[0][j];
                            copy.getArray()[0][j] = buffer;
                        }else{
                            copy.getArray()[i][j] = copy.getArray()[i+1][j];
                            copy.getArray()[i+1][j] = buffer;
                        }
                    }
                }
            }
        }
        return copy;
    }

    //Метод рассчитывает определитель матрицы перемножая значения основной диагонали треугольной матрицы
    private double determinant(Matrix matrix) {
        Matrix temp = getTriangleMatrix(matrix);
        double determinant = 1;
        for (int i = 0; i < temp.height; i++) {
                determinant *= temp.getArray()[i][i];
        }
        if(Math.ceil(determinant)-determinant<0.000001d){
            return Math.ceil(determinant);
        }else if(determinant-Math.floor(determinant)<0.000001d){
            return Math.floor(determinant);
        }else return determinant;

    }

    //Метод преобразует матрицу в треугольню методом элементарных преобразований
    private static Matrix getTriangleMatrix(Matrix matrix) {
        Matrix temp = new Matrix(matrix);
        double num;
        for (int j = 0; j < temp.height; j++) {
            for (int i = j + 1; i < temp.height; i++) {
                if (temp.getArray()[j][j] == 0) {
                    break;
                }
                num = temp.getArray()[i][j] / temp.getArray()[j][j];
                for (int k = j; k < temp.length; k++) {
                    temp.getArray()[i][k] = temp.getArray()[i][k] - temp.getArray()[j][k] * num;
                }
            }
            //terminal.writeMatrix(temp);
            if (j < temp.height - 1) {
                int count = 0;
                for (int i = j + 1; i < temp.height; i++) {
                    if (temp.getArray()[i][j + 1] != 0) {
                        for (int k = j; k < temp.length; k++) {
                            double buf = temp.getArray()[j + 1 + count][k];
                            temp.getArray()[j + 1 + count][k] = temp.getArray()[i][k];
                            temp.getArray()[i][k] = buf;
                        }
                        count++;
                    }
                }
            }
            //terminal.writeMatrix(temp);

        }
        return temp;
    }

    //Метод рассчитывает вектор результатов треугольной матрицы, вычисляя коэфициенты и подставляя значения в предыдущие уравнения.
    private static Double[] results(Matrix triangleMatrix){
        Matrix matrix = new Matrix(triangleMatrix);

        Double[] results = new Double[matrix.height];
        for(int i=matrix.height-1; i>=0; i--){
            results[i]=matrix.getArray()[i][i+1]/matrix.getArray()[i][i];
            for(int j = i; j>=0; j--){
                matrix.getArray()[j][i]=matrix.getArray()[j][i+1] - matrix.getArray()[j][i]*results[i];
            }
        }
        return results;
    }

    //Метод рассчитывает вектор невязок.
    private double[] residualVector(Matrix matrix, double[] results){
        Matrix copy = new Matrix(matrix);
        copy = copy.shorten();
        double[] residualVector = new double[results.length];
        for(int i=0; i< copy.height; i++){
            for(int j=0; j< copy.height; j++){
                residualVector[i]+=copy.getArray()[i][j]*results[j];
            }
            residualVector[i]-=matrix.getArray()[i][matrix.length-1];
        }
        return residualVector;
    }
}
