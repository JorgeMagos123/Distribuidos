import java.rmi.Naming;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClienteRMI {

    public static void main(String[] args) throws Exception {
        
        String url = "rmi://10.4.0.4/matrices";
	String url2 = "rmi://10.5.0.4/matrices";
        
	InterfaceRMI r = (InterfaceRMI)Naming.lookup(url);
	InterfaceRMI r2 = (InterfaceRMI)Naming.lookup(url2);	
	
	int N=8;	
	
	float[][] A = new float[N][N];
	float[][] B = new float[N][N];

        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                A[i][j] = 2 * i - j;
                B[i][j] = 2 * i + j;              
            }
        } 
     
	for (int i = 0; i < N; i++){
      		for (int j = 0; j < i; j++){
        		float x = B[i][j];
        		B[i][j] = B[j][i];
        		B[j][i] = x;
      		}
	}
        

        float [][] A1 = separa_matriz(A,0);
        float [][] A2 = separa_matriz(A, N/2);
        float [][] B1 = separa_matriz(B, 0); 
        float [][] B2 = separa_matriz(B, N/2);

        float [][] C1 = r.multiplica_matrices(A1, B1,N);
        float [][] C2 = r.multiplica_matrices(A1, B2,N);
        float [][] C3 = r2.multiplica_matrices(A2, B1,N);
        float [][] C4 = r2.multiplica_matrices(A2, B2,N);

	float[][] C = new float[N][N];
        acomoda_matriz(C, C1, 0, 0);
        acomoda_matriz(C, C2, 0, N/2);
        acomoda_matriz(C, C3, N/2, 0);
        acomoda_matriz(C, C4, N/2, N/2);
        
        if (N == 8){

            imprimir_matriz(A, N, N, "A");
            imprimir_matriz(B, N, N, "B transpuesta");
            imprimir_matriz(C, N, N, "C");
            System.out.println("checksum = " + checksum(C));

        } else {
            System.out.println("checksum = " + checksum(C));
        }
    // fin main
    }    

	static void acomoda_matriz(float[][] C,float[][] A,int renglon,int columna){
		for (int i = 0; i < N/2; i++)
    		for (int j = 0; j < N/2; j++)
      			C[i + renglon][j + columna] = A[i][j];
	}
	static void imprimir_matriz(float[][] m, int filas, int columnas, String s){
	        System.out.println("\nImprimiendo " + s);
	        for (int i = 0; i< filas; i++){
        		for (int j = 0; j < columnas; j++){
                	System.out.print(m[i][j] + " ");
            		}
            		System.out.println("");
        	}
        // fin método imprimir matriz
        }

	static double checksum(float[][] m) {        
	        double s = 0;
        	for (int i = 0; i < m.length; i++)
            		for (int j = 0; j < m[0].length; j++)
                		s += m[i][j];
        	return s;
        // fin método checksum    
    	}
	static float[][] separa_matriz(float[][] A,int inicio){
  		float[][] M = new float[N/2][N];
  		for (int i = 0; i < N/2; i++)
    		for (int j = 0; j < N; j++)
      		M[i][j] = A[i + inicio][j];
  		return M;
	}
// fin clase ClienteMatricesRMI    
}

