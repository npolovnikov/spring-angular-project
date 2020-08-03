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

import static ru.dfsystems.spring.tutorial.generated.Tables.QUEUE_ITEM;

@Service
@AllArgsConstructor
public class QueueService {
    private DSLContext jooq;
    private QueueItemDao queueItemDao;
    private RoomService roomService;
    private InstrumentService instrumentService;
    private CourseService courseService;
    private LessonService lessonService;
    private StudentService studentService;
    private TeacherService teacherService;
    private UserContext userContext;

    /**
     * Обрабатывает элемент (отправляет в нужный сервис на обработку)
     */
    public void process(QueueItem queueItem) {
        try {
            switch (ObjectType.valueOf(queueItem.getObjectType())) {
                case ROOM:
                    roomService.process(ActionTypeEnum.valueOf(queueItem.getActionType()), queueItem.getObjectData(), queueItem.getUserId());
                    break;
                case INSTRUMENT:
                    instrumentService.process(ActionTypeEnum.valueOf(queueItem.getActionType()), queueItem.getObjectData(), queueItem.getUserId());
                    break;
                case COURSE:
                    courseService.process(ActionTypeEnum.valueOf(queueItem.getActionType()), queueItem.getObjectData(), queueItem.getUserId());
                    break;
                case LESSON:
                    lessonService.process(ActionTypeEnum.valueOf(queueItem.getActionType()), queueItem.getObjectData(), queueItem.getUserId());
                    break;
                case STUDENT:
                    studentService.process(ActionTypeEnum.valueOf(queueItem.getActionType()), queueItem.getObjectData(), queueItem.getUserId());
                    break;
                case TEACHER:
                    teacherService.process(ActionTypeEnum.valueOf(queueItem.getActionType()), queueItem.getObjectData(), queueItem.getUserId());
                    break;
                default:
                    throw new RuntimeException("Тип объекта не найден");
            }

            queueItem.setUpdateDate(LocalDateTime.now());
            queueItem.setState(StateEnum.SUCCESS.name());
            queueItemDao.update(queueItem);
        } catch (Exception e) {
            queueItem.setState(StateEnum.FAIL.name());
            queueItem.setMessage(e.getMessage());
            queueItemDao.update(queueItem);
        }
    }

    /**
     * Добавялет элемент в очередь (добавялет айтем очереди в бд).
     * От вызывающей стороны получаем тип объекта, действие и сам объект.
     */
    public void addToQueue(ObjectType objectType, ActionTypeEnum actionType, String objectData) {
        QueueItem qi = new QueueItem();
        /* CREATE, UPDATE, DELETE */
        qi.setActionType(actionType.name());
        /*   ROOM,INSTRUMENT */
        qi.setObjectType(objectType.name());
        qi.setObjectData(objectData);
        /* NEW, PROCESS, FAIL, SUCCESS*/
        qi.setState(StateEnum.NEW.name());
        qi.setUserId(userContext.getUser().getId());

        queueItemDao.insert(qi);
    }

    /**
     * Получаем элементы очереди с заданным pcName и состоянием "PROCESS"
     */
    public List<QueueItem> getProcessQueueItemByPC(String pcName) {
        return jooq.selectFrom(QUEUE_ITEM)
                .where(QUEUE_ITEM.NODE_NAME.eq(pcName).and(QUEUE_ITEM.STATE.eq(StateEnum.PROCESS.name())))
                .fetchInto(QueueItem.class);
    }

    /**
     * Резервируем элементы для обработки: устанавливаем pcName и статус PROCESS для 5 элементов.
     * pcName нужен, чтобы другая нода не обработала элементы.
     */
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
