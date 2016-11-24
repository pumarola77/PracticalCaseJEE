package ejb;
import javax.ejb.Local;

import jpa.UserJPA;

@Local
public interface UserFacade {
	/*
	 Metodes invocats localment
   */
	public int registerUser(String nif, String name , String surname , String phone, String password , String email);
	public boolean updatePersonalData(String nif, String name , String surname , String phone, String password , String email);
	public UserJPA findUser(String nif);
	public String login(String email,String password);
	public boolean logout();
}
