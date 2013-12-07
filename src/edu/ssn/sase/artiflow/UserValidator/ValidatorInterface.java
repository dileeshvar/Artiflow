package edu.ssn.sase.artiflow.UserValidator;

import edu.ssn.sase.artiflow.functions.UserManager;
import edu.ssn.sase.artiflow.models.User;

public interface ValidatorInterface {
	public User performValidation(String userName, String password, UserManager mgr);
}
