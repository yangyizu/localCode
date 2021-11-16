package 流水线;

import javax.swing.JOptionPane;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.util.List;

public class Answer {
    int M = 0;
    int N = 0;
    int[][] mat;
    int[][] edge = new int[15][15]; // 结点之间的指向
    HashMap<String, Integer> conflicts = new HashMap<>(); // 存储所有冲突向量和各自的编号
    String initial_conflict; // 初始化冲突向量
    int conflict_num = 0; // 冲突向量的个数

    public Answer(int m, int n, int[][] mat) {
        M = m;
        N = n;
        this.mat = mat;
        setInitial_conflict(m, n, mat); // 创建初始向量
        setConflicts(); // 创建后续向量和转化图
        List<List<Integer>> loops = findLoops();
        StringBuilder out = new StringBuilder();
        System.out.println("所有的环(可行解)如下:" + loops);
        out.append("所有的环(可行解)如下:" + loops).append("\n");
        List<List<Integer>> bestAnses = findBest(loops);
        System.out.println("最优解如下:" + bestAnses);
        out.append("最优解如下:" + bestAnses).append("\n");
        // 结果输出
        JOptionPane.showMessageDialog(null, out.toString(), "输出", JOptionPane.PLAIN_MESSAGE);
        for (List<Integer> bestAns : bestAnses) { // 循环画图
            paint(bestAns);
        }

    }

    // 根据一个最优解画图
    private void paint(List<Integer> bestAns) {
        JFrame app = new JFrame("方案:"+ bestAns);
        Container container = app.getContentPane();
        container.setLayout(new GridLayout(M + 1, N * 2 + 2));
        int gridLen = 50; // 一个小格子的宽
        app.setSize((N * 2 + 2) * gridLen, (M + 1) * gridLen);
        int count = 1;
        int offset = 0;
        int[][] graph = new int[M + 1][N * 2 + 2];
        int i = 1, j = 1;
        while (true) {
            for (i = 1; i <= M; i++) {
                for (j = 1; j <= N && (j + offset) < (N * 2 + 2); j++) {
                    if (mat[i][j] > 0) {
                        graph[i][j + offset] = count;
                    }
                }
            }
            if (offset >= (N * 2 + 2)) break;
            offset += bestAns.get((count - 1) % bestAns.size());
            count++;
        }
        for (i = 0; i < M + 1; i++) {
            for (j = 0; j < N * 2 + 2; j++) {
                if (i == 0 && j == 0) {
                    container.add(new JLabel(""));
                } else if (i == 0) {
                    container.add(new JLabel("" + j));
                } else if (j == 0) {
                    container.add(new JLabel("s" + (i + 1)));
                } else {
                    if (graph[i][j] > 0) {
                        container.add(new JLabel("x" + graph[i][j]));
                    } else {
                        container.add(new JLabel(""));
                    }
                }
            }
        }
        app.setVisible(true);
        Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize(); // 获得显示器大小对象
        Dimension frameSize = app.getSize();             // 获得窗口大小对象
        app.setLocation(
                (displaySize.width - frameSize.width) / 2,
                (displaySize.height - frameSize.height) / 2); // 设置窗口居中显示
    }

    // 寻找最优解且去重
    List<List<Integer>> findBest(List<List<Integer>> loops) {
        List<List<Integer>> res = new ArrayList<>();
        int best = 100000;
        for (List<Integer> loop : loops) {
            int sum = 0;
            int count = 0;
            int avg = 0;
            for (int i : loop) {
                sum += i;
                count++;
            }
            avg = sum / count;
            if (avg < best) { // 如果严格小，就需要清空之前的res
                res.clear();
                best = avg;
                res.add(loop);
            } else if (avg == best) { // 相等就需要判断重复。如果重复了就不添加
                boolean addFlag = true;
                for (List<Integer> list : res) {
                    if (checkListSame(list, loop)) {
                        addFlag = false; // 不加入该loop
                        break;
                    }
                }
                if (addFlag) res.add(loop); // 否则加入该loop
            }
        }
        return res;
    }

