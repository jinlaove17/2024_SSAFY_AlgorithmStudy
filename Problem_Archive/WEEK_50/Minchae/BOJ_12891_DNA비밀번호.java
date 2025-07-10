/**
 * 12891 DNA 비밀번호
 * https://www.acmicpc.net/problem/12891
 *
 * @author minchae
 * @date 2025. 7. 10.
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	static int S, P;
	
	static char[] dna;
	
	static int[] my;
	static int[] check;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        S = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        
        dna = br.readLine().toCharArray();
        
        my = new int[4];
        check = new int[4];
        
        // 초기 부분 문자열
        for (int i = 0; i < P; i++) {
        	plus(i);
        }
        
        st = new StringTokenizer(br.readLine());
        
        for (int i = 0; i < 4; i++) {
        	check[i] = Integer.parseInt(st.nextToken());
        }
        
        int answer = 0;
        
        if (checkDNA()) {
        	answer++;
        }

        for (int j = P; j < S; j++) {
        	int i = j - P; // 시작 위치
        	
        	minus(i);
        	plus(j);
        	
        	if (checkDNA()) {
        		answer++;
        	}
        }
        
        System.out.println(answer);
	}
	
	private static boolean checkDNA() {
		for (int i = 0; i < 4; i++) {
			if (my[i] < check[i]) {
				return false;
			}
		}
		
		return true;
	}
	
	private static void plus(int idx) {
		if (dna[idx] == 'A') {
    		my[0]++;
    	} else if (dna[idx] == 'C') {
    		my[1]++;
    	} else if (dna[idx] == 'G') {
    		my[2]++;
    	} else if (dna[idx] == 'T') {
    		my[3]++;
    	}
	}
	
	private static void minus(int idx) {
		if (dna[idx] == 'A') {
    		my[0]--;
    	} else if (dna[idx] == 'C') {
    		my[1]--;
    	} else if (dna[idx] == 'G') {
    		my[2]--;
    	} else if (dna[idx] == 'T') {
    		my[3]--;
    	}
	}

}
