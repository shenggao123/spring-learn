package com.dubbo.api;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.zookeeper.ZookeeperDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class MyDataSource implements InitFunc {
    final String remoteAddress = "127.0.0.1:2181";
    final String groupId = "Sentinel-Demo";
    final String flowDataId = "SYSTEM-CODE-DEMO-FLOW";
    @Override
    public void init() throws Exception {
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ZookeeperDataSource<>(remoteAddress, groupId, flowDataId,
                source -> JSON.parseObject(source, new com.alibaba.fastjson.TypeReference<List<FlowRule>>() {}));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }
}
