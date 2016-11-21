package ejb;
import javax.ejb.Remote;

@Remote
public interface UserFacadeRemote{
	/*
	 Metodes invocats remotament
   */
	public boolean registerUser(String nif, String name , String surname , String phone, String password , String email);
	public void updatePersonalData(String nif, String name , String surname , String phone, String password , String email);
}