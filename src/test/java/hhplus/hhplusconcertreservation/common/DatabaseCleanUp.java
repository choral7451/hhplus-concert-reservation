package hhplus.hhplusconcertreservation.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.EntityType;
import jakarta.transaction.Transactional;

@Component
public class DatabaseCleanUp implements InitializingBean {

	@PersistenceContext
	private EntityManager entityManager;

	private List<String> tables = new ArrayList<>();

	@Override
	public void afterPropertiesSet() {
		List<EntityType<?>> entityTypes = entityManager.getMetamodel().getEntities()
			.stream()
			.filter(entity -> entity.getJavaType().getAnnotation(Entity.class) != null)
			.collect(Collectors.toList());

		for (EntityType<?> entityType : entityTypes) {
			Table tableAnnotation = entityType.getJavaType().getAnnotation(Table.class);
			if (tableAnnotation != null) {
				tables.add(tableAnnotation.name());
			}
		}
	}

	@Transactional
	public void execute() {
		entityManager.flush();
		for (String table : tables) {
			entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
			entityManager.createNativeQuery("TRUNCATE TABLE " + table).executeUpdate();
			entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
			entityManager.createNativeQuery("ALTER TABLE " + table + " ALTER COLUMN ID RESTART WITH 1").executeUpdate(); // H2에 맞게 수정
		}
	}
}