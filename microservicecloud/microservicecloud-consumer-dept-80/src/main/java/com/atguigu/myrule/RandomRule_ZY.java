package com.atguigu.myrule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.Random;

public class RandomRule_ZY  extends AbstractLoadBalancerRule {
    //需求：每台机器访问5次，轮询

    private int currentIndex = 0 ;
    private int total = 0 ;

    @SuppressWarnings({"RCN_REDUNDANT_NULLCHECK_OF_NULL_VALUE"})
    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        } else {
            Server server = null;

            while(server == null) {
                if (Thread.interrupted()) {
                    return null;
                }

                List<Server> upList = lb.getReachableServers();
                List<Server> allList = lb.getAllServers();
                int serverCount = allList.size();
                if (serverCount == 0) {
                    return null;
                }

//                int index = this.rand.nextInt(serverCount);
//                server = (Server)upList.get(index);
                if(total < 5){
                    server = (Server) upList.get(currentIndex);
                    total ++ ;
                }else{
                    total = 0;
                    currentIndex++;
                    if(currentIndex < serverCount){
                        server = (Server) upList.get(currentIndex);
                    }else {
                        currentIndex = 0;
                        server = (Server) upList.get(currentIndex);
                    }
                }

                if (server == null) {
                    Thread.yield();
                } else {
                    if (server.isAlive()) {
                        return server;
                    }

                    server = null;
                    Thread.yield();
                }
            }

            return server;
        }
    }

    public Server choose(Object key) {
        return this.choose(this.getLoadBalancer(), key);
    }

    public void initWithNiwsConfig(IClientConfig clientConfig) {
    }
}
