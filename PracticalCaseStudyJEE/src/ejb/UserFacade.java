package ejb;
import javax.ejb.Local;

@Local
public interface UserFacade {
	/*
	 Metodes invocats localment
   */
	public void registerUser(String nif, String name , String surname , String phone, String password , String email);
}
