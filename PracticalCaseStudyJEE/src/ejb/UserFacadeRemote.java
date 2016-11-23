package ejb;
import javax.ejb.Remote;

@Remote
public interface UserFacadeRemote{
	/*
	 Metodes invocats remotament
   */
	public int registerUser(String nif, String name , String surname , String phone, String password , String email);
	public boolean updatePersonalData(String nif, String name , String surname , String phone, String password , String email);
	public String login(String email, String pwd);
	public boolean logout();
}