package com.jiagouedu.web.action.manage.email;import com.jiagouedu.services.manage.email.EmailService;import com.jiagouedu.services.manage.email.bean.Email;import com.jiagouedu.web.action.BaseController;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Controller;import org.springframework.web.bind.annotation.RequestMapping;/** * 发送邮件列表 * @author wukong 图灵学院 QQ:245553999 */@Controller@RequestMapping("/manage/email/")public class EmailAction extends BaseController<Email> {	private static final long serialVersionUID = 1L;	@Autowired	private EmailService emailService;	private static final String page_toList = "/manage/email/emailList";	private EmailAction() {		super.page_toList = page_toList;		super.page_toAdd = null;		super.page_toEdit = null;	}	@Override	public EmailService getService() {		return emailService;	}	public void setEmailService(EmailService emailService) {		this.emailService = emailService;	}}