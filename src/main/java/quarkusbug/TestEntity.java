package quarkusbug;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "TEST_ENTITY")
@SequenceGenerator(sequenceName = "TEST_SEQ_ENTITY", name = "SEQUENCE_GENERATOR", allocationSize = 1)
public class TestEntity {
   
	@Id
    private Long id;
	
	@Column(name = "NUMBER_FIELD")
	private Integer numberField;

	@Column(name = "STRING_FIELD", length = 50)
	private String stringField;

	@Column(name = "DATE_FIELD")
	private Date dateField;
	
	public TestEntity(Long id, Integer numberField, String stringField, Date dateField) {
		super();
		this.id = id;
		this.numberField = numberField;
		this.stringField = stringField;
		this.dateField = dateField;
	}

	public TestEntity() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumberField() {
		return numberField;
	}

	public void setNumberField(Integer numberField) {
		this.numberField = numberField;
	}

	public String getStringField() {
		return stringField;
	}

	public void setStringField(String stringField) {
		this.stringField = stringField;
	}

	public Date getDateField() {
		return dateField;
	}

	public void setDateField(Date dateField) {
		this.dateField = dateField;
	}

}
