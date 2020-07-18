package ru.dfsystems.spring.tutorial.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.QueueItem;
import ru.dfsystems.spring.tutorial.service.QueueService;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Component
public class QueueScheduler {
    private QueueService queueService;

    public QueueScheduler(QueueService queueService) {
        this.queueService = queueService;
    }

    private int maxItemCount = 5;

    @Scheduled(fixedRate = 5 * 1000)
    public void run() throws UnknownHostException {
        String pcName = InetAddress.getLocalHost().getHostName();
        List<QueueItem> items = queueService.getProcessQueueItemByPC(pcName);
        items.forEach(qi -> queueService.process(qi));

        queueService.reserveQueueItemByPc(maxItemCount, pcName);
    }

}
