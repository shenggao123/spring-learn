package com.lebron;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ProviderMain {

    public static void main(String[] args) throws IOException {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("com.dubbo.api.TestService:test()");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        // Set limit QPS to 20.
        rule.setCount(10);
        rule.as(FlowRule.class);
        rule.setLimitApp("consumer-sg");
        rules.add(rule);
        FlowRuleManager.loadRules(rules);


//        SpringApplication.run(ProviderMain.class, args);
        SpringApplication application = new SpringApplication(ProviderMain.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
//        SpringApplication.run(Main.class, args);
        System.in.read();
    }
}
