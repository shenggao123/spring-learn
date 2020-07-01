package com.lebron;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.dubbo.api.TestService;
import org.apache.dubbo.config.annotation.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public void test() {
        System.out.println("asdasd");
    }

    @Override
    public void test2() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("com.dubbo.api.TestService:test()");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        // Set limit QPS to 20.
        rule.setCount(100);
        rule.as(FlowRule.class);
        rule.setLimitApp("consumer-sg");
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
