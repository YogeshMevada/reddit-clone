package com.java.spring.reddit.service.impl;

import com.java.spring.reddit.dto.SubRedditDto;
import com.java.spring.reddit.dto.SubRedditResponseDto;
import com.java.spring.reddit.entities.SubReddit;
import com.java.spring.reddit.exception.SystemException;
import com.java.spring.reddit.mapper.SubRedditMapper;
import com.java.spring.reddit.repository.SubRedditRepository;
import com.java.spring.reddit.service.SubRedditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class SubRedditServiceImpl implements SubRedditService {

    final SubRedditRepository subRedditRepository;

    final SubRedditMapper subRedditMapper;

    @Override
    @Transactional
    public SubRedditDto createSubReddit(final SubRedditDto subRedditDto) {
        final SubReddit subReddit = subRedditMapper.mapToSubReddit(subRedditDto);
        final SubReddit savedSubReddit = subRedditRepository.save(subReddit);
        subRedditDto.setId(savedSubReddit.getId());
        return subRedditDto;
    }

    @Override
    @Transactional(readOnly = true)
    public SubRedditResponseDto findAll() {
        final List<SubRedditDto> subRedditDtos = subRedditRepository.findAll().stream().map(subRedditMapper::mapToSubRedditDto).collect(Collectors.toList());
        return new SubRedditResponseDto(subRedditDtos, subRedditDtos.size());
    }

    @Override
    public Optional<SubReddit> findById(Long id) {
        return subRedditRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public SubRedditDto getSubReddit(final Long id) {
        final SubReddit subReddit = subRedditRepository.findById(id).orElseThrow(() -> new SystemException("No subreddit found with id " + id));
        return subRedditMapper.mapToSubRedditDto(subReddit);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SubReddit> findByName(final String subRedditName) {
        return subRedditRepository.findByName(subRedditName);
    }
}
