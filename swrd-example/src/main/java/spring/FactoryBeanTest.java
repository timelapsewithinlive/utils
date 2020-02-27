package spring;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class FactoryBeanTest implements FactoryBean {

    @PostConstruct
    public void init(){
        Object ooo=this;
        System.out.println(ooo);
    }
    @Override
    public Object getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
