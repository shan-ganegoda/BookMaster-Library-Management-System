package lk.projects.library.service;


import lk.projects.library.dao.FineStatusDao;
import lk.projects.library.entity.FineStatus;

import java.util.List;

public class FineStatusService {

    public static List<FineStatus> get(){
        return FineStatusDao.getAll();
    }

    public static FineStatus getById(int id){
        return FineStatusDao.getById(id);
    }
}
