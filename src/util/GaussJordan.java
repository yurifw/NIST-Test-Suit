/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

/**
 *
 * @author renatohidaka
 */

public class GaussJordan{

   // swap()
   // swap row i with row k
   // pre: A[i][q]==A[k][q]==0 for 1<=q<j
   private void swap(double[][] A, int i, int k, int j){
      int m = A[0].length - 1;
      double temp;
      for(int q=j; q<=m; q++){
         temp = A[i][q];
         A[i][q] = A[k][q];
         A[k][q] = temp;
      }
   }

   // divide()
   // divide row i by A[i][j]
   // pre: A[i][j]!=0, A[i][q]==0 for 1<=q<j
   // post: A[i][j]==1;
   private void divide(double[][] A, int i, int j){
      int m = A[0].length - 1;
      for(int q=j+1; q<=m; q++) A[i][q] /= A[i][j];
      A[i][j] = 1;
   }

   // eliminate()
   // subtract an appropriate multiple of row i from every other row
   // pre: A[i][j]==1, A[i][q]==0 for 1<=q<j
   // post: A[p][j]==0 for p!=i
   private void eliminate(double[][] A, int i, int j){
      int n = A.length - 1;
      int m = A[0].length - 1;
      for(int p=1; p<=n; p++){
         if( p!=i && A[p][j]!=0 ){
            for(int q=j+1; q<=m; q++){
               A[p][q] -= A[p][j]*A[i][q];
            }
            A[p][j] = 0;
         }
      }
   }
   
   public int run(double[][] A, int linha, int coluna) {
      int i, j, k;
     

      // perform Gauss-Jordan Elimination algorithm
      i = 0;
      j = 0;
      while( i<linha && j<coluna ){

         //look for a non-zero entry in col j at or below row i
         k = i;
         while( k<linha && A[k][j]==0 ) k++;

         // if such an entry is found at row k
         if( k<linha ){

            //  if k is not i, then swap row i with row k
            if( k!=i ) {
               swap(A, i, k, j);
              // printMatrix(out, A);
            }

            // if A[i][j] is not 1, then divide row i by A[i][j]
            if( A[i][j]!=1 ){
               divide(A, i, j);
               //printMatrix(out, A);
            }

            // eliminate all other non-zero entries from col j by subtracting from each
            // row (other than i) an appropriate multiple of row i
            eliminate(A, i, j);
           // printMatrix(out, A);
            i++;
         }
         j++;
      }
  
      return i;      
   }
}

