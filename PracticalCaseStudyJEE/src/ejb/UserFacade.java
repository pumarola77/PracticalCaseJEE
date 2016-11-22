package ejb;
import javax.ejb.Local;

@Local
public interface UserFacade {
	/*
	 Metodes invocats localment
   */
	public boolean registerUser(String nif, String name , String surname , String phone, String password , String email);
	public boolean updatePersonalData(String nif, String name , String surname , String phone, String password , String email);
	public boolean login(String email,String password);
	public boolean logout();
}
