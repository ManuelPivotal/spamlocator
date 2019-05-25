package org.telaside.spamlocator.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

@Entity
@Table(name = "host_hop")
public class HostHop {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "host_hop_name")
	private String name;

    @Column(name = "host_hop_ip")
    private String ip;
    
    @Column(name = "by_id")
    private String byId;
    
    public HostHop() {
    	
    }
    
	public HostHop(String name, String ip, String byId) {
		this.name = name;
		this.ip = ip;
		this.byId = byId;
	}
	
	public String getName() {
		return name;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getById() {
		return byId;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.add("ip", ip)
				.add("byId", byId)
				.toString();
	}
}
