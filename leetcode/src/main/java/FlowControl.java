public class FlowControl {
    private final long THRESHOLD = 1000;

    // 每个窗口表示 1s
    private final long WINDOW_LEN = 1000;

    // 60个窗口  表示1min
    private final long WINDOW_NUM = 60;

    private final long CONTROL_TIME = WINDOW_LEN * WINDOW_NUM;

    private WindowLinkedList list = new WindowLinkedList();

    public static void main(String[] args) {
        FlowControl flowControl = new FlowControl();
        try {
            flowControl.check();
            // 业务逻辑

        } catch (FlowException f) {
            // 被限流
        }
    }

    private void check() {
        Windows windows = list.currentWindow();

        doCheck(windows);

    }

    private void doCheck(Windows windows) {
        long count = 0;
        Windows current = windows;
        // 往前遍历
        // 累加所有窗口对应的请求数量
        while (current != null) {
            count += current.count;
            current = current.pre;
        }

        // 请求超过阈值，抛异常，限流
        if (count >= THRESHOLD) {
            throw new FlowException();
        }
        windows.count++;
    }

    class FlowException extends RuntimeException {

    }

    public class Windows {
        long start;
        long end;
        long count;

        Windows pre;
        Windows next;
    }

    // 采用链表的形式
    public class WindowLinkedList {

        // 头节点，表示第一个窗口
        private Windows head;

        private Windows end;


        public Windows currentWindow() {
            // 当前时间
            long now = System.currentTimeMillis();
            // 当前时间往前推一分钟
            long startTime = now - CONTROL_TIME;

            // 计算第一个窗口的startTime
            long windowStartTime = startTime - startTime % WINDOW_LEN;

            // 没有元素，直接初始化并返回头窗口
            if (head == null) {
                return reset(windowStartTime);
            }

            Windows current = head;
            while (current != null) {
                // 找到当前一分钟内的第一个窗口
                if (current.start <= startTime && current.end > startTime) {
                    // 过期的窗口直接丢掉
                    if (current.pre != null) {
                        current.pre.next = null;
                        current.pre = null;
                    }
                    head = current;

                    long currentWindowsStartTime = windowStartTime + CONTROL_TIME - WINDOW_LEN;

                    if (end.start == currentWindowsStartTime) {
                        return end;
                    } else {
                        // 添加一个当前时间对应的窗口在尾部
                        Windows addWindows = new Windows();
                        addWindows.count = 0;
                        addWindows.start = currentWindowsStartTime;
                        addWindows.end = addWindows.start + WINDOW_LEN;
                        return addLast(addWindows);
                    }
                }
                current = current.next;
            }
            // 窗口都过期了，重置
            return reset(windowStartTime);
        }


        //重置窗口列表
        private Windows reset(long startTime) {
            head = new Windows();
            head.start = startTime;
            head.end = head.start + WINDOW_LEN;
            head.count = 0;
            end = head;
            return head;
        }

        // 插入尾部
        private Windows addLast(Windows windows) {
            end.next = windows;
            windows.pre = end;
            end = windows;
            return end;
        }

    }
}