    boolean checkListSame(List<Integer> list1, List<Integer> list2) {
        if (list1 == null) return list2 == null;
        if (list2 == null) return list1 == null;
        if (list1.size() != list2.size()) return false;
        Collections.sort(list1);
        Collections.sort(list2);
        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i) != list2.get(i)) {
                return false;
            }
        }
        return true;

    }

    // 回溯法找环
    List<Integer> path = new ArrayList<>();

    List<List<Integer>> findLoops() {
        List<List<Integer>> res = new ArrayList<>();
        path.add(1);
        backtrace(res, 1);
        return res;
    }

    void backtrace(List<List<Integer>> res, int now) {
        for (int i = 1; i <= conflict_num; i++) { // 遍历结点now的所有可能边
            if (edge[now][i] != 0) { // 存在一条边
                if (i == now) { // 如果是自己指向自己的情况
                    res.add(new ArrayList<>(Arrays.asList(edge[i][i])));
                    continue;
                } else if (path.contains(i)) { // path已经存在了该结点
                    int start = path.indexOf(i);
                    ArrayList<Integer> loop = getLoop(path, start);
                    res.add(loop);
                    continue;
                } else {
                    path.add(i);
                    backtrace(res, i);
                }
            }
        }
        path.remove(path.size() - 1);
    }

    private ArrayList<Integer> getLoop(List<Integer> path, int start) {
        ArrayList<Integer> res = new ArrayList<>();
        int index = start;
        int i, j;
        for (; index < path.size() - 1; index++) {
            i = path.get(index);
            j = path.get(index + 1);
            res.add(edge[i][j]);
        }
        i = path.get(index);
        j = path.get(start);
        res.add(edge[i][j]);
        return res;
    }


    public void setInitial_conflict(int m, int n, int[][] mat) {
        int[] temp = new int[n];
        for (int i = 1; i <= m; i++) {
            int first = -1;
            int second = -1;
            for (int j = 1; j <= n; j++) {
                if (mat[i][j] == 1) {
                    if (first == -1) {
                        first = j;
                    } else {
                        second = j;
                        break;
                    }
                }
            }
            int distance = second - first;
            if (second == -1) { // 一行可能只有一个1
                distance = 0;
            }
            temp[distance] = 1;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = n - 1; i >= 1; i--) {
            if (temp[i] == 1) {
                sb.append('1');
            } else sb.append('0');
        }
        initial_conflict = sb.toString();
        conflicts.put(initial_conflict, 1);
        conflict_num++;
        System.out.println("初始向量为：" + initial_conflict);
    }

    // 建立所有的冲突向量和转化关系
    void setConflicts() {
        LinkedList<String> queue = new LinkedList<>();
        queue.offer(initial_conflict);
        int len = initial_conflict.length();
        // 创造出所有的冲突向量
        while (!queue.isEmpty()) {
            String nowConflict = queue.poll();
            int num = conflicts.get(nowConflict); // 获取当前冲突向量的编号
            for (int step : getSteps(nowConflict)) { // 右移步数
                String newConflict = or(move(nowConflict, step), initial_conflict);
                if (!conflicts.containsKey(newConflict)) { // 如果是新的冲突向量
                    conflicts.put(newConflict, ++conflict_num);
                    queue.offer(newConflict);
                }
            }
        }
        // 建立转化关系
        for (Map.Entry<String, Integer> entry : conflicts.entrySet()) {
            String nowConflict = entry.getKey();
            Integer num1 = entry.getValue();
            for (int step : getSteps(nowConflict)) {
                String newConflict = or(move(nowConflict, step), initial_conflict);
                Integer nums2 = conflicts.get(newConflict);
                edge[num1][nums2] = step;
            }

        }
        // 所有结点到初始结点的步数都是7
        for (int i = 1; i <= conflict_num; i++) {
            edge[i][1] = len + 1;
        }
    }

    // 根据冲突向量获取它的右移步数
    private List<Integer> getSteps(String conflict) {
        List<Integer> res = new ArrayList<>();
        int length = conflict.length();
        for (int i = length - 1; i >= 0; i--) {
            if (conflict.charAt(i) == '0') {
                res.add(length - i);
            }
        }
        return res;
    }

    // 将冲突向量右移操作
    private String move(String conflict, int step) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < conflict.length(); i++) {
            if (i < step) {
                sb.append('0');
            } else {
                sb.append(conflict.charAt(i - step));
            }
        }
        return sb.toString();
    }

    // 两个冲突向量做或操作
    private String or(String conflict1, String conflict2) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < conflict1.length(); i++) {
            if (conflict1.charAt(i) == '0' && conflict2.charAt(i) == '0') {
                sb.append('0');
            } else {
                sb.append('1');
            }
        }
        return sb.toString();
    }
}
