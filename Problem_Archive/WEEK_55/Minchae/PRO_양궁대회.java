class Solution {
    
    static int max = -1;
    static int[] lion;
    static int[] answer;
    
    public int[] solution(int n, int[] info) {
        lion = new int[11];
        
        backtracking(0, n, info);
        
        if (max == -1) {
            answer = new int[] {-1};
        }
        
        return answer;
    }
    
    private static void backtracking(int depth, int n, int[] info) {
        if (depth == n) {
            int diff = getDiff(info);
            
            if (max <= diff) {
                max = diff;
                answer = lion.clone();
            }
            
            return;
        }
        
        for (int i = 0; i < info.length; i++) {
            // 라이언의 화살 수가 더 많은 경우 종료
            if (lion[i] > info[i]) {
                break;
            }
            
            lion[i] += 1;
            backtracking(depth + 1, n, info);
            lion[i] -= 1;
        }
    }
    
    private static int getDiff(int[] info) {
        int l = 0;
        int a = 0;
        
        for (int i = 0; i < info.length; i++) {
            if (info[i] == 0 && lion[i] == 0) {
                continue;
            }
            
            if (lion[i] > info[i]) {
                l += (10 - i);
            } else {
                a += (10 - i);
            }
        }
        
        return l - a <= 0 ? -1 : l - a;
    }
}
