package com.springinaction.knights;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BraveKnight implements Knight,
        BeanNameAware, BeanFactoryAware, ApplicationContextAware,
        InitializingBean ,BeanPostProcessor, DisposableBean {
  private Quest quest;
  
  public BraveKnight(Quest quest) {
    this.quest = quest;       //<co id="co_injectedQuest"/>
  }
  
  public void embarkOnQuest() throws QuestException {
    quest.embark();
  }

    @Override
    public void setBeanName(String name) {
        System.out.println("set bean name:" + name);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("setBeanFactory");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("setApplicationContext");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization");
        return this;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization");
        return this;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy");
    }
}
