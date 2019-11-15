package com.bigdata.uno.service.impl;

import com.alibaba.fastjson.JSON;
import com.bigdata.uno.common.model.ModelUtil;
import com.bigdata.uno.common.model.information.Info;
import com.bigdata.uno.common.model.information.InfoPoJo;
import com.bigdata.uno.common.model.user.User;
import com.bigdata.uno.common.util.Preconditions;
import com.bigdata.uno.repository.InfoRepository;
import com.bigdata.uno.repository.base.Fields;
import com.bigdata.uno.service.InfoService;
import com.bigdata.uno.service.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InfoServiceImpl implements InfoService {

    @Autowired
    private InfoRepository infoRepository;

    @Autowired
    private UserService userService;

    @Override
    public boolean inform(String creator, List<String> recipients, String title, String content) {
        if (content == null || recipients == null) {
            return false;
        }
        InfoPoJo infoPoJo = InfoPoJo.builder()
                .creator(creator)
                .recipient(JSON.toJSONString(recipients))
                .content(content)
                .build();
        return infoRepository.insert(infoPoJo) > 0;
    }

    @Override
    public List<Info> queryInfo(Long requirementId) {
        List<InfoPoJo> infoPoJos = infoRepository.selectWhere(Fields.REQUIREMENT_ID.eq(requirementId));
        List<Info> infos = Lists.newLinkedList();
        List<String> userNames = Lists.newLinkedList();
        infoPoJos.forEach(infoPoJo -> userNames.add(infoPoJo.getCreator()));
        List<User> users = userService.queryByNames(userNames);
        Map<String, User> userMap = new HashMap<>();
        users.forEach(user -> userMap.put(user.getName(), user));
        infoPoJos.forEach(infoPoJo -> {
            Info info = Info.builder().build();
            ModelUtil.poJoToModel(infoPoJo, info);
            info.setUser(userMap.get(infoPoJo.getCreator()));
            infos.add(info);
        });
        return infos;
    }

    @Override
    public Long save(Info info) {
        Preconditions.checkNotNull(info.getUser(), "创建人不可为空");
        Preconditions.checkNotNull(info.getContent(), "内容不可为空");
        Long currentTime = System.currentTimeMillis() / 1000;
        InfoPoJo infoPoJo = InfoPoJo.builder().build();
        ModelUtil.modelToPoJO(info, infoPoJo);
        //暂时先假用户
        User user = userService.queryByName("bchen");
        info.setUser(user);
        infoPoJo.setCreator(info.getUser().getName());
        if (infoPoJo.getId() != null) {
            infoRepository.updateNotNullFields(infoPoJo);
            return infoPoJo.getId();
        }
        infoRepository.insert(infoPoJo);
        return infoRepository.selectOne(
                Fields.CREATED_AT.gt(currentTime).and(Fields.CREATOR.eq(infoPoJo.getCreator()))
        ).getId();
    }
}
