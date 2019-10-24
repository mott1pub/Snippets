package com.g2switch.core.gateway;

import com.g2switch.common.concurrent.FutureJobResponse;
import com.g2switch.common.concurrent.FutureJobs;
import com.g2switch.common.concurrent.FutureJobsExecutor;
import com.g2switch.common.concurrent.FutureJobsExecutorFactory;
import com.g2switch.common.context.MessageContext;
import com.g2switch.common.logger.TransactionError;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// wrap the excutor; create the tasks
public class HttpConnectionThreadPool {
        
    private static Map<String, FutureJobsExecutor> executorMap = new ConcurrentHashMap<String, FutureJobsExecutor>(10);

    private static synchronized FutureJobsExecutor getExecutor(String host) {
        FutureJobsExecutor executor = executorMap.get(host);
        if (executor == null) {
            executor = FutureJobsExecutorFactory.getInstance().getExecutorService("http_" + host  + "_connection");
            executorMap.put(host, executor);
        }
        return executor;
    }

    public static String send(String message, Map<String, String> mapOfParams,boolean retainNewLine, WSConfiguration config) {
        MessageContext.get().getLoggingContext().addBreadcrumb("HttpConnectionThreadPool: start send");
        FutureJobsExecutor threadPool = getExecutor(config.getHost());
        
        HostComTask hostComTask = new HostComTask();
        
        hostComTask.setPayload(message);
        hostComTask.setConfig(config);
        hostComTask.setRetainNewLine(retainNewLine);
        hostComTask.setMapOfParams(mapOfParams);
        
        TransactionError transactionError = (TransactionError) MessageContext.get().getTransactionContext().get(TransactionError.TRANSACTION_ERROR);
        if(transactionError != null) {
        	hostComTask.setTransactionError(transactionError);
       	}
        
        FutureJobs<String> futureJobs = new FutureJobs<String>();
        futureJobs.addJob(hostComTask);
        
        List<FutureJobResponse<String>> responses = threadPool.executeJobs(futureJobs, config.getReadTimeOut()/1000);
                
        MessageContext.get().getLoggingContext().addBreadcrumb("HttpConnectionThreadPool: end send");
        return responses.get(0).getResponse();
    }
    
}
