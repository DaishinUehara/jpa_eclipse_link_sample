package uehara.daishin.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

//@Data // Dataアノテーションだと循環参照をおこしやすいのでやめておいかほうが良い
@Getter
@Setter
@Entity
public class JobHistory {
    @Id
    @GeneratedValue
    private Integer id;

    private Job job;

    private Person person;

	// 楽観的排他制御に用いるバージョンフィールドの指定
	@Version
	private Integer version;

/*
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
*/

}
