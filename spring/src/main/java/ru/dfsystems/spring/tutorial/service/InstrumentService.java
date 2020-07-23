//package ru.dfsystems.spring.tutorial.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import ru.dfsystems.spring.tutorial.dao.instrument.InstrumentDaoImpl;
//import ru.dfsystems.spring.tutorial.dao.instrument.InstrumentListDao;
//import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
//import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentHistoryDto;
//import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
//import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
//import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
//import ru.dfsystems.spring.tutorial.mapping.MappingService;
//
//@Service
//public class InstrumentService  extends BaseService<InstrumentListDto, InstrumentDto, InstrumentParams, Instrument> {
//    @Autowired
//    public InstrumentService(MappingService mappingService, InstrumentListDao instrumentListDao, InstrumentDaoImpl instrumentDao) {
//        super(mappingService, instrumentListDao, instrumentDao, InstrumentListDto.class, InstrumentDto.class, Instrument.class);
//    }
//}
