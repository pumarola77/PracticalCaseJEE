package ejb;
import java.util.Collection;

import javax.ejb.Local;

import jpa.TalkedLanguageJPA;

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
	public Collection<TalkedLanguageJPA> listAllTalkedLanguages(String nif);
	public void addTalkedLanguage(String nif, String language, String level, String description);
	public void deleteTalkedLanguage(String nif, String language);
}
