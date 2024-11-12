/**
 * 2461 대표 선수
 * https://www.acmicpc.net/problem/2461
 *
 * @author minchae
 * @date 2024. 11. 12.
 *
 * 문제 풀이
 *  - 먼저 각 반마다 능력치를 오름차순 정렬
 *  - 각 반에서 최솟값 추출 -> 그 중에서의 최대/최소 찾고 둘의 차이를 갱신
 *  - 최솟값 중에서 가장 작은 값을 가지는 반의 인덱스 구함
 *  - 그 반의 인덱스를 1 증가시킴 -> 다음 최솟값을 구해서 계속 최소/최대 값의 차이를 갱신
 *
 *  예시
 *  12  16  67  43
 *  7   17  68  48
 *  14  15  77  54
 *  -> 여기서 처음 최솟값은 12, 7, 14 (각 반 최솟값 인덱스 각각 0) -> 최대/최소 차이는 7, 최솟값을 가지는 반의 인덱스 1을 저장
 *  -> 다음 탐색은 12, 17, 14 (각 반 최솟값 인덱스 0, 1, 0) -> 최대/최소 차이 5, 최솟값을 가지는 반의 인덱스는 0
 *  -> 다음 탐색 16, 17, 14 (각 반 최솟값 인덱스 1, 1, 0) -> 최대/최소 차이 3, 최솟값 가지는 반 인덱스 2
 *  -> 계속 탐색하다가 최솟값 가지는 반의 인덱스가 M을 넘어갈 경우 종료 -> 더이상 탐색이 불가능하기 때문
 *
 * 최솟값을 저장하는 거라서 우선순위큐를 이용 -> 클래스를 만들어 능력치를 기준으로 오름차순 정렬
 * 
 * 시간 복잡도
 * search => O(N^2*M)
 * search2 => O((N*M)*logN)
 *
 * 실행 시간
 * search => 2432 ms
 * search2 => 1224 ms
 * */

import java.util.*;
import java.io.*;

public class BOJ_2461 {

    static class Student implements Comparable<Student> {
        int score;
        int num; // 반 정보

        public Student(int score, int num) {
            this.score = score;
            this.num = num;
        }


        @Override
        public int compareTo(Student o) {
            return Integer.compare(this.score, o.score);
        }
    }

    static int N, M;
    static int[][] students;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        students = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < M; j++) {
                students[i][j] = Integer.parseInt(st.nextToken());
            }

            Arrays.sort(students[i]);
        }

//        System.out.println(search());
        System.out.println(search2());
    }

    // 최대/최소 차이가 최소가 되는 경우를 찾음
    private static int search() {
        int result = Integer.MAX_VALUE;

        int[] minIndex = new int[N]; // 각 반에서 최솟값을 가지는 인덱스를 저장
        int classIdx = 0; // 최솟값들 중에서 가장 작은 값을 가지는 반의 인덱스

        // (M - 1)까지 다 탐색한 경우 종료
        while (minIndex[classIdx] < M) {
            classIdx = 0;
            
            int min = students[0][minIndex[0]];
            int max = students[0][minIndex[0]];

            for (int i = 1; i < N; i++) {
                // 최솟값 갱신
                if (students[i][minIndex[i]] < min) {
                    min = students[i][minIndex[i]];
                    classIdx = i;
                }

                // 최댓값 갱신
                if (students[i][minIndex[i]] > max) {
                    max = students[i][minIndex[i]];
                }
            }

            result = Math.min(result, max - min);
            minIndex[classIdx]++; // 다음 탐색을 위해 가장 작은 값을 가지는 반의 최소값 인덱스를 1 증가시킴
        }

        return result;
    }

    // 우선순위큐 이용
    private static int search2() {
        int result = Integer.MAX_VALUE;

        int[] minIndex = new int[N]; // 각 반에서 최솟값을 가지는 인덱스를 저장

        PriorityQueue<Student> pq = new PriorityQueue<>();
        int max = 0; // 최댓값

        for (int i = 0; i < N; i++) {
            pq.add(new Student(students[i][0], i)); // 처음 최솟값과 반 저장
            minIndex[i] = 1; // 0번째 인덱스의 값은 큐에 넣었으니까 다음 탐색 위치를 증가시킴

            max = Math.max(max, students[i][0]); // 처음 탐색 시 최댓값 저장
        }

        while (!pq.isEmpty()) {
            Student cur = pq.poll();

            result = Math.min(result, max - cur.score); // 최대/최소 차이 갱신

            // 특정 학급을 다 탐색한 경우
            if (minIndex[cur.num] >= M) {
                break;
            }
            
            pq.add(new Student(students[cur.num][minIndex[cur.num]], cur.num)); // 큐에 다음 탐색 값 저장
            max = Math.max(max, students[cur.num][minIndex[cur.num]]); // 최댓값 갱신
            
            minIndex[cur.num]++;
        }

        return result;
    }

}
