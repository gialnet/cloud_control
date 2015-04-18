/*
 * Consuming LinkedIn REST based Web Services using Scribe OAuth Java Library / API
 * http://codeoftheday.blogspot.com/2013/07/consuming-linkedin-rest-based-web.html
 */
package es.redmoon.tetburyss.sesion.linkedin;

/**
 * Represents a simple model used to store LinkedIn Group Information
 * 
 * @author smhumayun
 */
public class GroupInfo {
    
    private Long id = new Long(0);
    private String name;
    private Long memberCount = new Long(0);
    private Long lastChecked = new Long(0);

    public GroupInfo() {
    }

    public GroupInfo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Long memberCount) {
        this.memberCount = memberCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLastChecked() {
        return lastChecked;
    }

    public void setLastChecked(Long lastChecked) {
        this.lastChecked = lastChecked;
    }

}
