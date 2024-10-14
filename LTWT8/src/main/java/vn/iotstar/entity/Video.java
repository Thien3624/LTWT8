package vn.iotstar.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Data
@Entity
@Table(name = "Videos")
@NamedQuery(name = "Video.findAll", query = "SELECT c FROM Video c")
public class Video implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "videoid")
	private String videoid;

	@Column(name = "active")
	private int active;

	@Column(name = "description", columnDefinition = "VARCHAR(500)")
	private String description;

	@Column(name = "poster", columnDefinition = "VARCHAR(500)")
	private String poster;

	@Column(name = "title", columnDefinition = "VARCHAR(255)")
	private String title;

	@Column(name = "views")
	private int views;

	// bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name = "categoryid")
	private Category category;
}
