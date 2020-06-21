package com.lebron.leetcode;

public class TwoNumbers {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        boolean carry = false;
        ListNode tmp1 = l1, tmp2 = l2;
        while (true) {
            tmp1.val = tmp1.val + tmp2.val;
            if (carry) {
                tmp1.val += 1;
            }
            carry = tmp1.val >= 10;
            if (carry) {
                tmp1.val -= 10;
            }
            if (tmp1.next == null || tmp2.next == null) {
                if (tmp1.next == null) {
                    tmp1.next = tmp2.next;
                }
                if (carry) {
                    if (tmp1.next == null) {
                        tmp1.next = new ListNode(1);
                    } else {
                        tmp1.next.val += 1;
                    }
                }
                break;
            }
            tmp1 = tmp1.next;
            tmp2 = tmp2.next;
        }

        while (true) {
            tmp1 = tmp1.next;
            if (tmp1 == null) {
                break;
            }
            if (tmp1.val >= 10) {
                tmp1.val -= 10;
                if (tmp1.next == null) {
                    tmp1.next = new ListNode(1);
                    break;
                } else {
                    tmp1.next.val += 1;
                }
            } else {
                break;
            }
        }
        return l1;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(9);
        l1.next = new ListNode(9);
        ListNode l2 = new ListNode(1);
        ListNode result = addTwoNumbers(l2, l1);

        while (result != null) {
            System.out.println(result.val);
            result = result.next;
        }
    }
}