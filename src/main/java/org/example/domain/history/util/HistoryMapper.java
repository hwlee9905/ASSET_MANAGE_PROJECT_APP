package org.example.domain.history.util;

import lombok.Data;
import org.example.domain.history.dto.SaveHistoryDto;
import org.example.domain.history.dto.response.GetHistoriesResponseDto;
import org.example.domain.history.entity.History;
import org.example.domain.history.dto.Afterjson;
import org.example.domain.history.dto.Beforejson;
import org.example.domain.manager.entity.Manager;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class HistoryMapper {
    private final ModelMapper modelMapper;
    private Manager loginMember;
    public void convertHistoryFromDto(GetHistoriesResponseDto getHistoriesResponseDto, History history) {
        modelMapper.map(history,getHistoriesResponseDto);
    }
    public History createHistoryFromDto(SaveHistoryDto saveHistoryDto) {
        History history = modelMapper.map(saveHistoryDto , History.class);
        history.setChangeddate(new Date());
        history.setChangedby(loginMember.getName());
        return history;
    }
    public History createHistoryFromBeforeAfter(Beforejson before, Afterjson after) {
        History history = modelMapper.map(before , History.class);
        modelMapper.map(after, history);
        history.setChangeddate(new Date());
        history.setChangedby(loginMember.getName());
        return history;
    }
}
