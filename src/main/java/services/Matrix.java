package services;

public class Matrix {
    private double[][] array;
    public int height;
    public int length;

    public Matrix(int height, int length){
        this.height = height;
        this.length = length;
        this.array = new double[height][length];
    }

    public Matrix(double[] ...arrays){
        this.height = arrays.length;
        this.length = arrays[0].length;
        this.array = arrays;
    }

    public Matrix(Matrix matrix){
        this.height = matrix.height;
        this.length = matrix.length;
        this.array = new double[height][length];
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.length; j++) {
                array[i][j] = matrix.getArray()[i][j];
            }
        }
    }

    public double[][] getArray(){
        return this.array;
    }

    public int getMaxNumberLength(){
        int kol = 0;
        for(int i=0; i<this.height; i++){
            for(int j=0; j< this.length; j++){
                if(String.valueOf(Math.abs(array[i][j])).length()>kol){
                    if((String.valueOf(Math.abs(array[i][j])).substring(String.valueOf(Math.abs(array[i][j])).indexOf(".")).length())>=6){
                        kol = (String.valueOf(Math.abs(array[i][j])).substring(0,String.valueOf(Math.abs(array[i][j])).indexOf(".")+5)).length();;
                    }else{
                        kol = String.valueOf(Math.abs(array[i][j])).length();
                    }
                }
            }
        }
        return kol+6;
    }

    public Matrix shorten(){
        Matrix shortenMatrix = new Matrix(height, height);
        for(int i=0; i<shortenMatrix.height; i++){
            for(int j=0; j<shortenMatrix.length; j++){
                shortenMatrix.getArray()[i][j] = this.array[i][j];
            }
        }
        return shortenMatrix;
    }


}
