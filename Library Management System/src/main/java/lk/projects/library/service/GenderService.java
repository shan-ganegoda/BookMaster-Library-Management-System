package lk.projects.library.service;

import lk.projects.library.dao.GenderDao;
import lk.projects.library.entity.Gender;

import java.util.List;

public class GenderService {

    public static List<Gender> get(){
        return GenderDao.getAll();
    }
}
