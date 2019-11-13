package com.bigdata.uno.service.impl;

import com.bigdata.uno.common.constant.Constant;
import com.bigdata.uno.common.model.ModelUtil;
import com.bigdata.uno.common.model.information.Info;
import com.bigdata.uno.common.model.requirement.Requirement;
import com.bigdata.uno.common.model.requirement.RequirementPoJo;
import com.bigdata.uno.common.model.requirement.RequirementStatistic;
import com.bigdata.uno.common.model.requirement.UpdateRequirement;
import com.bigdata.uno.common.util.Preconditions;
import com.bigdata.uno.repository.RequirementRepository;
import com.bigdata.uno.repository.base.Fields;
import com.bigdata.uno.service.InfoService;
import com.bigdata.uno.service.RequirementService;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequirementServiceImpl implements RequirementService {

    @Autowired
    private RequirementRepository requirementRepository;

    @Autowired
    private InfoService infoService;

    @Override
    public Requirement queryById(Long id) {
        RequirementPoJo requirementPoJo = requirementRepository.selectById(id);
        if (requirementPoJo == null) {
            return null;
        }
        Requirement requirement = Requirement.builder().build();
        ModelUtil.poJoToModel(requirementPoJo, requirement);
        return requirement;
    }

    @Override
    public Long save(Requirement requirement) {
        Preconditions.checkNotNull(requirement.getTitle(), "标题不可为空");
        Preconditions.checkNotNull(requirement.getProjectId(), "项目Id不可为空");
        Preconditions.checkNotNull(requirement.getCreator(), "创建者不可为空");
        Preconditions.checkArgument(
                requirement.getStart().equals(requirement.getEnd())
                        || requirement.getStart().before(requirement.getEnd()),
                "截止时间必须晚于起始时间"
        );
        RequirementPoJo requirementPoJo = RequirementPoJo.builder().build();
        ModelUtil.modelToPoJO(requirement, requirementPoJo);
        if (requirement.getId() != null) {
            requirementRepository.updateNotNullFields(requirementPoJo);
            return requirement.getId();
        }
        Preconditions.checkNull(
                requirementRepository.selectOne(
                        Fields.PROJECT_ID.eq(requirement.getProjectId())
                                .and(Fields.TITLE.eq(requirement.getTitle()))
                ),
                "标题不可重复"
        );
        requirementRepository.insert(requirementPoJo);
        return requirementRepository.selectOne(Fields.TITLE.eq(requirement.getTitle())).getId();
    }

    @Override
    public List<Requirement> queryAll() {
        List<RequirementPoJo> requirementPoJos = requirementRepository.selectAll();
        List<Requirement> requirements = Lists.newLinkedList();
        requirementPoJos.forEach(requirementPoJo -> {
            Requirement requirement = Requirement.builder().build();
            ModelUtil.poJoToModel(requirementPoJo, requirement);
            requirements.add(requirement);
        });
        return requirements;
    }

    @Override
    public List<Requirement> queryByProjectId(Long projectId) {
        List<RequirementPoJo> requirementPoJos = requirementRepository.selectWhere(Fields.PROJECT_ID.eq(projectId));
        requirementPoJos = requirementPoJos.stream()
                .sorted(
                        Comparator.comparing(RequirementPoJo::getPriority, Comparator.reverseOrder())
                                .thenComparing(RequirementPoJo::getStart)
                ).collect(Collectors.toList());
        List<Requirement> requirements = Lists.newLinkedList();
        requirementPoJos.forEach(requirementPoJo -> {
            Requirement requirement = Requirement.builder().build();
            ModelUtil.poJoToModel(requirementPoJo, requirement);
            requirements.add(requirement);
        });
        return requirements;
    }

    @Override
    public RequirementStatistic statistic(Long projectId) {
        RequirementStatistic requirementStatistic = RequirementStatistic.builder()
                .delay(0)
                .finished(0)
                .unStarted(0)
                .accepted(0)
                .total(0)
                .bug(0)
                .build();
        List<RequirementPoJo> requirementPoJos = requirementRepository.selectWhere(Fields.PROJECT_ID.eq(projectId));
        requirementPoJos.forEach(requirementPoJo -> {
            switch (requirementPoJo.getStatus()) {
                case Constant.RequirementStatus.CREATED:
                    requirementStatistic.setUnStarted(requirementStatistic.getUnStarted() + 1);
                    break;
                case Constant.RequirementStatus.ACCEPTED:
                    requirementStatistic.setAccepted(requirementStatistic.getAccepted() + 1);
                    break;
                case Constant.RequirementStatus.CHECKED:
                    requirementStatistic.setFinished(requirementStatistic.getFinished() + 1);
                    break;
            }
            if (requirementPoJo.getStatus() != Constant.RequirementStatus.ACCEPTED) {
                requirementStatistic.setTotal(requirementStatistic.getTotal() + 1);
            }
            if (requirementPoJo.getEnd().after(DateTime.now().toDate())) {
                requirementStatistic.setDelay(requirementStatistic.getDelay() + 1);
            }
            if (requirementPoJo.getType() == Constant.RequirementType.BUG) {
                requirementStatistic.setBug(requirementStatistic.getBug() + 1);
            }
        });
        return requirementStatistic;
    }

    @Override
    public boolean statusFlow(UpdateRequirement updateRequirement) {
        Preconditions.checkNotNull(updateRequirement.getId(), "id不可为空");
        RequirementPoJo requirementPoJo = requirementRepository.selectById(updateRequirement.getId());
        if (requirementPoJo.getStatus() == updateRequirement.getStatus()) {
            return true;
        }
        requirementPoJo.setStatus(updateRequirement.getStatus());
        requirementRepository.updateNotNullFields(requirementPoJo);
        return infoService.inform(
                "bchen",
                updateRequirement.getMentions(),
                "需求状态更新",
                updateRequirement.getContent()
        );
    }

    @Override
    public boolean updateDate(UpdateRequirement updateRequirement) {
        Preconditions.checkNotNull(updateRequirement.getId(), "id不可为空");
        RequirementPoJo requirementPoJo = requirementRepository.selectById(updateRequirement.getId());
        requirementPoJo.setEnd(updateRequirement.getEnd());
        requirementPoJo.setStart(updateRequirement.getStart());
        requirementRepository.updateNotNullFields(requirementPoJo);
        return infoService.inform(
                "bchen",
                updateRequirement.getMentions(),
                "需求排期变更",
                updateRequirement.getContent()
        );
    }

    @Override
    public List<Info> queryComments(Long id) {
        return infoService.queryInfo(id);
    }
}
