package vn.iotstar.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "user")
@NamedQuery(name = "User.findAll", query = "SELECT c FROM User c")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userid")
	private int userid;
	
	@Column(name = "username", columnDefinition = "VARCHAR(200) NOT NULL")
	private String username;
	
	@Column(name = "password", columnDefinition = "VARCHAR(200) NOT NULL")
	private String password;
	
	@Column(name = "avatar", columnDefinition = "VARCHAR(500)")
	private String avatar;
	
	@Column(name = "isseller")
	private int isseller;
	
	@Column(name = "isadmin")
	private int isadmin;
	
	@Column(name = "status")
	private int status;

}
