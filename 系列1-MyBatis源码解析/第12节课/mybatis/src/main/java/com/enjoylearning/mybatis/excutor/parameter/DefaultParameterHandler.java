package com.enjoylearning.mybatis.excutor.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.omg.CosNaming.IstringHelper;

public class DefaultParameterHandler implements ParameterHandler {

	private Object parameter;
	
	
	
	
	
	

	public DefaultParameterHandler(Object parameter) {
		super();
		this.parameter = parameter;
	}




	public Object getParameter() {
		return parameter;
	}




	public void setParameter(Object parameter) {
		this.parameter = parameter;
	}




	@Override
	public void setParameters(PreparedStatement ps) throws SQLException {
		if(parameter == null){
			return;
		}
		if(parameter.getClass().isArray()){
			Object[] paramArray =  (Object[]) parameter;
			for (int i = 0; i < paramArray.length; i++) {
				if(paramArray[i] instanceof Integer){
					ps.setInt(i+1, (int)paramArray[i]);
				}else if(paramArray[i] instanceof String){
					ps.setString(i+1, (String)paramArray[i]);
				}else if(paramArray[i] instanceof Long){
					ps.setLong(i+1, (long)paramArray[i]);
				}
			}
		}

	}
	
	public static void main(String[] args) {
		Object[] ttt = new Object[]{"sdfsfd",1};
		for (int i = 0; i < ttt.length; i++) {
			
			if(ttt[i] instanceof Integer){
				System.out.println(ttt[i]);
			}else if(ttt[i] instanceof String){
				System.out.println(ttt[i]);
				
			}
		}
	}

}
