package lk.projects.library.service;

import lk.projects.library.dao.MemberStatusDao;
import lk.projects.library.entity.MemberStatus;

import java.util.List;

public class MemberStatusService {

    public static List<MemberStatus> get(){
        return MemberStatusDao.getAll();
    }

}
