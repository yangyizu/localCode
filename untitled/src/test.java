/**
 * @author yyz
 * @version v1.0
 */

import java.util.*;

public class test {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();
        queue.offer("a"); // 添加元素
        queue.poll(); // 删除并返回一个元素
        queue.peek(); // 只返回第一个元素但不删除
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(1);
        ListNode c = new ListNode(4);
        ListNode d = new ListNode(5);
        ListNode e = new ListNode(5);
        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;
        e.next = null;
        Inter inter = new Inter();
        int[] nums = {1, 2, 3, 4, 6};
        //System.out.println(inter.IsContinuous(nums));

    }
}

