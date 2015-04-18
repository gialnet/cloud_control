/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.redmoon.tetburyss.alta;

/**
 *
 * @author antonio
 */
public class TuplasOAuth2Google {
    
    /*
    {
       "id": "108068397209142441065",
       "email": "antonio.gialnet@gmail.com",
       "verified_email": true,
       "name": "antonio perez caballero",
       "given_name": "antonio",
       "family_name": "perez caballero",
       "link": "https://plus.google.com/108068397209142441065",
       "gender": "male",
       "locale": "es"
      }
    */
    private String id;
    private String email;
    private String verified_email;
    private String name;
    private String given_name;
    private String family_name;
    private String link;
    private String gender;
    private String locale;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getVerified_email() {
        return verified_email;
    }

    public String getName() {
        return name;
    }

    public String getGiven_name() {
        return given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public String getLink() {
        return link;
    }

    public String getGender() {
        return gender;
    }

    public String getLocale() {
        return locale;
    }
    
    
    
}
