package ru.student.studentSpring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.student.studentSpring.tutorial.dao.InstrumentDaoImpl;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Instruments;
import java.util.List;

@Service
@AllArgsConstructor
public class InstrumentService {

    private InstrumentDaoImpl instrumentDao;

    public List<Instruments> getAllInstruments() {
        instrumentDao.fetchOneById(12);
        instrumentDao.getActiveByIdd(12);
        return instrumentDao.findAll();
    }

    public Page<Instruments> getInstrumentsByParam(PageParams<InstrumentParams> pageParams) {
        return instrumentDao.getInstrumentsByParam(pageParams);
    }
}
