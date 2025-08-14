import java.util.*;
class Solution {
    int N, M;
    int len;
    int[][] temp;
    int[][] map;
    public boolean solution(int[][] key, int[][] lock) {
        boolean answer = false;
        N = key.length;
        M = lock.length;
        len = 2*N+M-2;
        map = new int[len][len];
        for(int i=0; i<M; i++) {
            for(int j=0; j<M; j++) {
                map[N-1+i][N-1+j] = lock[i][j];
            }
        }
        init(map);
        for(int d=0; d<4; d++) {
            rotate(key);
            for(int x=0; x<len-N+1; x++) {
                for(int y=0; y<len-N+1; y++) {
                    for(int i=0; i<N; i++) {
                        for(int j=0; j<N; j++) {
                            map[i+x][j+y]+=key[i][j];
                        }
                    }
                    boolean flag=true;
                    for(int i=N-1; i<N-1+M; i++) {
                        for(int j=N-1; j<N-1+M; j++) {
                            if(map[i][j] != 1){
                                flag=false;
                                break;
                            }
                        }
                        if(!flag)
                            break;
                    }
                    if(flag)
                        return true;
                    restore(map);
                }
            }
        }
        return answer;
    }

    public void init(int[][] map){
        temp = new int[len][len];
        for(int i=0; i<len; i++){
            for(int j=0; j<len; j++) {
                temp[i][j]=map[i][j];
            }
        }
    }


    public void rotate(int[][] key) {
        int[][] arr = new int[N][N];
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                arr[N-j-1][i]=key[i][j];
            }
        }
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                key[i][j] = arr[i][j];
            }
        }
    }

    public void restore(int[][] map){
        for(int i=0; i<len; i++) {
            for(int j=0; j<len; j++){
                map[i][j]=temp[i][j];
            }
        }
    }
}