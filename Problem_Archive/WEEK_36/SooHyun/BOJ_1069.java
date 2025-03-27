/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 문제를 처음 읽었을 때 테스트 케이스의 결과 값을 이해하지 못했습니다.
    - 이동이라고 하니 당연히 board와 같이 좌표가 주어지고 4방 탐색을 통해서 최단 거리를 구한다고 생각했습니다.
    - 하지만 이 문제는 4방 탐색을 통한 이동이 아니라 정확한 (x, y) 좌표가 아니라도 이동할 수 있는 것이였습니다. 즉 원이라고 생각하면 좋을 것 같습니다.
    - 목표 지점까지의 거리가 즉 직선거리이기에 Math.sqrt를 통해서 직선 거리를 구합니다.
    - 그리고 이제 조건문 분기처리를 수행합니다.
    - 접프 1번으로 목표를 지나친 경우 이제 또 생각해야되는 것은 점프를 하고 목표 지점을 지나치고 그로 인해 생긴 거리 걷기 vs 어떻게든 점프 2번 중 가장 작은 값을 선택합니다.
    - 점프 1번으로 목표를 지나치지 않은 경우 일단 직선거리를 한 번의 점프로 이동하는 거리로 나누어 몇 번 점프할 수 있는지 구한 값을 cnt에 저장하고 cnt번 점프에 남은 거리 걷기 vs 한번 더 점프하기 중 더 작은 값을 더합니다.
    - 마지막으로 점프를 활용하는 것과 점프 활용하지 않고 걷기 중 최소를 선택해 값을 출력합니다.

시간 복잡도
    - 입력 : O(1)
    - 거리 계산 및 조건 처리 : O(1)
    - 전체 시간복잡도: O(1)

실행 시간
   - 108ms(java)
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int X, Y, D, T;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        X = Integer.parseInt(st.nextToken());
        Y = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());
        double distance = Math.sqrt(X * X + Y * Y);
        double result = distance; // 걷기만 할 때 시간

        // 점프 1회 이상 가능할 경우
        double jumpTime = 0.0;

        if (distance < D) {
            // 점프 1번 하고 남은 거리 걷기 vs 점프 2번 (제자리 점프)
            jumpTime = Math.min(T + (D - distance), 2.0 * T);
        } else {
            int cnt = (int) (distance / D);
            double rest = distance - cnt * D;
            jumpTime = cnt * T + Math.min(rest, T);
        }

        result = Math.min(result, jumpTime);
        System.out.println(result);

    }
}