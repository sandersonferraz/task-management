package br.com.sanderson.managements.service.impl;

import br.com.sanderson.managements.domain.model.Project;
import br.com.sanderson.managements.dto.ProjectResponse;
import br.com.sanderson.managements.repository.ProjectRepository;
import br.com.sanderson.managements.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    @Override
    public List<ProjectResponse> getProject() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream().map(this::buildResponse).collect(Collectors.toList());
    }

    private ProjectResponse buildResponse(Project project) {
        return ProjectResponse.builder()
                .projectId(project.getId())
                .name(project.getName())
                .build();

    }
}
