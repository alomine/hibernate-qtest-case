package quarkusbug;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestBugTestJPA {

	private EntityManagerFactory entityManagerFactory;
	
	@BeforeEach
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
	}

	@AfterEach
	public void destroy() {
		entityManagerFactory.close();
	}

	private Date justDate = new Date();

	@Test
	public void saveAndCheck_test() {
		clean();

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
		clean();
		
		TestEntity testEntity = createEntityWithNonNullString();
		save(testEntity);
		assertEquals("a", find(1l).getStringField());

		TestEntity testEntity2 = createEntityWithAllNulls();
		update(testEntity2);
		assertEquals(null, find(1l).getStringField());
	}

	@Test
	public void saveAndCheckWithNonNullDate_test() {
		clean();

		TestEntity testEntity = createEntityWithNonNullDate();
		save(testEntity);
		assertEquals(justDate, find(1l).getDateField());

		testEntity = createEntityWithAllNulls();
		update(testEntity);
		assertEquals(null, find(1l).getDateField());
	}

	@Test
	public void saveAndCheckWithNonNullNumber_test2() {
		clean();

		TestEntity testEntity = createFullEntity();
		save(testEntity);
		assertEquals(1, find(1l).getNumberField());

		testEntity = createEntityWithNullNumber();
		update(testEntity);
		assertEquals(null, find(1l).getNumberField());
	}

	@Test
	public void saveAndCheckWithNonNullString_test2() {
		clean();

		TestEntity testEntity = createFullEntity();
		save(testEntity);
		assertEquals("a", find(1l).getStringField());

		testEntity = createEntityWithNullString();
		update(testEntity);
		assertEquals(null, find(1l).getStringField());
	}

	@Test
	public void saveAndCheckWithNonNullDate_test2() {
		clean();

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
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.createNativeQuery("DELETE FROM TEST_ENTITY").executeUpdate();
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	public TestEntity find(Long id) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		TestEntity entity = entityManager.find(TestEntity.class, id);
		entityManager = entityManagerFactory.createEntityManager();

		return entity;
	}

	public void save(TestEntity entity) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public void update(TestEntity entity) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(entity);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
