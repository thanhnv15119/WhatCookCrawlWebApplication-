package dao;

import entity.Method;
import entity.Recipe;
import jaxb.MethodType;
import jaxb.MethodsType;

import java.util.ArrayList;
import java.util.List;

public class MethodDAO extends BaseDAO<Method, Integer> {

    public MethodDAO() {
    }

    public boolean save(Method method) {
        if (method == null) {
            return false;
        }
        if (method.getContent() == null || method.getStep() == null) {
            return false;
        }
        insert(method);
        return true;
    }

}
