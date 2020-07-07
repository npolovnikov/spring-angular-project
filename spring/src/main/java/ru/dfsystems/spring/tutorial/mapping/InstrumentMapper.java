package ru.dfsystems.spring.tutorial.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;

import java.util.ArrayList;
import java.util.List;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@Mapper
public interface InstrumentMapper  extends BaseMapper<Instrument, InstrumentDto, InstrumentListDto> {

    InstrumentListDto instrumentToInstrumentListDto(Instrument instrument);

    InstrumentDto instrumentToInstrumentDto(Instrument instrument);

    Instrument instrumentDtoToInstrument(InstrumentDto instrumentDto);

    default List<InstrumentListDto> instrumentListToInstrumentListDto(List<Instrument> instruments) {
        List<InstrumentListDto> instrumentDto = new ArrayList<>();

        for (Instrument instrument : instruments) {
            instrumentDto.add(instrumentToInstrumentListDto(instrument));
        }
        return instrumentDto;
    }

}
