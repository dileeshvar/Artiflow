package edu.ssn.sase.artiflow.UserValidator;

import java.sql.SQLException;

import edu.ssn.sase.artiflow.functions.UserManager;
import edu.ssn.sase.artiflow.models.User;

public class ValidateUser implements ValidatorInterface{

	@Override
	public User performValidation(String userName, String password, UserManager mgr) {
		User user = null;
		try {
			user = mgr.checkLogin(userName, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

}
