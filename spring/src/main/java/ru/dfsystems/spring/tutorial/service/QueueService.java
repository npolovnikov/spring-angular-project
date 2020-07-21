package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.enums.ActionTypeEnum;
import ru.dfsystems.spring.tutorial.enums.ObjectType;
import ru.dfsystems.spring.tutorial.enums.StateEnum;
import ru.dfsystems.spring.tutorial.generated.tables.daos.QueueItemDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.QueueItem;
import ru.dfsystems.spring.tutorial.security.UserContext;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.QueueItem.QUEUE_ITEM;

@Service
@AllArgsConstructor
public class QueueService {
    private DSLContext jooq;
    private QueueItemDao queueItemDao;
    private RoomService roomService;
    private InstrumentService instrumentService;
    private StudentService studentService;
    private UserContext userContext;

    public void process(QueueItem queueItem) {
        try {
            switch (ObjectType.valueOf(queueItem.getObjectType())) {
                case ROOM:
                    roomService.process(ActionTypeEnum.valueOf(queueItem.getActionType()), queueItem.getObjectData(), queueItem.getUserId());
                    break;
                case INSTRUMENT:
                    instrumentService.process(ActionTypeEnum.valueOf(queueItem.getActionType()), queueItem.getObjectData(), queueItem.getUserId());
                    break;
                case STUDENT:
                    studentService.process(ActionTypeEnum.valueOf(queueItem.getActionType()), queueItem.getObjectData(), queueItem.getUserId());
                    break;
                default:
                    throw new RuntimeException("Тип объекта не найден");
            }

            queueItem.setUpdateDate(LocalDateTime.now());
            queueItem.setState(StateEnum.SUCCESS.name());
            queueItemDao.update(queueItem);
        } catch (Exception e){
            queueItem.setState(StateEnum.FAIL.name());
            queueItem.setMessage(e.getMessage());
            queueItemDao.update(queueItem);
        }
    }

    public void addToQueue(ObjectType objectType, ActionTypeEnum actionType, String objectData) {
        QueueItem qi = new QueueItem();
        qi.setActionType(actionType.name());
        qi.setObjectType(objectType.name());
        qi.setObjectData(objectData);
        qi.setState(StateEnum.NEW.name());
        qi.setUserId(userContext.getUser().getId());

        queueItemDao.insert(qi);
    }

    public List<QueueItem> getProcessQueueItemByPC(String pcName) {
        return jooq.selectFrom(QUEUE_ITEM)
                .where(QUEUE_ITEM.NODE_NAME.eq(pcName).and(QUEUE_ITEM.STATE.eq(StateEnum.PROCESS.name())))
                .fetchInto(QueueItem.class);
    }

    public void reserveQueueItemByPc(int maxItemCount, String pcName) {
        jooq.update(QUEUE_ITEM)
                .set(QUEUE_ITEM.UPDATE_DATE, LocalDateTime.now())
                .set(QUEUE_ITEM.NODE_NAME, pcName)
                .set(QUEUE_ITEM.STATE, StateEnum.PROCESS.name())
                .where(QUEUE_ITEM.STATE.eq(StateEnum.NEW.name()))
                .limit(maxItemCount)
                .execute();
    }
}
