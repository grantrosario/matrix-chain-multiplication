import java.util.*;
import java.sql.Timestamp;

class MatrixChainMultiplication
{
    // A naive recursive implementation
    static int RecursiveMatrixChain(int p[], int i, int j)
    {
        if (i == j)
            return 0;
 
        int min = Integer.MAX_VALUE;
 
        // place parenthesis at different places between first
        // and last matrix, recursively calculate count of
        // multiplications for each parenthesis placement and
        // return the minimum count
        for (int k=i; k<j; k++)
        {
            int count = RecursiveMatrixChain(p, i, k) +
                        RecursiveMatrixChain(p, k+1, j) +
                        p[i-1]*p[k]*p[j];
 
            if (count < min)
                min = count;
        }
 
        // Return minimum count
        return min;
    }


    // Dynamic Programming implementation
    static int MatrixChainOrder(int p[], int n)
    {
        /* For simplicity of the program, one extra row and one
        extra column are allocated in m[][].  0th row and 0th
        column of m[][] are not used */
        int m[][] = new int[n][n];
 
        int i, j, k, L, q;
 
        /* m[i,j] = Minimum number of scalar multiplications needed
        to compute the matrix A[i]A[i+1]...A[j] = A[i..j] where
        dimension of A[i] is p[i-1] x p[i] */
 
        // cost is zero when multiplying one matrix.
        for (i = 1; i < n; i++)
            m[i][i] = 0;
 
        // L is chain length.
        for (L=2; L<n; L++)
        {
            for (i=1; i<n-L+1; i++)
            {
                j = i+L-1;
                if(j == n) continue;
                m[i][j] = Integer.MAX_VALUE;
                for (k=i; k<=j-1; k++)
                {
                    // q = cost/scalar multiplications
                    q = m[i][k] + m[k+1][j] + p[i-1]*p[k]*p[j];
                    if (q < m[i][j])
                        m[i][j] = q;
                }
            }
        }
        
        // Return minimum count
        return m[1][n-1];
    }
 
    public static void main(String args[])
    {
        Random rand = new Random();

        List<Long> reTimes = new ArrayList<Long>();
        List<Long> dyTimes = new ArrayList<Long>();

        int j = 0;
        while(j < 10) {
            int arr[] = new int[26];
            int i = 0;
            while(i <= 25) {
                arr[i] = rand.nextInt(50) + 1;
                i++;
            };
            int size = arr.length;

            /*===============================================================================
            Recursive Algorithm
            =================================================================================*/
            
            long firstAlgStart = new Date().getTime();

            RecursiveMatrixChain(arr, 1, size-1);

            long firstAlgFinish = new Date().getTime();
            reTimes.add(firstAlgFinish - firstAlgStart);

            /*===============================================================================
            Dynamic Algorithm
            =================================================================================*/
     
            long secondAlgStart = new Date().getTime();

            MatrixChainOrder(arr, size);

            long secondAlgFinish = new Date().getTime();
            dyTimes.add(secondAlgFinish - secondAlgStart);

            j++;
        }

        long reSum = 0;
        long dySum = 0;
        if(!reTimes.isEmpty() && !dyTimes.isEmpty()) {
            for (long reTime : reTimes) {
                reSum += reTime;
            }

            for (long dyTime : dyTimes) {
                dySum += dyTime;
            }

            System.out.println("\n");
            System.out.println("Average running time for recursive: " + reSum / reTimes.size() + " milliseconds");
            System.out.println("Average running time for dynamic: " + dySum / dyTimes.size() + " milliseconds");
        }
    }
}