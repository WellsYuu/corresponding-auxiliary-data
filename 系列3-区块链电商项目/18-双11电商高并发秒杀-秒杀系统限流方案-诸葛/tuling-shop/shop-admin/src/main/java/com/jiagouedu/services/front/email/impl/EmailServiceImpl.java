package com.jiagouedu.services.front.email.impl;import com.jiagouedu.core.ServersManager;import com.jiagouedu.services.front.email.EmailService;import com.jiagouedu.services.front.email.bean.Email;import com.jiagouedu.services.front.email.dao.EmailDao;import org.springframework.stereotype.Service;import javax.annotation.Resource;@Service("emailServiceFront")public class EmailServiceImpl extends ServersManager<Email, EmailDao> implements        EmailService {    @Resource(name = "emailDaoFront")    @Override    public void setDao(EmailDao emailDao) {        this.dao = emailDao;    }    @Override	public void updateEmailInvalidWhenReg(Email email) {		dao.updateEmailInvalidWhenReg(email);	}}