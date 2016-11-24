package ejb;
import javax.ejb.Remote;

import jpa.UserJPA;

@Remote
public interface UserFacadeRemote{
	/*
	 Metodes invocats remotament
   */
	public int registerUser(String nif, String name , String surname , String phone, String password , String email);
	public boolean updatePersonalData(String nif, String name , String surname , String phone, String password , String email);
	public UserJPA findUser(String nif);
	public String login(String email, String pwd);
	public boolean logout();
}