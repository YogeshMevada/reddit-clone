package com.java.spring.reddit.service.impl;

import com.java.spring.reddit.dto.SubRedditRequest;
import com.java.spring.reddit.dto.SubRedditResponse;
import com.java.spring.reddit.entities.SubReddit;
import com.java.spring.reddit.exception.SystemException;
import com.java.spring.reddit.mapper.SubRedditMapper;
import com.java.spring.reddit.repository.SubRedditRepository;
import com.java.spring.reddit.service.AuthService;
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

    private final SubRedditRepository subRedditRepository;

    private final AuthService authService;

    private final SubRedditMapper subRedditMapper;

    @Override
    public SubReddit save(final SubReddit subReddit) {
        log.info("Save subreddit.");
        return subRedditRepository.save(subReddit);
    }

    @Override
    @Transactional
    public SubRedditRequest createSubReddit(final SubRedditRequest subRedditRequest) {
        final SubReddit subReddit = subRedditMapper.mapToSubReddit(subRedditRequest, authService.getCurrentUser());
        final SubReddit savedSubReddit = save(subReddit);
        subRedditRequest.setId(savedSubReddit.getId());
        return subRedditRequest;
    }

    @Override
    @Transactional(readOnly = true)
    public SubRedditResponse findAll() {
        final List<SubRedditRequest> subRedditDtos = subRedditRepository.findAll().stream().map(subRedditMapper::mapToSubRedditDto).collect(Collectors.toList());
        return new SubRedditResponse(subRedditDtos, subRedditDtos.size());
    }

    @Override
    public Optional<SubReddit> findById(final Long id) {
        return subRedditRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public SubRedditRequest getSubReddit(final Long id) {
        final SubReddit subReddit = findById(id).orElseThrow(() -> new SystemException("No subreddit found with id " + id));
        return subRedditMapper.mapToSubRedditDto(subReddit);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SubReddit> findByName(final String subRedditName) {
        return subRedditRepository.findByName(subRedditName);
    }
}
