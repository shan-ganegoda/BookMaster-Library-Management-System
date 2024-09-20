package lk.projects.library.service;

import lk.projects.library.dao.LanguageDao;
import lk.projects.library.entity.Language;

import java.util.List;

public class LanguageService {

    public static List<Language> get(){
        return LanguageDao.getAll();
    }
}
