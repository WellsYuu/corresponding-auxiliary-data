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
	 * 给容器中注册组件的方式
	 * 1,@Bean: [导入第三方的类或包的组件],比如Person为第三方的类, 需要在我们的IOC容器中使用
	 * 2,包扫描+组件的标注注解(@ComponentScan:  @Controller, @Service  @Reponsitory  @ Componet),一般是针对 我们自己写的类,使用这个
	 * 3,@Import:[快速给容器导入一个组件] 注意:@Bean有点简单
	 *      a,@Import(要导入到容器中的组件):容器会自动注册这个组件,bean 的 id为全类名
	 *      b,ImportSelector:是一个接口,返回需要导入到容器的组件的全类名数组
	 *      c,ImportBeanDefinitionRegistrar:可以手动添加组件到IOC容器, 所有Bean的注册可以使用BeanDifinitionRegistry
	 *          写JamesImportBeanDefinitionRegistrar实现ImportBeanDefinitionRegistrar接口即可
	 *  4,使用Spring提供的FactoryBean(工厂bean)进行注册
	 *     
	 *   
	 */
	//容器启动时初始化person的bean实例
	@Bean("person")
	public Person persond(){
		System.out.println("aaaaaaaaaaaa");
		return new Person("james",20);
	}
	//@Bean("person1")
	public Person persona(int a){
		System.out.println("bbbbbbbbbbb");
		return new Person("james",20);
	}
	@Bean
	public JamesFactoryBean jamesFactoryBean(){
		return new JamesFactoryBean();
	}
}
