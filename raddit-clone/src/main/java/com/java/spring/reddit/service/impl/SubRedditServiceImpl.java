package com.java.spring.reddit.service.impl;

import com.java.spring.reddit.dto.SubRedditDto;
import com.java.spring.reddit.dto.SubRedditResponseDto;
import com.java.spring.reddit.entities.SubReddit;
import com.java.spring.reddit.repository.SubRedditRepository;
import com.java.spring.reddit.service.SubRedditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class SubRedditServiceImpl implements SubRedditService {

    final SubRedditRepository subRedditRepository;

    @Override
    @Transactional
    public SubRedditDto save(final SubRedditDto subRedditDto) {
        final SubReddit subReddit = mapToSubReddit(subRedditDto);
        final SubReddit savedSubReddit = subRedditRepository.save(subReddit);
        subRedditDto.setId(savedSubReddit.getId());
        return subRedditDto;
    }

    @Override
    @Transactional(readOnly = true)
    public SubRedditResponseDto getAll() {
        final List<SubRedditDto> subRedditDtos = subRedditRepository.findAll().stream()
                .map(this::mapToSubRedditDto)
                .collect(Collectors.toList());
        return new SubRedditResponseDto(subRedditDtos, subRedditDtos.size());
    }

    private SubReddit mapToSubReddit(final SubRedditDto subRedditDto) {
        final SubReddit subReddit = new SubReddit();
        subReddit.setName(subRedditDto.getName());
        subReddit.setDescription(subRedditDto.getDescription());
        return subReddit;
    }

    private SubRedditDto mapToSubRedditDto(final SubReddit subReddit) {
        final SubRedditDto subRedditDto = new SubRedditDto();
        subRedditDto.setId(subReddit.getId());
        subRedditDto.setName(subReddit.getName());
        subRedditDto.setDescription(subReddit.getDescription());
        return subRedditDto;
    }
}
