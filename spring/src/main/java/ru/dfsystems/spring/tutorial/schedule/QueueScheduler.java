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

    @Scheduled(fixedRate = 5 * 1000) // расписание, метод будет запускаться каждые 5 секунд
    public void run() throws UnknownHostException {
        String pcName = InetAddress.getLocalHost().getHostName();
        /* получаем список элементов очереди */
        //получаем 5 зарезервированных элементов со статусом PROCESS
        List<QueueItem> items = queueService.getProcessQueueItemByPC(pcName);
        items.forEach(qi -> queueService.process(qi));

/* резервируем 5 элементов очереди для следующей обработки, выставляем состояние в Process.
   Устанавилваем наш pcName в ноду, чтобы другие компьютеры не обработали наши элементы очереди */
        queueService.reserveQueueItemByPc(maxItemCount, pcName);
    }
}