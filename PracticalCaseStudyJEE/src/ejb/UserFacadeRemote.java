package ejb;
import java.util.Collection;

import javax.ejb.Remote;

import jpa.TalkedLanguageJPA;

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
	public Collection<TalkedLanguageJPA> listAllTalkedLanguages(String nif);
	public void addTalkedLanguage(String nif, String language, String level, String description);
	public void deleteTalkedLanguage(String nif, String language);
	public void addLanguageToTalk(String nif, String language, String level, String description, boolean acceptPay);
	public void deleteLanguageToTalk(String nif, String language);
}