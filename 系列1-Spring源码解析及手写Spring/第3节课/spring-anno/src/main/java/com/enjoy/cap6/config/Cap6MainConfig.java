package com.enjoy.cap6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.enjoy.cap1.Person;
import com.enjoy.cap6.bean.Cat;
import com.enjoy.cap6.bean.Dog;

@Configuration
@Import(value = { Dog.class,Cat.class, JamesImportSelector.class,JamesImportBeanDefinitionRegistrar.class })
public class Cap6MainConfig {
	/*
	 * ��������ע������ķ�ʽ
	 * 1,@Bean: [��������������������],����PersonΪ����������, ��Ҫ�����ǵ�IOC������ʹ��
	 * 2,��ɨ��+����ı�עע��(@ComponentScan:  @Controller, @Service  @Reponsitory  @ Componet),һ������� �����Լ�д����,ʹ�����
	 * 3,@Import:[���ٸ���������һ�����] ע��:@Bean�е��
	 *      a,@Import(Ҫ���뵽�����е����):�������Զ�ע��������,bean �� idΪȫ����
	 *      b,ImportSelector:��һ���ӿ�,������Ҫ���뵽�����������ȫ��������
	 *      c,ImportBeanDefinitionRegistrar:�����ֶ���������IOC����, ����Bean��ע�����ʹ��BeanDifinitionRegistry
	 *          дJamesImportBeanDefinitionRegistrarʵ��ImportBeanDefinitionRegistrar�ӿڼ���
	 *  4,ʹ��Spring�ṩ��FactoryBean(����bean)����ע��
	 *     
	 *   
	 */
	//��������ʱ��ʼ��person��beanʵ��
	@Bean("person")
	public Person person(){
		return new Person("james",20);
	}
	@Bean
	public JamesFactoryBean jamesFactoryBean(){
		return new JamesFactoryBean();
	}
}
