import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
//        initFlowRules();
        initDegradeRules();
        test();
    }


    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(10);
        rule.as(FlowRule.class);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    private static void initDegradeRules() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        rule.setTimeWindow(3);
        //  set threshold rt, 10 ms
        rule.setCount(10);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }


    public static void test() {
        for(int i=0;i<100;i++) {
            new Thread(() -> {
            while (true) {
                Entry entry = null;
                try {
                    entry = SphU.entry("HelloWorld");
                    System.out.println(1);
                    Thread.sleep(500);
                } catch (BlockException e) {
//                e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (entry != null) {
                        entry.exit();
                    }
                }
            }
        }).start();
        }
    }


}
