package quarkusbug;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;

public class TestBugTestORM extends BaseCoreFunctionalTestCase {

	
	@BeforeEach
	public void buildSessionFactory() {
		super.buildSessionFactory( null );
	}
	
	@Override
	protected Class[] getAnnotatedClasses() {
		return new Class[] {
				TestEntity.class
		};
	}
	
	@Override
	protected void configure(Configuration configuration) {
		super.configure( configuration );

		configuration.setProperty( AvailableSettings.SHOW_SQL, Boolean.TRUE.toString() );
		configuration.setProperty( AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString() );
	};

	private Date justDate = new Date();

	@Test
	public void saveAndCheck_test() {

		TestEntity testEntity = new TestEntity(1l, 1, "a", justDate);
		save(testEntity);
		assertEquals(1, find(1l).getNumberField());
	}

	// no update to database is made
	@Test
	public void saveAndCheckWithNonNullNumber_test() {

		TestEntity testEntity = createEntityWithNonNullNumber();
		save(testEntity);
		assertEquals(1, find(1l).getNumberField());

		testEntity = createEntityWithAllNulls();
		update(testEntity);
		assertEquals(null, find(1l).getNumberField());
	}

	// no update to database is made
	@Test
	public void saveAndCheckWithNonNullString_test() {

		TestEntity testEntity = createEntityWithNonNullString();
		save(testEntity);
		assertEquals("a", find(1l).getStringField());

		TestEntity testEntity2 = createEntityWithAllNulls();
		update(testEntity2);
		assertEquals(null, find(1l).getStringField());
	}

	@Test
	public void saveAndCheckWithNonNullDate_test() {

		TestEntity testEntity = createEntityWithNonNullDate();
		save(testEntity);
		assertEquals(justDate, find(1l).getDateField());

		testEntity = createEntityWithAllNulls();
		update(testEntity);
		assertEquals(null, find(1l).getDateField());
	}

	@Test
	public void saveAndCheckWithNonNullNumber_test2() {

		TestEntity testEntity = createFullEntity();
		save(testEntity);
		assertEquals(1, find(1l).getNumberField());

		testEntity = createEntityWithNullNumber();
		update(testEntity);
		assertEquals(null, find(1l).getNumberField());
	}

	@Test
	public void saveAndCheckWithNonNullString_test2() {

		TestEntity testEntity = createFullEntity();
		save(testEntity);
		assertEquals("a", find(1l).getStringField());

		testEntity = createEntityWithNullString();
		update(testEntity);
		assertEquals(null, find(1l).getStringField());
	}

	@Test
	public void saveAndCheckWithNonNullDate_test2() {

		TestEntity testEntity = createFullEntity();
		save(testEntity);
		assertEquals(justDate, find(1l).getDateField());

		testEntity = createEntityWithNullDate();
		update(testEntity);
		assertEquals(null, find(1l).getDateField());
	}

	private TestEntity createFullEntity() {
		return new TestEntity(1l, 1, "a", justDate);
	}

	private TestEntity createEntityWithNullNumber() {
		return new TestEntity(1l, null, "a", justDate);
	}

	private TestEntity createEntityWithNullString() {
		return new TestEntity(1l, 1, null, justDate);
	}

	private TestEntity createEntityWithNullDate() {
		return new TestEntity(1l, 1, "a", null);
	}

	private TestEntity createEntityWithNonNullNumber() {
		return new TestEntity(1l, 1, null, null);
	}

	private TestEntity createEntityWithNonNullString() {
		return new TestEntity(1l, null, "a", null);
	}

	private TestEntity createEntityWithNonNullDate() {
		return new TestEntity(1l, null, null, justDate);
	}

	private TestEntity createEntityWithAllNulls() {
		return new TestEntity(1l, null, null, null);
	}
	
	public void clean() {
		Session s = openSession();
		Transaction tx = s.beginTransaction();
		EntityManager em = s.getEntityManagerFactory().createEntityManager();
		em.createQuery("DELETE FROM TESTDELETE FROM TEST_ENTITY").executeUpdate();
		tx.commit();
		em.close();
		s.close();
	}
	
	public TestEntity find(Long id) {
		Session s = openSession();
		Transaction tx = s.beginTransaction();
		TestEntity entity = s.find(TestEntity.class, id);
		tx.commit();
		s.close();
		return entity;
	}

	public void save(TestEntity entity) {
		Session s = openSession();
		Transaction tx = s.beginTransaction();
		s.persist(entity);
		tx.commit();
		s.close();
	}

	public void update(TestEntity entity) {
		Session s = openSession();
		Transaction tx = s.beginTransaction();
		s.merge(entity);
		tx.commit();
		s.close();
	}
}
